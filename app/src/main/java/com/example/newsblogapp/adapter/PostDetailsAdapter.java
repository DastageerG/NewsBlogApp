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
import androidx.viewpager.widget.ViewPager;

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

public class PostDetailsAdapter extends PagerAdapter
{
    private Context context;
    private List<Post> postList;
    private DatabaseReference databaseReference;
    private FireBaseMethods fireBaseMethods;

    public PostDetailsAdapter(Context context, List<Post> postList)
    {
        this.context = context;
        this.postList = postList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        fireBaseMethods = new FireBaseMethods(context);
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

        View view = LayoutInflater.from(context).inflate(R.layout.layout_post_details_view_pager, null);

        ViewPager viewPager = view.findViewById(R.id.viewPagerInside);
        ImageView imageViewPostImage = view.findViewById(R.id.imageViewLayoutPostDetailsViewPagerPostImage);
        final TextView textViewPostTitle, textViewTimeAndName, textViewDescription, textViewImageDescription;

        textViewPostTitle = view.findViewById(R.id.textViewLayoutPostDetailsViewPagerPostTitle);
        textViewTimeAndName = view.findViewById(R.id.textViewLayoutPostDetailsViewPagerNameAndTime);
        textViewDescription = view.findViewById(R.id.textViewLayoutPostDetailsViewPagerPostDetails);
        textViewImageDescription = view.findViewById(R.id.textViewLayoutPostDetailsViewPagerImageDescription);

        final Post post = postList.get(position);

        Picasso.get().load(post.getPostImageUrl()).placeholder(R.color.colorWhite).into(imageViewPostImage);
        textViewPostTitle.setText(post.getPostTitle());
        textViewDescription.setText(post.getPostDescription());
        if (!post.getPostImageDescription().equals("empty"))
        {
            textViewImageDescription.setText(post.getPostImageDescription());
        } else
        {
            textViewImageDescription.setVisibility(View.GONE);
        }


        String userId = post.getUserId();

        fireBaseMethods.getUserName(userId, new UserNameCallBack()
        {
            @Override
            public void userName(String userName)
            {
                if (userName != null)
                {
                    textViewTimeAndName.setText(userName + " | " + getRelationTime(post.getTimestamp()));
                }

            }
        });


        databaseReference.child(Constants.USERS).child(userId).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    User user = snapshot.getValue(User.class);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        InSideViewPagerAdapter adapter = new InSideViewPagerAdapter(context, postList);
        viewPager.setAdapter(adapter);


        container.addView(view);
        return view;
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


} // class closed
