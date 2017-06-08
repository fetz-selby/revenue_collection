package com.libertycapital.marketapp.views.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.libertycapital.marketapp.R;
import com.libertycapital.marketapp.utils.GenUtils;
import com.libertycapital.marketapp.views.adapters.HintSpinnerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmAsyncTask;

import static com.libertycapital.marketapp.utils.GenUtils.getCharSequenceArrayAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessDetailsFormFragment extends Fragment {
    @BindView(R.id.floatingActionButtonBusiness)
    FloatingActionButton floatingActionButtonBusiness;
    @BindView(R.id.spinnerBussinessType)
    Spinner spinnerBusinessType;
    @BindView(R.id.spinnerBusinessCategory)
    Spinner spinnerBusinessCategory;
    @BindView(R.id.spinnerSection)
    Spinner spinnerSection;
    @BindView(R.id.spinnerLane)
    Spinner spinnerLane;
    @BindView(R.id.editTextLandmark)
    EditText editTextLandmark;
    Realm mRealm;
    RealmAsyncTask realmAsyncTask;



    public BusinessDetailsFormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_business_details_form, container, false);
        ButterKnife.bind(this, view);
        mRealm = Realm.getDefaultInstance();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<CharSequence> adapterBusinessType =
                 getCharSequenceArrayAdapter(getActivity(),R.array.items_bussiness_type,
                        android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterBusinessCategory =
                getCharSequenceArrayAdapter(getActivity(),R.array.items_bussiness_category,
                        android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterSection =
                getCharSequenceArrayAdapter(getActivity(),R.array.items_section,
                        android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterLane =
                getCharSequenceArrayAdapter(getActivity(),R.array.items_lane,
                        android.R.layout.simple_spinner_item);


        spinnerBusinessType.setAdapter(new HintSpinnerAdapter(
                adapterBusinessType, R.layout.hint_business_type, getContext()));

        spinnerBusinessCategory.setAdapter(new HintSpinnerAdapter(
                adapterBusinessCategory, R.layout.hint_business_category, getContext()));

        spinnerSection.setAdapter(new HintSpinnerAdapter(
                adapterSection, R.layout.hint_section, getContext()));

        spinnerLane.setAdapter(new HintSpinnerAdapter(
                adapterLane, R.layout.hint_lane, getContext()));


    }

//    public  ArrayAdapter<CharSequence> getCharSequenceArrayAdapter(int array, int layout) {
//        return ArrayAdapter.createFromResource(getActivity(), array, layout);
//    }

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
        mRealm.close();

    }

    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

//            switch (view.getId()) {
//                case R.id.editTextUsername:
//                    GenUtils.isEmpty(editTextUsername, textInputLayoutUsername, "Enter your username");
//                    break;
//                case R.id.editTextPassword:
//                    GenUtils.isEmpty(editTextPassword, textInputLayoutPassword, "Enter your password");
//                    break;
//                default:
//            }
        }
    }


}
