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
import com.example.newsblogapp.PostDetailsActivity;
import com.example.newsblogapp.R;
import com.example.newsblogapp.model.Post;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

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
        Picasso.get().load(post.getPostThumbnailUrl()).placeholder(context.getResources().getDrawable(R.color.colorWhite)).into(holder.imageViewPostThumbNail);
        holder.textViewPostTitle.setText(post.getPostTitle());
        holder.textViewTime.setText("UPDATED  " + getRelationTime(post.getTimestamp()));

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

    public static final long AVERAGE_MONTH_IN_MILLIS = DateUtils.DAY_IN_MILLIS * 30;

    public String getRelationTime(long time)
    {
        final long now = new Date().getTime();
        final long delta = now - time;
        long resolution;
        if (delta <= DateUtils.MINUTE_IN_MILLIS)
        {
            resolution = DateUtils.SECOND_IN_MILLIS;
        } else if (delta <= DateUtils.HOUR_IN_MILLIS)
        {
            resolution = DateUtils.MINUTE_IN_MILLIS;
        } else if (delta <= DateUtils.DAY_IN_MILLIS)
        {
            resolution = DateUtils.HOUR_IN_MILLIS;
        } else if (delta <= DateUtils.WEEK_IN_MILLIS)
        {
            resolution = DateUtils.DAY_IN_MILLIS;
        } else if (delta <= AVERAGE_MONTH_IN_MILLIS)
        {
            return Integer.toString((int) (delta / DateUtils.WEEK_IN_MILLIS)) + " weeks(s) ago";
        } else if (delta <= DateUtils.YEAR_IN_MILLIS)
        {
            return Integer.toString((int) (delta / AVERAGE_MONTH_IN_MILLIS)) + " month(s) ago";
        } else
        {
            return Integer.toString((int) (delta / DateUtils.YEAR_IN_MILLIS)) + " year(s) ago";
        }
        return DateUtils.getRelativeTimeSpanString(time, now, resolution).toString();
    }

}
