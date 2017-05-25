package com.libertycapital.marketapp.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.libertycapital.marketapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactDetailsFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactDetailsFormFragment extends Fragment {

    public ContactDetailsFormFragment() {
        // Required empty public constructor
    }


    public static ContactDetailsFormFragment newInstance(String param1, String param2) {
        ContactDetailsFormFragment fragment = new ContactDetailsFormFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_details_form, container, false);
        return view;
    }

}
