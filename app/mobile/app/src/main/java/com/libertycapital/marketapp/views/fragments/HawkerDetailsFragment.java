package com.libertycapital.marketapp.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import io.realm.Realm;
import io.realm.RealmAsyncTask;

import static com.libertycapital.marketapp.utils.GenUtils.getCharSequenceArrayAdapter;

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
    @BindView(R.id.spinnerBussinessType)
    Spinner spinnerBusinessType;
    @BindView(R.id.spinnerBusinessCategory)
    Spinner spinnerBusinessCategory;
    @BindView(R.id.editTextLandmark)
    EditText editTextLandmark;
    Realm mRealm;
    RealmAsyncTask realmAsyncTask;


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
       View view =  inflater.inflate(R.layout.fragment_hawker_details, container, false);
        ButterKnife.bind(this, view);
        mRealm = Realm.getDefaultInstance();
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<CharSequence> adapterBusinessType =
                getCharSequenceArrayAdapter(getActivity(),R.array.items_markets,
                        android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterBusinessCategory =
                getCharSequenceArrayAdapter(getActivity(),R.array.items_hawker,
                        android.R.layout.simple_spinner_item);

//        ArrayAdapter<CharSequence> adapterSection =
//                getCharSequenceArrayAdapter(getActivity(),R.array.items_section,
//                        android.R.layout.simple_spinner_item);
//
//        ArrayAdapter<CharSequence> adapterLane =
//                getCharSequenceArrayAdapter(getActivity(),R.array.items_lane,
//                        android.R.layout.simple_spinner_item);


        spinnerBusinessType.setAdapter(new HintSpinnerAdapter(
                adapterBusinessType, R.layout.hint_market, getContext()));

        spinnerBusinessCategory.setAdapter(new HintSpinnerAdapter(
                adapterBusinessCategory, R.layout.hint_hawker, getContext()));

//        spinnerSection.setAdapter(new HintSpinnerAdapter(
//                adapterSection, R.layout.hint_section, getContext()));
//
//        spinnerLane.setAdapter(new HintSpinnerAdapter(
//                adapterLane, R.layout.hint_lane, getContext()));
//

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
