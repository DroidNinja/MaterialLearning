package com.binarywall.learning;

import android.content.res.Configuration;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private View mLeftDrawerView;
    private View mRightDrawerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private View mainContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<SampleModel> sampleModels = new ArrayList<SampleModel>();
        for(int i=0;i<4;i++)
        {
            SampleModel sampleModel = new SampleModel();
            sampleModel.name = "Sample " + i;
            sampleModel.imageUrls = new ArrayList<>();
                for(int j=0;j<3;j++)
                {
                    String imageUrl = "http://lorempixel.com/400/200";
                    sampleModel.imageUrls.add(imageUrl);
                }
            sampleModels.add(sampleModel);
        }

        initSlidingTabs(sampleModels);
        initNavigationDrawer();
    }

    private void initNavigationDrawer() {

    }

    private void initSlidingTabs(ArrayList<SampleModel> sampleModels) {
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), sampleModels, this);
        viewPager.setAdapter(pagerAdapter);

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        slidingTabLayout.setCustomTabView(R.layout.custom_tab_view, R.id.tabtitle);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        slidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
        slidingTabLayout.setViewPager(viewPager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initCustomActionBar();
        if(mDrawerLayout == null || mLeftDrawerView == null || mRightDrawerView == null || mDrawerToggle == null) {
            // Configure navigation drawer
            mainContent = findViewById(R.id.mainContent);
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mLeftDrawerView = findViewById(R.id.leftDrawerView);
            mRightDrawerView = findViewById(R.id.rightDrawerView);
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

                /** Called when a drawer has settled in a completely closed state. */
                public void onDrawerClosed(View drawerView) {
                    if(drawerView.equals(mLeftDrawerView)) {
                        getSupportActionBar().setTitle(getTitle());
                        supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                        mDrawerToggle.syncState();
                    }
                }

                /** Called when a drawer has settled in a completely open state. */
                public void onDrawerOpened(View drawerView) {
                    if(drawerView.equals(mLeftDrawerView)) {
                        getSupportActionBar().setTitle(getString(R.string.app_name));
                        supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                        mDrawerToggle.syncState();
                    }
                }

                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    // Avoid normal indicator glyph behaviour. This is to avoid glyph movement when opening the right drawer
                    super.onDrawerSlide(drawerView, slideOffset);
                    if(drawerView == mRightDrawerView) {
                        mainContent.setTranslationX((-slideOffset) * drawerView.getWidth());
                        mDrawerLayout.bringChildToFront(drawerView);
                        mDrawerLayout.requestLayout();
                    }
                }
            };

            mDrawerLayout.setDrawerListener(mDrawerToggle); // Set the drawer toggle as the DrawerListener
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }


    private void initCustomActionBar() {

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // If the nav drawer is open, hide action items related to the content view
        for(int i = 0; i< menu.size(); i++)
            menu.getItem(i).setVisible(!mDrawerLayout.isDrawerOpen(mLeftDrawerView));

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                mDrawerToggle.onOptionsItemSelected(item);

                if(mDrawerLayout.isDrawerOpen(mRightDrawerView))
                    mDrawerLayout.closeDrawer(mRightDrawerView);

                return true;
            case R.id.action_settings:
                if(!mDrawerLayout.isDrawerOpen(mRightDrawerView))
                    mDrawerLayout.openDrawer(mRightDrawerView);
                else
                    mDrawerLayout.closeDrawer(mRightDrawerView);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
