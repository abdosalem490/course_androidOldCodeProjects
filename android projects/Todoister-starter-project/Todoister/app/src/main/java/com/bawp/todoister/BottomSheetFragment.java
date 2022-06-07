package com.bawp.todoister;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bawp.todoister.model.Priority;
import com.bawp.todoister.model.SharedViewModel;
import com.bawp.todoister.model.Task;
import com.bawp.todoister.model.TaskViewModel;
import com.bawp.todoister.util.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private EditText enterTodo;
    private ImageButton calendarButton;
    private ImageButton priorityButton;
    private RadioGroup priorityRadioGroup;
    private RadioButton selectedRadioButton;
    private int selectedButtonId;
    private ImageButton saveButton;
    private CalendarView calendarView;
    private Group calendarGroup;
    private Date dueDate;
    Calendar calendar = Calendar.getInstance();
    private SharedViewModel sharedViewModel;
    private boolean isEdit;
    private Priority priority;

    private static final String TAG = "BottomSheetFragment";

    public BottomSheetFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        calendarGroup = view.findViewById(R.id.calendar_group);
        calendarView = view.findViewById(R.id.calendar_view);
        calendarButton = view.findViewById(R.id.today_calendar_button);
        enterTodo = view.findViewById(R.id.enter_todo_et);
        saveButton = view.findViewById(R.id.save_todo_button);
        priorityButton = view.findViewById(R.id.priority_todo_button);
        priorityRadioGroup = view.findViewById(R.id.radioGroup_priority);

        Chip todayChip = view.findViewById(R.id.today_chip);
        todayChip.setOnClickListener(this);
        Chip tomorrowChip = view.findViewById(R.id.tomorrow_chip);
        tomorrowChip.setOnClickListener(this);
        Chip nextWeekChip = view.findViewById(R.id.next_week_chip);
        nextWeekChip.setOnClickListener(this);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        calendarButton.setOnClickListener(v->{
            calendarGroup.setVisibility(calendarGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            Utils.hidSoftKeyboard(v);
        });

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            calendar.clear();
            calendar.set(year , month , dayOfMonth);
            dueDate = calendar.getTime();
        });

        priorityButton.setOnClickListener(v->{
            Utils.hidSoftKeyboard(v);
            priorityRadioGroup.setVisibility(priorityRadioGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            priorityRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                if (priorityRadioGroup.getVisibility() == View.VISIBLE){
                    selectedButtonId = checkedId;
                    selectedRadioButton = view.findViewById(selectedButtonId);
                    if (selectedRadioButton.getId() == R.id.radioButton_high){
                        priority = Priority.HIGH;
                    }else if (selectedRadioButton.getId() == R.id.radioButton_med){
                        priority = Priority.MEDIUM;
                    }else if (selectedRadioButton.getId() == R.id.radioButton_low){
                        priority = Priority.LOW;
                    }else{
                        priority = Priority.LOW;
                    }
                }else{
                    priority = Priority.LOW;
                }
            });
        });

        saveButton.setOnClickListener(v -> {
            String task = enterTodo.getText().toString().trim();
            if (!TextUtils.isEmpty(task) && dueDate != null && priority != null){
                Task myTask = new Task(task, priority , dueDate, Calendar.getInstance().getTime(), false);
                if (isEdit){
                    Task updateTask = sharedViewModel.getSelectedItem().getValue();
                    updateTask.setTask(task);
                    updateTask.setDataCreated(Calendar.getInstance().getTime());
                    updateTask.setPriority(priority);
                    updateTask.setDueDate(dueDate);
                    TaskViewModel.update(updateTask);
                    sharedViewModel.setIsEdit(false);
                } else {
                    TaskViewModel.insert(myTask);
                }
                enterTodo.setText("");
                if (this.isVisible()){
                    this.dismiss();
                }
            }else {
                Snackbar.make(saveButton, R.string.empty_field, Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedViewModel.getSelectedItem().getValue() != null && sharedViewModel.getIsEdit()){
            isEdit = sharedViewModel.getIsEdit();
            Task task = sharedViewModel.getSelectedItem().getValue();
            enterTodo.setText(task.getTask());
        }else{
            enterTodo.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.today_chip){
            calendar.add(Calendar.DAY_OF_YEAR , 0);
            dueDate = calendar.getTime();
        }else if (id == R.id.tomorrow_chip){
            calendar.add(Calendar.DAY_OF_YEAR , 1);
            dueDate = calendar.getTime();
        }else if (id == R.id.next_week_chip){
            calendar.add(Calendar.DAY_OF_YEAR , 7);
            dueDate = calendar.getTime();
        }
    }
}