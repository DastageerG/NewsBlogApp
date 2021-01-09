package com.example.newsblogapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.newsblogapp.R;
import com.example.newsblogapp.adapter.AdapterUtils;
import com.example.newsblogapp.adapter.CategoriesGridViewAdapter;
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
import com.example.newsblogapp.utils.Constants;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.newsblogapp.adapter.AdapterUtils.getFragment;

public class HomeActivity extends AppCompatActivity
{

    public static final String TAG = "1111";
    private Context context = HomeActivity.this;
    private ImageView imageViewSearch;
    private TextView textViewDawn, textViewTop, textViewLatest;
    public static TextView textViewFragmentName;

    // HashMap for bottomSheet Categories GridView;
    private HashMap<String, Fragment> categoriesHashMap;
    /// these intents work like requestCode from otherActivity
    public static final int HOME_FRAG = 111;
    public static final int LATEST_FRAG = 222;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        imageViewSearch = findViewById(R.id.imageViewHomeActivitySearch);
        textViewDawn = findViewById(R.id.textViewHomeActivityDawn);
        textViewTop = findViewById(R.id.texViewHomeActivityTop);
        textViewLatest = findViewById(R.id.texViewHomeActivityLatest);
        textViewFragmentName = findViewById(R.id.textViewHomeActivityFragmentName);


        categoriesHashMap = new HashMap<>();
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


        // coming from PostDetailsActivity // receive intent and show that fragment
        if (getIntent().getExtras() != null)
        {
            // position cum requestCode
            int position = getIntent().getExtras().getInt(Constants.position, HOME_FRAG); //
            Log.d(TAG, "onCreate: " + position);

            if (position == HOME_FRAG)
            {
                setFragmentAndTextView("HOME", new HomeFragment());
            } else if (position == LATEST_FRAG)
            {
                setFragmentAndTextView("latest", new LatestFragment());
            } else
            {
                setFragmentAndTextView(AdapterUtils.getKeyList(categoriesHashMap)
                        .get(position), AdapterUtils.getFragment(categoriesHashMap, position));
            } // else closed

        } // if closed

        // else  = if launching first time , show home fragment
        else
        {
            setFragmentAndTextView("Home", new HomeFragment());
        }


        imageViewSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SearchDialogueFragment dialog = new SearchDialogueFragment();
                dialog.show(getSupportFragmentManager(), "searchFragment");
            } //
        });


        // bottom toolbar Events  (BottomSheet Dialog also has the same buttons)
        textViewTop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setFragmentAndTextView("Home", new HomeFragment());
            }
        });
        textViewLatest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setFragmentAndTextView("latest", new LatestFragment());
            }
        });

        textViewDawn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                inflateBottomSheetDialogue();
            }
        });


    } // onCreate

    // method for setting fragment name at the top and replacing fragment
    public void setFragmentAndTextView(String text, Fragment fragment)
    {
        String fragName = text.toUpperCase();
        textViewFragmentName.setText(fragName);
        replaceFragment(fragment);

    } // setFragmentAndTexViewClosed

    private boolean replaceFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();
        return true;
    } /// replaceFragment

    // bottomSheet Dialogue for categories
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
        /// BottomSheetDialog Events
        TextView textViewDawn, textViewLatest, textViewTop;
        textViewDawn = dialog.findViewById(R.id.textViewLayoutCategoriesBottomSheetDawn);
        textViewLatest = dialog.findViewById(R.id.textViewLayoutCategoriesBottomSheetLatest);
        textViewTop = dialog.findViewById(R.id.textViewLayoutCategoriesBottomSheetTop);


        /// Setting hashMap is using dummy data for now
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
                setFragmentAndTextView("HOME", new HomeFragment());
                dialog.dismiss();
            }
        });
        textViewLatest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setFragmentAndTextView("latest", new LatestFragment());
                dialog.dismiss();
            }
        });

        gridViewCategories.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                replaceFragment(AdapterUtils.getFragment(categoriesHashMap, position));
                if (AdapterUtils.getKeyList(categoriesHashMap).get(position).toLowerCase().equals("top"))
                {
                    textViewFragmentName.setText("HOME");
                } else
                {
                    textViewFragmentName.setText(AdapterUtils.getKeyList(categoriesHashMap).get(position));
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    } // inflate dialogue closed


} // class closed