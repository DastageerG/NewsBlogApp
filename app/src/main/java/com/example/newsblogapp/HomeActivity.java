package com.example.newsblogapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newsblogapp.adapter.HomeRecyclerViewAdapter;
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

public class HomeActivity extends AppCompatActivity
{

    public static final String TAG = "1111";
    private Context context = HomeActivity.this;
    private RecyclerView recyclerView;
    private FireBaseMethods fireBaseMethods;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        progressBar = findViewById(R.id.progressBarHomeActivity);
        fireBaseMethods = new FireBaseMethods(context, progressBar);

        recyclerView = findViewById(R.id.recyclerViewHomeActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);


        fireBaseMethods.getAllPosts(new PostsListCallBack()
        {
            @Override
            public void postList(List<Post> postList)
            {
                if (postList != null)
                {
                    HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(context, postList);
                    recyclerView.setAdapter(adapter);
                } else
                {
                    Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
                } // else closed

            } // postList closed
        });


    } // onCreate closed


} // class closed