package com.libertycapital.marketapp.views.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.libertycapital.marketapp.R;
import com.libertycapital.marketapp.utils.GenUtils;
import com.libertycapital.marketapp.views.activities.SellerACT;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmAsyncTask;


public class PersonalDetailsFormFragment extends Fragment {
    @BindView(R.id.editTextFname)
    EditText editTextFname;
    @BindView(R.id.editTextLname)
    EditText editTextLname;
    @BindView(R.id.editTextOname)
    EditText editTextOname;
    @BindView(R.id.textInputLayoutFname)
    TextInputLayout textInputLayoutFname;
    @BindView(R.id.textInputLayoutLname)
    TextInputLayout textInputLayoutLname;
    @BindView(R.id.textInputLayoutOname)
    TextInputLayout textInputLayoutOname;
    @BindView(R.id.floatingActionButtonPdetails)
    FloatingActionButton floatingActionButtonPDetails;
    Realm mRealm;
    RealmAsyncTask realmAsyncTask;
    private boolean editTextLnameError, editTextFnameError;
    static ViewPager mViewPager = SellerACT.viewPager;



    public PersonalDetailsFormFragment() {
        // Required empty public constructor
    }


    public static PersonalDetailsFormFragment newInstance(String param1, String param2) {
        PersonalDetailsFormFragment fragment = new PersonalDetailsFormFragment();
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
        View view = inflater.inflate(R.layout.fragment_personal_details_form, container, false);
        ButterKnife.bind(this, view);
        mRealm= Realm.getDefaultInstance();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        floatingActionButtonPDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextFnameError = GenUtils.isEmpty(editTextFname, textInputLayoutFname, "Firstname required");
                editTextLnameError = GenUtils.isEmpty(editTextLname, textInputLayoutLname, "Lastname required");


                if (!(editTextFnameError && editTextLnameError)) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                } else {

//To go to the next page
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);

                }

            }
        });
    }

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

            switch (view.getId()) {
                case R.id.editTextFname:
                    GenUtils.isEmpty(editTextFname, textInputLayoutFname, "Firstname  required");
                    break;
                case R.id.editTextLname:
                    GenUtils.isEmpty(editTextLname, textInputLayoutLname, "Lastname  required");
                    break;

                default:
            }
        }
    }


}
