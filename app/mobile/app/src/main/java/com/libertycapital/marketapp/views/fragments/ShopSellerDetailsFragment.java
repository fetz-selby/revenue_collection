package com.libertycapital.marketapp.views.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
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
import com.libertycapital.marketapp.models.SellerMDL;
import com.libertycapital.marketapp.utils.GenUtils;
import com.libertycapital.marketapp.views.adapters.HintSpinnerAdapter;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmAsyncTask;

import static com.libertycapital.marketapp.utils.GenUtils.getCharSequenceArrayAdapter;
import static com.libertycapital.marketapp.utils.GenUtils.getToastMessage;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopSellerDetailsFragment extends Fragment {
    @BindView(R.id.floatingActionButtonShopSeller)
    FloatingActionButton floatingActionButtonShopSeller;
    @BindView(R.id.spinnerMarket)
    Spinner spinnerMarket;
    @BindView(R.id.spinnerShopType)
    Spinner spinnerShopType;
    @BindView(R.id.editTextLandmark)
    EditText editTextLandmark;
    @BindView(R.id.textInputLayoutLandmark)
    TextInputLayout textInputLayoutLandmark;
    Realm mRealm;
    RealmAsyncTask realmAsyncTask;
    private boolean editTextLandmarkError;


    public ShopSellerDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_seller_details, container, false);
        ButterKnife.bind(this, view);
        mRealm = Realm.getDefaultInstance();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<CharSequence> adapterMarket =
                getCharSequenceArrayAdapter(getActivity(), R.array.items_markets,
                        android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterShopType =
                getCharSequenceArrayAdapter(getActivity(), R.array.items_shops,
                        android.R.layout.simple_spinner_item);


        spinnerMarket.setAdapter(new HintSpinnerAdapter(
                adapterMarket, R.layout.hint_market, getContext()));

        spinnerShopType.setAdapter(new HintSpinnerAdapter(
                adapterShopType, R.layout.hint_shop, getContext()));

        floatingActionButtonShopSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextLandmarkError= GenUtils.isEmpty(editTextLandmark, textInputLayoutLandmark, "Landmark required");


                if (spinnerMarket.getSelectedItem() == null && spinnerShopType.getSelectedItem() ==null && !editTextLandmarkError) {
                    getToastMessage(getContext(), "Please select a market and a shop");
                    getToastMessage(getContext(), "Landmark required");
                } else {
                    storeData();
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

    private void storeData() {
        realmAsyncTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SellerMDL sellerMDL = realm.where(SellerMDL.class).findAllSorted("createdDate").last();
                String id = UUID.randomUUID().toString();
                if (spinnerMarket.getSelectedItem().toString()!=null){
                    sellerMDL.setMarket(spinnerMarket.getSelectedItem().toString());
                }if (spinnerShopType.getSelectedItem().toString()!=null){
                    sellerMDL.setMarket(spinnerShopType.getSelectedItem().toString());
                }

                sellerMDL.setMarket(spinnerShopType.getSelectedItem().toString());

                sellerMDL.setLandmark(editTextLandmark.getText().toString());

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

                GenUtils.getToastMessage(getContext(), "Added successfully");

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                GenUtils.getToastMessage(getContext(), "Market and Store are required");
            }
        });
//        setViewpager(sellerMDL,mViewPager);

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
