package com.example.newsblogapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.newsblogapp.model.Post;
import com.example.newsblogapp.model.User;
import com.example.newsblogapp.utils.Constants;
import com.example.newsblogapp.utils.PostsListCallBack;
import com.example.newsblogapp.utils.UserNameCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class FireBaseMethods
{
    public static final String TAG = "1111";
    private Context context;
    private DatabaseReference databaseReference;
    public List<Post> allPosts;
    private ProgressBar progressBar;


    public FireBaseMethods(Context context)
    {
        this.context = context;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.keepSynced(true);
    }

    public FireBaseMethods(Context context, ProgressBar progressBar)
    {
        this.progressBar = progressBar;
        this.context = context;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.keepSynced(true);
    }

    ////

    public void getAllPosts(final PostsListCallBack callBack)
    {
        progressBar.setVisibility(View.VISIBLE);
        databaseReference.child(Constants.Posts).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                progressBar.setVisibility(View.GONE);
                allPosts = new ArrayList<>();
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        Post post = snapshot.getValue(Post.class);
                        allPosts.add(post);
                    } // for closed
                    callBack.postList(allPosts);
                } // if closed

            } // onDataChange closed

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(context, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onCancelled: " + databaseError.getMessage());
            } // onCancelled closed
        }); // addValueEventListener closed
    } // getAllPosts closed


    public void getEveryPosts(final PostsListCallBack callBack)
    {
        databaseReference.child(Constants.Posts).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                allPosts = new ArrayList<>();
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        Post post = snapshot.getValue(Post.class);
                        allPosts.add(post);
                    } // for closed
                    callBack.postList(allPosts);
                } // if closed

            } // onDataChange closed

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(context, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onCancelled: " + databaseError.getMessage());
            } // onCancelled closed
        }); // addValueEventListener closed
    } // getAllPosts closed


    public void getUserName(String userId, final UserNameCallBack callBack)
    {

        databaseReference.child(Constants.USERS).child(userId).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    User user = snapshot.getValue(User.class);
                    callBack.userName(user.getUserName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

    } // getUseName Method closed


} // firebaseMethod closed