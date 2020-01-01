package com.example.tabhostgogo;


//import info.androidhive.imageslider.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

//import androidx.core.view.PagerAdapter;
//import androidx.core.view.ViewPager;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Tab2FullScreenImageAdapter extends PagerAdapter {
    Context context;
    String[] images;
    LayoutInflater inflater;

    public Tab2FullScreenImageAdapter(Context context, String[] images){
        this.context=context;
        this.images=images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.tab2_activity_full_screen_image, null);
        ImageView imageView=(ImageView) v.findViewById(R.id.fullScreenImageView);
        Glide.with(context).load(images[position]).centerCrop()
                .crossFade().into(imageView);
        ViewPager vp=(ViewPager) container;
        vp.addView(v,0);
        return v;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        ViewPager viewPager=(ViewPager) container;
        View v= (View) object;
        viewPager.removeView(v);
    }

}



