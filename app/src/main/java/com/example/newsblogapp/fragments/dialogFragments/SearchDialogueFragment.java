package com.example.newsblogapp.fragments.dialogFragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.newsblogapp.FireBaseMethods;
import com.example.newsblogapp.R;
import com.example.newsblogapp.adapter.FilteredListAdapter;
import com.example.newsblogapp.model.Post;
import com.example.newsblogapp.utils.PostsListCallBack;

import java.util.ArrayList;
import java.util.List;

public class SearchDialogueFragment extends DialogFragment
{
    private ImageView imageViewBack;
    private EditText editTextSearch;
    private FireBaseMethods fireBaseMethods;
    private List<Post> allPostList;
    private FilteredListAdapter adapter;
    private RecyclerView recyclerView;
    private CharSequence search = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_dilogue, container, false);
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        fireBaseMethods = new FireBaseMethods(getActivity());
        allPostList = new ArrayList<>();

        imageViewBack = view.findViewById(R.id.imageViewSearchDialogFragmentBack);
        editTextSearch = view.findViewById(R.id.editTextSearchDialogFragmentSearch);
        recyclerView = view.findViewById(R.id.recyclerViewSearchDialogueFragment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setVisibility(View.GONE);


        imageViewBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });

        fireBaseMethods.getAllPost(new PostsListCallBack()
        {
            @Override
            public void postList(List<Post> postList)
            {
                if (postList != null)
                {
                    allPostList = postList;
                    adapter = new FilteredListAdapter(getActivity(), allPostList);
                    recyclerView.setAdapter(adapter);
                } // if closed
            } // posList
        });


        editTextSearch.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count)
            {

                adapter.getFilter().filter(charSequence);
                search = charSequence;
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });


    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    } // onStart closed


} // SearchDialogFragment class closed