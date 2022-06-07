package com.abdosalm.cmp2024.Activities.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdosalm.cmp2024.Activities.Model.FeatureModel;
import com.abdosalm.cmp2024.Activities.interfaces.IMainViewClicked;
import com.abdosalm.cmp2024.R;

import java.util.List;

public class MainViewRecyclerView extends RecyclerView.Adapter<MainViewRecyclerView.ViewModel> {
    private Context context;
    private List<FeatureModel> featureModels;
    private IMainViewClicked mainViewClicked;
    public MainViewRecyclerView(Context context, List<FeatureModel> featureModels , IMainViewClicked mainViewClicked) {
        this.context = context;
        this.featureModels = featureModels;
        this.mainViewClicked = mainViewClicked;
    }

    @NonNull
    @Override
    public MainViewRecyclerView.ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_view_row,parent , false);
        return new ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewRecyclerView.ViewModel holder, int position) {
        if (1 == position)
            holder.title.setTextSize(18);
        holder.title.setText(featureModels.get(position).getTitle());
        holder.featureImage.setImageResource(featureModels.get(position).getImageId());
    }

    @Override
    public int getItemCount() {
        return featureModels.size();
    }

    public class ViewModel extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView featureImage;
        public TextView title;
        public ViewModel(@NonNull View view) {
            super(view);
            featureImage = view.findViewById(R.id.featureImage);
            title = view.findViewById(R.id.featureTitle);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mainViewClicked.goFromMainViewTo(getAdapterPosition());
        }
    }
}
