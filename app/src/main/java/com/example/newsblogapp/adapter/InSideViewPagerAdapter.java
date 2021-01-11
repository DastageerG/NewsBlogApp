package com.example.newsblogapp.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.newsblogapp.FireBaseMethods;
import com.example.newsblogapp.R;
import com.example.newsblogapp.model.Post;
import com.example.newsblogapp.model.User;
import com.example.newsblogapp.utils.Constants;
import com.example.newsblogapp.utils.UserNameCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class InSideViewPagerAdapter extends PagerAdapter
{
    private Context context;
    private List<Post> postList;

    public InSideViewPagerAdapter(Context context, List<Post> postList)
    {
        this.context = context;
        this.postList = postList;
    }

    @Override
    public int getCount()
    {
        return postList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_inside_view_pager, null);

        ImageView imageView = view.findViewById(R.id.imageViewLayoutInsideViewPagerThumbNail);
        TextView textViewPostTitle = view.findViewById(R.id.textViewLayoutInsideViewPagerPostTitle);
        TextView textViewPostTime = view.findViewById(R.id.textViewLayoutInsideViePagerPostTime);

        Post bean = postList.get(position);
        Picasso.get().load(bean.getPostImageUrl()).placeholder(R.color.colorWhite).into(imageView);
        textViewPostTitle.setText(bean.getPostTitle());
        textViewPostTime.setText("UPDATED  " + AdapterUtils.getRelationTime(bean.getTimestamp()));
        container.addView(view);
        return view;
    }



} // class closed
