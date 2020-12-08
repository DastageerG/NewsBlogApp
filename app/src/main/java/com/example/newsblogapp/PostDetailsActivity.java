package com.example.newsblogapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.newsblogapp.adapter.PostDetailsAdapter;
import com.example.newsblogapp.model.Post;
import com.example.newsblogapp.utils.Constants;
import com.example.newsblogapp.utils.PostsListCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PostDetailsActivity extends AppCompatActivity
{

    public static final String TAG = "1111";
    private Context context = PostDetailsActivity.this;
    private ViewPager viewPager, viewPagerNew;
    private ProgressBar progressBar;
    private int position;
    private FireBaseMethods fireBaseMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        viewPager = findViewById(R.id.viewPagerPostDetailsActivity);
        progressBar = findViewById(R.id.progressBarPostDetailsActivity);

        fireBaseMethods = new FireBaseMethods(context, progressBar);
        position = getIntent().getExtras().getInt("position");

        fireBaseMethods.getAllPosts(new PostsListCallBack()
        {
            @Override
            public void postList(List<Post> postList)
            {
                PostDetailsAdapter adapter = new PostDetailsAdapter(context, postList);
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(position);
            }
        });


    } //onCreate closed
} /// class closed