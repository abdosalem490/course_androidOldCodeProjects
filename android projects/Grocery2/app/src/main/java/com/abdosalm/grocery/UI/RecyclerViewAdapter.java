package com.abdosalm.grocery.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdosalm.grocery.Activities.DetailsActivity;
import com.abdosalm.grocery.Model.Grocery;
import com.abdosalm.grocery.R;
import com.abdosalm.grocery.Data.DatabaseHandler;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Grocery> groceryList;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public RecyclerViewAdapter(Context context, List<Grocery> groceryList) {
        this.context = context;
        this.groceryList = groceryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);
        return new ViewHolder(view , context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Grocery grocery = groceryList.get(position);
        holder.groceryItemName.setText(grocery.getName());
        holder.quantity.setText(grocery.getQuantity());
        holder.dateAdded.setText(grocery.getDateItemAdded());
    }

    @Override
    public int getItemCount() {
        return groceryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView groceryItemName;
        public TextView quantity;
        public TextView dateAdded;
        public Button editButton;
        public Button deleteButton;
        public int id;
        public ViewHolder(@NonNull View view , Context ctx) {
            super(view);
            context = ctx;
            groceryItemName = view.findViewById(R.id.name);
            quantity = view.findViewById(R.id.quantity);
            dateAdded = view.findViewById(R.id.dateAdded);

            editButton = view.findViewById(R.id.editButton);
            deleteButton = view.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //go to next screen\Details Activity
                    int position = getAdapterPosition();
                    Grocery grocery = groceryList.get(position);
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("name",grocery.getName());
                    intent.putExtra("quantity",grocery.getQuantity());
                    intent.putExtra("id",grocery.getId());
                    intent.putExtra("date",grocery.getDateItemAdded());
                    context.startActivity(intent);

                }
            });
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.editButton:
                    int position = getAdapterPosition();
                    Grocery grocery = groceryList.get(position);
                    editItem(grocery);
                    break;
                case R.id.deleteButton:
                    grocery = groceryList.get(getAdapterPosition());
                    deleteItem(grocery.getId());
                    break;
            }
        }
        public void deleteItem(final int id){
            //create an alertDialog
            alertDialogBuilder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_dialog,null);

            Button noButton = view.findViewById(R.id.noButton);
            Button yesButton = view.findViewById(R.id.yesButton);

            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();
            noButton.setOnClickListener(v->{
                dialog.dismiss();
            });
            yesButton.setOnClickListener(v->{
                //delete the item
                DatabaseHandler db = new DatabaseHandler(context);
                db.deleteGrocery(id);
                groceryList.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                dialog.dismiss();
            });
        }
        public void editItem(Grocery grocery){
            alertDialogBuilder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.popup,null);

            final EditText groceryItem = view.findViewById(R.id.groceryItem);
            final EditText quantity = view.findViewById(R.id.groceryQty);
            final TextView title = view.findViewById(R.id.title);
            title.setText("Edit Grocery");
            final Button saveButton = view.findViewById(R.id.saveButton);

            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();

            saveButton.setOnClickListener(v->{
                DatabaseHandler db = new DatabaseHandler(context);
                //update item
                grocery.setName(groceryItem.getText().toString());
                grocery.setQuantity(quantity.getText().toString());
                if (!groceryItem.getText().toString().isEmpty() && !quantity.getText().toString().isEmpty())
                {
                    db.updateGrocery(grocery);
                    notifyItemChanged(getAdapterPosition() , grocery);
                }else{
                    Snackbar.make(view,"Add Grocery and Quantity",Snackbar.LENGTH_LONG).show();
                }
                dialog.dismiss();

            });

        }
    }
}
