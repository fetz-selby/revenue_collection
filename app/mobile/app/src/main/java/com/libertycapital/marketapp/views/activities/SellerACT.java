package com.libertycapital.marketapp.views.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;

import com.astuetz.PagerSlidingTabStrip;
import com.libertycapital.marketapp.R;
import com.libertycapital.marketapp.views.adapters.SellerViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SellerACT extends AppCompatActivity {
    @BindView(R.id.tabSeller)
    PagerSlidingTabStrip tabsStrip;
    @BindView(R.id.viewpagerSeller)
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_act);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        viewPager.setAdapter(new SellerViewPagerAdapter(getSupportFragmentManager()));

        tabsStrip.setViewPager(viewPager);
        tabsStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (viewPager.getCurrentItem() == 0) {
                        // Hide the keyboard.
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(viewPager.getWindowToken(), 0);
                    }
                }

            }
        });


    }


}


