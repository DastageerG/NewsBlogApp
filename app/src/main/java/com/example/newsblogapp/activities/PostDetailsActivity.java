package com.example.newsblogapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.newsblogapp.FireBaseMethods;
import com.example.newsblogapp.R;
import com.example.newsblogapp.adapter.AdapterUtils;
import com.example.newsblogapp.adapter.CategoriesGridViewAdapter;
import com.example.newsblogapp.adapter.PostDetailsAdapter;
import com.example.newsblogapp.adapter.SettingsGridViewAdapter;
import com.example.newsblogapp.fragments.BusinessFragment;
import com.example.newsblogapp.fragments.CityFMFragment;
import com.example.newsblogapp.fragments.EntertainmentFragment;
import com.example.newsblogapp.fragments.HomeFragment;
import com.example.newsblogapp.fragments.LatestFragment;
import com.example.newsblogapp.fragments.MagazineFragment;
import com.example.newsblogapp.fragments.OpinionFragment;
import com.example.newsblogapp.fragments.PakistanFragment;
import com.example.newsblogapp.fragments.SportsFragment;
import com.example.newsblogapp.fragments.WorldFragment;
import com.example.newsblogapp.fragments.dialogFragments.SearchDialogueFragment;
import com.example.newsblogapp.model.Post;
import com.example.newsblogapp.utils.Constants;
import com.example.newsblogapp.utils.PostsListCallBack;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostDetailsActivity extends AppCompatActivity implements View.OnClickListener
{

    public static final String TAG = "1111";

    private Context context = PostDetailsActivity.this;
    private ViewPager viewPager;
    private ProgressBar progressBar;
    private int position;
    private String fragmentName;
    private FireBaseMethods fireBaseMethods;
    private TextView textViewDawn, textViewTop, textViewLatest, textViewFragmentName;
    private ImageView imageViewBack, imageViewSearch;

    public static final int HOME_FRAG = 111;
    public static final int LATEST_FRAG = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        viewPager = findViewById(R.id.viewPagerPostDetailsActivity);
        progressBar = findViewById(R.id.progressBarPostDetailsActivity);
        textViewDawn = findViewById(R.id.textViewPostDetailsActivityDawn);
        textViewTop = findViewById(R.id.texViewPostDetailsActivityTop);
        textViewLatest = findViewById(R.id.texViewPostDetailsActivityLatest);
        textViewFragmentName = findViewById(R.id.textViewPostDetailsActivityFragmentName);
        imageViewBack = findViewById(R.id.imageViewPostDetailsActivityBack);
        imageViewSearch = findViewById(R.id.imageViewPostDetailsActivitySearch);

        fireBaseMethods = new FireBaseMethods(context, progressBar);

        position = getIntent().getExtras().getInt(Constants.position);
        fragmentName = getIntent().getExtras().getString(Constants.fragName);

        // making first letter capital due to database structure like , Top , Latest
        String cap = fragmentName.substring(0, 1).toUpperCase() + fragmentName.substring(1).toLowerCase();

        textViewFragmentName.setText(fragmentName);
        if (cap.equals("Home"))
        {
            cap = "Top"; // because Home is not available in database but Top
        }

        // getting Posts with respective to category
        fireBaseMethods.getAllPosts(cap, new PostsListCallBack()
        {
            @Override
            public void postList(List<Post> postList)
            {
                Log.d(TAG, "postList: " + postList);
                PostDetailsAdapter adapter = new PostDetailsAdapter(context, postList);
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(position);
                adapter.notifyDataSetChanged();
            }
        });

        textViewTop.setOnClickListener(this);
        textViewLatest.setOnClickListener(this);
        textViewDawn.setOnClickListener(this);
        imageViewBack.setOnClickListener(this);
        imageViewSearch.setOnClickListener(this);


//        textViewTop.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                // sendPosition adds integer to intent and starts other activity
//                sendPosition(HOME_FRAG); // HOME_FRAG is like Request Code of Activity
//            } // onclick closed
//        });
//        textViewLatest.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                sendPosition(LATEST_FRAG);
//
//            } // onClick closed
//        });
//
//        textViewDawn.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                inflateBottomSheetDialogue();
//            }
//        });


    } //onCreate closed

    private void inflateBottomSheetDialogue()
    {

        //BottomSheet dialogue properties
        final BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.SheetDialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.setContentView(R.layout.layout_categories_bottom_sheet);
        dialog.setCanceledOnTouchOutside(false);

        // BottomSheet dialog views
        GridView gridViewCategories, gridViewSettings;
        gridViewCategories = dialog.findViewById(R.id.gridViewCategories);
        gridViewSettings = dialog.findViewById(R.id.gridViewSettings);
        TextView textViewDawn, textViewLatest, textViewTop;
        textViewDawn = dialog.findViewById(R.id.textViewLayoutCategoriesBottomSheetDawn);
        textViewLatest = dialog.findViewById(R.id.textViewLayoutCategoriesBottomSheetLatest);
        textViewTop = dialog.findViewById(R.id.textViewLayoutCategoriesBottomSheetTop);


        final HashMap<String, Fragment> categoriesHashMap = new HashMap<>();
        categoriesHashMap.put(Constants.opinion, new OpinionFragment());
        categoriesHashMap.put(Constants.world, new WorldFragment());
        categoriesHashMap.put(Constants.sports, new SportsFragment());
        categoriesHashMap.put(Constants.magazine, new MagazineFragment());
        categoriesHashMap.put(Constants.business, new BusinessFragment());
        categoriesHashMap.put("FM", new CityFMFragment());
        categoriesHashMap.put("Home", new HomeFragment());
        categoriesHashMap.put(Constants.latest, new LatestFragment());
        categoriesHashMap.put(Constants.pakistan, new PakistanFragment());
        categoriesHashMap.put(Constants.entertainment, new EntertainmentFragment());


        HashMap<String, Fragment> settingsHashMap = new HashMap<>();
        settingsHashMap.put(getString(R.string.search), new HomeFragment());
        settingsHashMap.put(getString(R.string.settings), new HomeFragment());
        settingsHashMap.put(getString(R.string.bookmarks), new HomeFragment());
        settingsHashMap.put(getString(R.string.liveTV), new HomeFragment());
        settingsHashMap.put(getString(R.string.aboutus), new HomeFragment());
        settingsHashMap.put(getString(R.string.feedback), new HomeFragment());


        CategoriesGridViewAdapter adapter = new CategoriesGridViewAdapter(context, categoriesHashMap);
        gridViewCategories.setAdapter(adapter);
        SettingsGridViewAdapter adapter1 = new SettingsGridViewAdapter(context, settingsHashMap);
        gridViewSettings.setAdapter(adapter1);

        // Dawn from BottomSheetDialogue

        textViewDawn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                dialog.dismiss();
            }
        });
        // top and Latest in SearchDialogue ,
        textViewTop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendPosition(LATEST_FRAG);
                dialog.dismiss();
            }
        });
        textViewLatest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                sendPosition(LATEST_FRAG);
                dialog.dismiss();
            }
        });

        gridViewCategories.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                sendPosition(position);
            } /// onItemClick closed
        });
        dialog.show();

    } // inflate DialogClosed

    private void sendPosition(int position)
    {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(Constants.position, position);
        startActivity(intent);
        finish();
    } /// sendPosition closed


    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.texViewPostDetailsActivityTop:
                sendPosition(HOME_FRAG);
                break;
            case R.id.textViewPostDetailsActivityDawn:
                inflateBottomSheetDialogue();
                break;
            case R.id.texViewPostDetailsActivityLatest:
                sendPosition(LATEST_FRAG);
                break;
            case R.id.imageViewPostDetailsActivityBack:
                finish();
                break;
            case R.id.imageViewPostDetailsActivitySearch:
                SearchDialogueFragment dialog = new SearchDialogueFragment();
                dialog.show(getSupportFragmentManager(), "searchFragment");
                break;
        } // switch closed
    } // onClick closed
} /// class closed