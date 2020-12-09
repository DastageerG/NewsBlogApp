package com.example.newsblogapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsblogapp.FireBaseMethods;
import com.example.newsblogapp.R;
import com.example.newsblogapp.activities.PostDetailsActivity;
import com.example.newsblogapp.model.Post;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import static com.example.newsblogapp.adapter.TimeFormat.getRelationTime;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>
{

    private Context context;
    private List<Post> postList;

    public HomeRecyclerViewAdapter(Context context, List<Post> postList)
    {
        this.context = context;
        this.postList = postList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imageViewPostThumbNail;
        private TextView textViewPostTitle, textViewTime;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageViewPostThumbNail = itemView.findViewById(R.id.imagViewLayoutMainRecyclerViewThumbNail);
            textViewPostTitle = itemView.findViewById(R.id.textViewLayoutMainRecyclerViewPostTitle);
            textViewTime = itemView.findViewById(R.id.textViewLayoutMainRecyclerViewPostTime);
        } // viewHolder closed
    } // viewHolder class closed

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_main_recycler_view, parent, false);
        return new ViewHolder(view);

    } // viewHolder closed

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        Post post = postList.get(position);
        Picasso.get().load(post.getPostThumbnailUrl()).placeholder(context.getResources().getDrawable(R.color.colorPrimaryDark)).into(holder.imageViewPostThumbNail);
        holder.textViewPostTitle.setText(post.getPostTitle());
        holder.textViewTime.setText("UPDATED  " + TimeFormat.getRelationTime(post.getTimestamp()));

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, PostDetailsActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            } // onClick closed
        });

    }

    @Override
    public int getItemCount()
    {
        return postList.size();
    }



}
