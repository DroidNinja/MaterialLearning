package com.binarywall.learning;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import java.util.ArrayList;

/**
 * Created by BANE on 6/13/2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<SampleModel> sampleModels;
    private Context context;
    public ViewPagerAdapter(FragmentManager fm, ArrayList<SampleModel> sampleModels, Context context) {
        super(fm);
        this.sampleModels=sampleModels;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        SampleModelFragment sampleModelFragment = SampleModelFragment.getInstance(sampleModels.get(position));
        return sampleModelFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {

//        //use the MrVector library to inflate vector drawable inside tab
        Drawable drawable = context.getResources().getDrawable(R.drawable.ic_android);
//        //set the size of drawable to 36 pixels
        drawable.setBounds(0, 0, 48, 48);
        ImageSpan imageSpan = new ImageSpan(drawable);
//        //to make our tabs icon only, set the Text as blank string with white space
        SpannableString spannableString;
        if(position != sampleModels.size()-1)
            spannableString = new SpannableString(String.valueOf(position+1) + "   ");
        else
            spannableString = new SpannableString("  ");
        spannableString.setSpan(imageSpan, spannableString.length()-1, spannableString.length(), 0);
        return spannableString;
    }

    @Override
    public int getCount() {
        return sampleModels.size();
    }
}
