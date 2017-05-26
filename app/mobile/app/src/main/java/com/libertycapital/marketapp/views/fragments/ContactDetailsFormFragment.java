package com.libertycapital.marketapp.views.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.libertycapital.marketapp.R;
import com.libertycapital.marketapp.views.adapters.HintSpinnerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.libertycapital.marketapp.utils.GenUtils.getCharSequenceArrayAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactDetailsFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactDetailsFormFragment extends Fragment {
    @BindView(R.id.spinnerMobileMoneyChoice)
    Spinner spinnerMobileMoney;
    @BindView(R.id.textInputLayoutMobileMoney)
    TextInputLayout textInputLayoutPhone;
    @BindView(R.id.editTextMobileMoneyNumber)
    EditText editTextPhone;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_details_form, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayAdapter<CharSequence> adapterNetwork =
                getCharSequenceArrayAdapter(getActivity(), R.array.items_mobile_network,
                        android.R.layout.simple_spinner_item);
        spinnerMobileMoney.setAdapter(new HintSpinnerAdapter(
                adapterNetwork, R.layout.hint_mobile_money, getContext()));


    }
}
