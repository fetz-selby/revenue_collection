package com.libertycapital.marketapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.libertycapital.marketapp.R;
import com.libertycapital.marketapp.views.activities.PaymentACT;
import com.libertycapital.marketapp.views.activities.SellerACT;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment {
    @BindView(R.id.buttonRegisterSeller)
    Button buttonRegisterSeller;
    @BindView(R.id.buttonCollectTaxes)
    Button buttonCollectTaxes;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        buttonRegisterSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SellerACT.class));

            }
        });
        buttonCollectTaxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PaymentACT.class));
            }
        });
        return view;
    }
}
