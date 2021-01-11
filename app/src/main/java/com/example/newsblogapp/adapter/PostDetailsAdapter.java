package com.example.newsblogapp.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.newsblogapp.FireBaseMethods;
import com.example.newsblogapp.R;
import com.example.newsblogapp.model.Post;
import com.example.newsblogapp.model.User;

import com.example.newsblogapp.utils.Constants;
import com.example.newsblogapp.utils.PostsListCallBack;
import com.example.newsblogapp.utils.UserNameCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class PostDetailsAdapter extends PagerAdapter
{
    private Context context;
    private List<Post> postList;
    private FireBaseMethods fireBaseMethods;

    public PostDetailsAdapter(Context context, List<Post> postList)
    {
        this.context = context;
        this.postList = postList;
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

        final ViewPager viewPager = view.findViewById(R.id.viewPagerInside);
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
                    textViewTimeAndName.setText(userName + " | " + AdapterUtils.getRelationTime(post.getTimestamp()));
                }
            }
        });


        final InSideViewPagerAdapter adapter = new InSideViewPagerAdapter(context, postList);
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        LinearLayout linearLayoutBack, linearLayoutForward;
        linearLayoutBack = view.findViewById(R.id.buttonInsideViePagerBack);
        linearLayoutForward = view.findViewById(R.id.buttonInsideViePagerForward);

//
//        final int min = 20;
//        final int max = 80;


        linearLayoutBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                final int random = new Random().nextInt((postList.size() - 1 - 0) + 1) + 0;
                int inc = (viewPager.getCurrentItem() - 1) % postList.size();
                viewPager.setCurrentItem(random, true);
                adapter.notifyDataSetChanged();
            }
        });

        linearLayoutForward.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final int random = new Random().nextInt((postList.size() - 1 - 0) + 1) + 0;
                int inc = (viewPager.getCurrentItem() + 1) % postList.size();
                viewPager.setCurrentItem(random, true);
                adapter.notifyDataSetChanged();
            }
        });

        container.addView(view);
        return view;
    }





} // class closed
