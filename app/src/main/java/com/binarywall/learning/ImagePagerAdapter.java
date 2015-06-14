package com.binarywall.learning;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by BANE on 6/14/2015.
 */
public class ImagePagerAdapter extends PagerAdapter {

    private ArrayList<String> imageUrls;
    private Context context;
    public ImagePagerAdapter(ArrayList<String> imageUrls, Context context) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.image_container,container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        Picasso.with(context)
                .load(imageUrls.get(position))
                .placeholder(R.drawable.ic_android)
                .error(R.mipmap.ic_launcher)
                .into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
