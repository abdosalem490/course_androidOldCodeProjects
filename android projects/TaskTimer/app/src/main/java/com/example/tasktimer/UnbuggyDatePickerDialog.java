package com.example.tasktimer;

import android.app.DatePickerDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

public class UnbuggyDatePickerDialog extends DatePickerDialog
{
    public UnbuggyDatePickerDialog(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable DatePickerDialog.OnDateSetListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
    }

    @Override
    protected void onStop() {
        //do nothing , don't call super
    }
}
