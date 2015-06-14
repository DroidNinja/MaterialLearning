package com.binarywall.learning;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SampleModelFragment extends Fragment {

    private static final long ANIM_VIEWPAGER_DELAY = 5000;
    private static SampleModel mSampleModel;
    private Handler handler;
    private boolean stopSliding = false;
    private ViewPager viewPager;
    private Runnable animateViewPager;

    public SampleModelFragment()
    {
        super();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sample_model, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = (ViewPager) getView().findViewById(R.id.image_pager);
        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(mSampleModel.imageUrls, getActivity());
        viewPager.setAdapter(imagePagerAdapter);

        CirclePageIndicator circlePageIndicator = (CirclePageIndicator) getView().findViewById(R.id.indicator);
        circlePageIndicator.setViewPager(viewPager);

        runnable(mSampleModel.imageUrls.size());

    }

    public static SampleModelFragment getInstance(SampleModel sampleModel) {
        SampleModelFragment sampleModelFragment =  new SampleModelFragment();
        mSampleModel = sampleModel;
        return sampleModelFragment;
    }

    public void runnable(final int size) {
        handler = new Handler();
        animateViewPager = new Runnable() {
            public void run() {
                if (!stopSliding) {
                    if (viewPager.getCurrentItem() == size - 1) {
                        viewPager.setCurrentItem(0);
                    } else {
                        viewPager.setCurrentItem(
                                viewPager.getCurrentItem() + 1, true);
                    }
                    handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
                }
            }
        };
    }
}
