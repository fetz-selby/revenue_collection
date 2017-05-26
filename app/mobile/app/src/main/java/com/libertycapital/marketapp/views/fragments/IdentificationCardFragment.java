package com.libertycapital.marketapp.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.libertycapital.marketapp.R;

import io.realm.Realm;
import io.realm.RealmAsyncTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IdentificationCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IdentificationCardFragment extends Fragment {
    Realm mRealm;
    RealmAsyncTask realmAsyncTask;



    public IdentificationCardFragment() {
        // Required empty public constructor
    }


    public static IdentificationCardFragment newInstance(String param1, String param2) {
        IdentificationCardFragment fragment = new IdentificationCardFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_identification_card, container, false);
        return view;
//      to set hint in spinner
//        https://android--code.blogspot.com/2015/08/android-spinner-hint.html

    }

}
