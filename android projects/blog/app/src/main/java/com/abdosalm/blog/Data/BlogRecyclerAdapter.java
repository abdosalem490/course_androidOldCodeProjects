package com.abdosalm.blog.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdosalm.blog.Model.Blog;
import com.abdosalm.blog.R;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Blog> blogList;

    public BlogRecyclerAdapter(Context context, List<Blog> blogList) {
        this.context = context;
        this.blogList = blogList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row,parent,false);
        return new ViewHolder(view , context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Blog blog = blogList.get(position);
        String imageUrl = null;

        holder.title.setText(blog.getTitle());
        holder.desc.setText(blog.getDescription());
        holder.timestamp.setText(blog.getTimestamp());

        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(Long.valueOf(blog.getTimestamp())).getTime());
        holder.timestamp.setText(formattedDate);

        imageUrl = blog.getImage();

        Picasso.get().load(imageUrl).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView desc;
        public TextView timestamp;
        public ImageView image;
        String userid;
        public ViewHolder(@NonNull View itemView , Context ctx) {
            super(itemView);
            context = ctx;
            title = itemView.findViewById(R.id.postTitleList);
            desc = itemView.findViewById(R.id.postDescriptionEt);
            timestamp = itemView.findViewById(R.id.timestampList);
            image = itemView.findViewById(R.id.postImageList);
            userid = null;

            itemView.setOnClickListener(v->{
                // we can go to next activity

            });
        }
    }
}
