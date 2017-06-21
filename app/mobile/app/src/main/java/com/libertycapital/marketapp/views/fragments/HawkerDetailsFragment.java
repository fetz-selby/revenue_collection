package com.libertycapital.marketapp.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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
 * Activities that contain this fragment must implement the
 * {@link HawkerDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HawkerDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HawkerDetailsFragment extends Fragment {

    @BindView(R.id.floatingActionButtonBusiness)
    FloatingActionButton floatingActionButtonBusiness;
    @BindView(R.id.spinnerMarket)
    Spinner spinnerMarket;
    @BindView(R.id.spinnerHawkerType)
    Spinner spinnerHawkerType;
    @BindView(R.id.editTextLandmark)
    EditText editTextLandmark;
    @BindView(R.id.textInputLayoutLandmark)
    TextInputLayout textInputLayoutLandmark;
    @BindView(R.id.checkBoxSellingOnTable)
    CheckBox checkBoxSellingOnTable;
    @BindView(R.id.checkBoxSellingOnTheFloor)
    CheckBox getCheckBoxSellingOnTheFloor;
    Realm mRealm;
    RealmAsyncTask realmAsyncTask;
    private boolean editTextLandmarkError;


    private OnFragmentInteractionListener mListener;

    public HawkerDetailsFragment() {
        // Required empty public constructor
    }


    public static HawkerDetailsFragment newInstance(String param1, String param2) {
        HawkerDetailsFragment fragment = new HawkerDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_hawker_details, container, false);
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

        ArrayAdapter<CharSequence> adapterHawkerType =
                getCharSequenceArrayAdapter(getActivity(), R.array.items_hawker,
                        android.R.layout.simple_spinner_item);


        spinnerMarket.setAdapter(new HintSpinnerAdapter(
                adapterMarket, R.layout.hint_market, getContext()));

        spinnerHawkerType.setAdapter(new HintSpinnerAdapter(
                adapterHawkerType, R.layout.hint_hawker, getContext()));

        floatingActionButtonBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextLandmarkError = GenUtils.isEmpty(editTextLandmark, textInputLayoutLandmark, "Landmark required");

                if (spinnerMarket.getSelectedItem() == null && spinnerHawkerType.getSelectedItem() == null && !editTextLandmarkError) {
                    getToastMessage(getContext(), "Please select a market and a hawker category");
                    getToastMessage(getContext(), "Landmark required");

                } else {
                    storeData();
                }
            }
        });


    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void storeData() {
        realmAsyncTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SellerMDL sellerMDL = realm.where(SellerMDL.class).findAllSorted("createdDate").last();
                String id = UUID.randomUUID().toString();
                if (spinnerMarket.getSelectedItem().toString() != null) {
                    sellerMDL.setMarket(spinnerMarket.getSelectedItem().toString());
                }
                if (spinnerHawkerType.getSelectedItem().toString() != null) {
                    sellerMDL.setSellerCategory(spinnerHawkerType.getSelectedItem().toString());
                }

                sellerMDL.setMarket(spinnerHawkerType.getSelectedItem().toString());

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
                GenUtils.getToastMessage(getContext(), "Market and hawker type  are required");
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
