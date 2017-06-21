package com.libertycapital.marketapp.views.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.libertycapital.marketapp.R;
import com.libertycapital.marketapp.models.SellerMDL;
import com.libertycapital.marketapp.utils.GenUtils;
import com.libertycapital.marketapp.views.adapters.HawkerViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmAsyncTask;

public class HawkerSellerACT extends AppCompatActivity {

    public static ViewPager viewPager;
    @BindView(R.id.tabSeller)
    PagerSlidingTabStrip tabsStrip;
    int currentPage;
    RelativeLayout dummyView;
    Realm realm;
    RealmAsyncTask realmAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_act);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        realm = Realm.getDefaultInstance();
        ButterKnife.bind(this);
        viewPager = (ViewPager) findViewById(R.id.viewpagerSeller);
        viewPager.setAdapter(new HawkerViewPagerAdapter(getSupportFragmentManager()));
        dummyView = (RelativeLayout) findViewById(R.id.dummyView);

        dummyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Just leave this empty
            }
        });
        tabsStrip.setViewPager(viewPager);
        tabsStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

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

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            deleteData();
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        deleteData();
        GenUtils.getToastMessage(HawkerSellerACT.this, "CIAO");

    }

    private void deleteData() {
        realmAsyncTask = realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SellerMDL sellerMDL = realm.where(SellerMDL.class).findAllSorted("createdDate").last();
                if (sellerMDL.getFirstname() == null && sellerMDL.getSurname() == null || sellerMDL.getMarket() == null) {
                    sellerMDL.deleteFromRealm();
                }


            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

                GenUtils.getToastMessage(HawkerSellerACT.this, "delete successfully");

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                GenUtils.getToastMessage(HawkerSellerACT.this, error.getMessage());
            }
        });
//        setViewpager(sellerMDL,mViewPager);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (realmAsyncTask != null && !realmAsyncTask.isCancelled()) {
            realmAsyncTask.cancel();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();

    }


}


