package com.libertycapital.marketapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.libertycapital.marketapp.R;
import com.libertycapital.marketapp.views.activities.SellerListACT;
import com.libertycapital.marketapp.views.activities.SellerMenuACT;
import com.libertycapital.marketapp.views.activities.SellerProfileACT;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment {
    public static final String HOMETAG = HomeFragment.class.getName();
    @BindView(R.id.buttonRegisterSeller)
    Button buttonRegisterSeller;
    @BindView(R.id.buttonCollectTaxes)
    Button buttonCollectTaxes;
    @BindView(R.id.buttonSellerList)
    Button buttonSellerList;
    private Realm mRealm;



    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        mRealm = Realm.getDefaultInstance();

        buttonRegisterSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SellerMenuACT.class));



            }
        });
        buttonCollectTaxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SellerProfileACT.class));
            }
        });
        buttonSellerList = (Button) view.findViewById(R.id.buttonSellerList);

        buttonSellerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SellerListACT.class));
            }

        });


        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();

    }
}
