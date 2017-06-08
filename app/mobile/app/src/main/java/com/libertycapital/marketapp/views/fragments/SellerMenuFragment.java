package com.libertycapital.marketapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.libertycapital.marketapp.R;
import com.libertycapital.marketapp.views.activities.HawkerSellerACT;
import com.libertycapital.marketapp.views.activities.ShopSellerACT;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class SellerMenuFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.buttonRegisterShopSeller)
    Button buttonRegisterShopSeller;
    @BindView(R.id.buttonRegisterHawker)
    Button buttonRegisterHawker;

    public SellerMenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_menu, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonRegisterShopSeller.setOnClickListener(this);
        buttonRegisterHawker.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRegisterShopSeller:
                startActivity(new Intent(getActivity(), ShopSellerACT.class));
                break;
            case R.id.buttonRegisterHawker:
                startActivity(new Intent(getActivity(), HawkerSellerACT.class));
                break;
            default:


        }
    }
}
