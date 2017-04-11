package com.effone.hostismeandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.effone.hostismeandroid.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MyPagerAdapter extends PagerAdapter {

    Context context;
    TextView tv1, tv2, readMore;
    ArrayList<Integer> podCasts;
    public static final String

            READ_MORE_HEADER = "read_more_header";
    public static final String

            READ_MORE_CONTENT = "read_more_content";
    private static final int

            IO_BUFFER_SIZE = 4 * 1024;
    ImageView webView;
    String url = "http://www.dot-combustion.com/clients/drupal/sites/default/files/pictures/";

    public MyPagerAdapter(Context context, ArrayList<Integer> podCasts) {
        super();
        this.context = context;
        this.podCasts = podCasts;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return podCasts.size();
    }

    public Object instantiateItem(ViewGroup collection, int pos) {
        LayoutInflater inflater =(LayoutInflater) collection.getContext().getSystemService

                        (Context.LAYOUT_INFLATER_SERVICE);
        final Integer podCast = podCasts.get

                (pos);
        View view = inflater.inflate

                (R.layout.fragment_hot_dish_home_page_image, null);
        webView = (ImageView) view.findViewById

                (R.id.iv_dish);

            webView.setImageResource(podCast);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup

                                    container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView

                ((View) object);
    }

    @Override
    public boolean isViewFromObject(View arg0,

                                    Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == ((View) arg1);
    }

    @Override
    public Parcelable saveState() {
        // TODO Auto-generated method stub
        return null;
    }


}

