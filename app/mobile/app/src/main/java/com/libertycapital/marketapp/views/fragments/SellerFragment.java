package com.libertycapital.marketapp.views.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.libertycapital.marketapp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SellerFragment extends Fragment {

    public SellerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seller, container, false);
    }
}
