package com.abdosalm.contactroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdosalm.contactroom.R;
import com.abdosalm.contactroom.model.Contact;

import java.util.List;
import java.util.Objects;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private OnContactClickListener onContactClickListener;
    private List<Contact> contactList;
    private Context context;
    private static final String TAG = "RecyclerViewAdapter";
    public RecyclerViewAdapter(List<Contact> contactList, Context context , OnContactClickListener onContactClickListener) {
        this.contactList = contactList;
        this.context = context;
        this.onContactClickListener = onContactClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row , parent , false);
        return new ViewHolder(view , onContactClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerViewAdapter.ViewHolder holder, int position) {
        Contact contact = Objects.requireNonNull(contactList.get(position));
        holder.name.setText(contact.getName());
        holder.occupation.setText(contact.getOccupation());
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(contactList).size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        OnContactClickListener onContactClickListener ;
        public TextView name;
        public TextView occupation;

        public ViewHolder(@NonNull  View itemView , OnContactClickListener onContactClickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.row_name_textview);
            occupation = itemView.findViewById(R.id.row_occupation_textview);
            itemView.setOnClickListener(this);
            this.onContactClickListener = onContactClickListener;
        }

        @Override
        public void onClick(View v) {
            onContactClickListener.onContact(getAdapterPosition());
        }
    }
    public interface OnContactClickListener{
        void onContact(int position);
    }
}
