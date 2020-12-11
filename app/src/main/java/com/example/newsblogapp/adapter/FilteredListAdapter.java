package com.example.newsblogapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsblogapp.R;
import com.example.newsblogapp.activities.PostDetailsActivity;
import com.example.newsblogapp.model.Post;

import java.util.ArrayList;
import java.util.List;

public class FilteredListAdapter extends RecyclerView.Adapter<FilteredListAdapter.ViewHolder>
{
    private Context context;
    private List<Post> postList;
    private List<Post> filteredList;

    public FilteredListAdapter(Context context, List<Post> postList)
    {
        this.context = context;
        this.postList = postList;
        this.filteredList = postList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textViewResult;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textViewResult = itemView.findViewById(R.id.textViewLayoutFilteredRecyclerView);
        }
    } // ViewHolder closed


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_filtered_recycyler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        Post post = filteredList.get(position);
        holder.textViewResult.setText(post.getPostTitle());

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
        return filteredList.size();
    }


    public Filter getFilter()
    {

        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {

                String Key = charSequence.toString();
                if (Key.isEmpty())
                {
                    filteredList = filteredList;
                } else
                {

                    List<Post> lstFiltered = new ArrayList<>();
                    for (Post row : postList)
                    {
                        if (row.getPostTitle().toLowerCase().contains(Key.toLowerCase()))
                        {
                            lstFiltered.add(row);
                        }
                    }

                    filteredList = lstFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {

                filteredList = (List<Post>) filterResults.values;
                notifyDataSetChanged();
            }
        };

    }


}
