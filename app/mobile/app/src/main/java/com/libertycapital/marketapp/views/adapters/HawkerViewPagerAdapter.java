package com.libertycapital.marketapp.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.libertycapital.marketapp.views.fragments.ContactDetailsFormFragment;
import com.libertycapital.marketapp.views.fragments.FinalStepFragment;
import com.libertycapital.marketapp.views.fragments.HawkerDetailsFragment;
import com.libertycapital.marketapp.views.fragments.MapBusinessFragment;
import com.libertycapital.marketapp.views.fragments.PersonalDetailsFormFragment;

import java.util.List;

/**
 * Created by root on 5/31/17.
 */

public class HawkerViewPagerAdapter extends SmartFragmentStatePagerAdapter {

    private SmartFragmentStatePagerAdapter adapterViewPager;


    private String tabTitles[] = new String[]{"Personal", "Contact", "Business", "Location","Submit"};
    //in PagerAdapter:
    private List<Fragment> fragmentList;

    public HawkerViewPagerAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PersonalDetailsFormFragment();
//            case 1:
//                return new IdentificationCardFragment();
            case 1:
                return new ContactDetailsFormFragment();
            case 2:
                return new HawkerDetailsFragment();
            case 3:
                return new MapBusinessFragment();
            case 4:
                return new FinalStepFragment();


        }
        return null;
//        return PersonalDetailsFormFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

//    http://stackoverflow.com/questions/38760052/how-to-save-edittext-value-in-shared-preferences-when-user-swipe-fragment-in-vie
//    http://stackoverflow.com/questions/35476874/android-saving-form-data-on-a-swipe-rather-than-from-a-button
//    http://stackoverflow.com/questions/40745636/how-to-save-viewpager-form-data-onswipe-to-next-pager-android

    public Fragment getFragment(int position) {

        return fragmentList.get(position);
    }
}