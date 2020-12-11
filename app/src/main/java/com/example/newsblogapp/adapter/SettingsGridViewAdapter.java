package com.example.newsblogapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.newsblogapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsGridViewAdapter extends BaseAdapter
{
    private Context context;
    private List<String> SettingsList;
    private HashMap<String, Fragment> hashMap;

    public SettingsGridViewAdapter(Context context, HashMap<String, Fragment> hashMap)
    {
        this.context = context;
        this.hashMap = hashMap;
        this.SettingsList = AdapterUtils.getKeyList(hashMap);
    }

    @Override
    public int getCount()
    {
        return SettingsList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return SettingsList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_custom_settings_grid_view, parent, false);
        TextView textView = view.findViewById(R.id.textViewCustomSettingsGridViewSettingName);
        textView.setText(SettingsList.get(position));
        return view;
    }


} // class closed
