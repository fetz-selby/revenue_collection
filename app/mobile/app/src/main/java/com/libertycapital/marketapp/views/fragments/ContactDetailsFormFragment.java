package com.libertycapital.marketapp.views.fragments;


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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.libertycapital.marketapp.R;
import com.libertycapital.marketapp.models.ContactMDL;
import com.libertycapital.marketapp.models.SellerMDL;
import com.libertycapital.marketapp.utils.GenUtils;
import com.libertycapital.marketapp.views.adapters.HintSpinnerAdapter;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmList;

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
    @BindView(R.id.checkBoxIsMobileMoney)
    CheckBox checkBoxIsMobileMoney;
    @BindView(R.id.floatingActionButtonSubmitContact)
    FloatingActionButton floatingActionButtonContact;
    Realm mRealm;
    RealmAsyncTask realmAsyncTask;
    private boolean isMobileMoney, editTextPhoneError;


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
        mRealm = Realm.getDefaultInstance();
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
        spinnerMobileMoney.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//               mobileNetwork =   parent.getItemAtPosition(position +1).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        checkBoxIsMobileMoney.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isMobileMoney = isChecked;

            }
        });

        floatingActionButtonContact.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                editTextPhoneError = GenUtils.isEmpty(editTextPhone, textInputLayoutPhone, "Phone required");

                if (!(editTextPhoneError)) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                } else {

                    StoreData();

                }
            }
        });


    }

    private void StoreData() {
        realmAsyncTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SellerMDL sellerMDL = realm.where(SellerMDL.class).findAllSorted("createdDate").last();
                sellerMDL.setPhone(editTextPhone.getText().toString());
                sellerMDL.setMobileNetwork(spinnerMobileMoney.getSelectedItem().toString());
                sellerMDL.setMobileMoney(isMobileMoney);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

                GenUtils.getToastMessage(getContext(), "Added successfully");

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                GenUtils.getToastMessage(getContext(), error.getMessage());
            }
        });
//        setViewpager(sellerMDL,mViewPager);

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
                    GenUtils.isEmpty(editTextPhone, textInputLayoutPhone, "Phone  required");
                    break;

                default:
            }
        }
    }
}
