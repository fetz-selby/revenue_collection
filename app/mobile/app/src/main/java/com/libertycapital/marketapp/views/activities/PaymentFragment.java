package com.libertycapital.marketapp.views.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.libertycapital.marketapp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PaymentFragment extends Fragment {

    public PaymentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }
}
