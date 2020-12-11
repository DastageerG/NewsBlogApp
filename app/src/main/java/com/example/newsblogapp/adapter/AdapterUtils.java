package com.example.newsblogapp.adapter;

import android.text.format.DateUtils;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AdapterUtils
{
    public static final long AVERAGE_MONTH_IN_MILLIS = DateUtils.DAY_IN_MILLIS * 30;

    public static String getRelationTime(long time)
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
    } // getRelation Time closed


    // get key List from HashMap
    public static List<String> getKeyList(HashMap<String, Fragment> hashMap)
    {
        List<String> tempList = new ArrayList<>();

        for (String key : hashMap.keySet())
        {
            tempList.add(key); // key is the category Name
        } // for closed

        return tempList;
    } //  get Categories closed


    public static Fragment getFragment(HashMap<String, Fragment> categoriesHashMap, int position)
    {
        List<Fragment> fragmentList = new ArrayList<>();
        for (Fragment fragment : categoriesHashMap.values())
        {

            fragmentList.add(fragment);
        }
        return fragmentList.get(position); // return fragment fro hashMap
    } // getFragment closed


}
