package com.example.newsblogapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newsblogapp.FireBaseMethods;
import com.example.newsblogapp.R;
import com.example.newsblogapp.adapter.HomeRecyclerViewAdapter;
import com.example.newsblogapp.model.Post;
import com.example.newsblogapp.utils.Constants;
import com.example.newsblogapp.utils.PostsListCallBack;

import java.util.List;


public class OpinionFragment extends Fragment
{


    public static final String TAG = "1111";
    private RecyclerView recyclerView;
    private FireBaseMethods fireBaseMethods;
    private ProgressBar progressBar;
    private FragmentActivity context = getActivity();
    private String child;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_opinion, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);


        fireBaseMethods.getAllPosts("Opinion", new PostsListCallBack()
        {
            @Override
            public void postList(List<Post> postList)
            {
                if (postList != null)
                {
                    HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(getActivity(), postList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else
                {
                    Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                } // else closed

            } // postList closed
        });
    } // onViewCreated closed

    private void initViews(View view)
    {


        progressBar = view.findViewById(R.id.progressBarOpinionFragment);
        fireBaseMethods = new FireBaseMethods(context, progressBar);
        recyclerView = view.findViewById(R.id.recyclerViewOpinionFragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);


    }

}