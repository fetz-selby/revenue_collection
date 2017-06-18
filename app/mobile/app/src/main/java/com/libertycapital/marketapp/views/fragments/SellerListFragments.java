package com.libertycapital.marketapp.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.libertycapital.marketapp.R;
import com.libertycapital.marketapp.models.SellerMDL;
import com.libertycapital.marketapp.utils.ItemClickSupport;
import com.libertycapital.marketapp.views.adapters.SellerListAdapter;

import io.realm.Realm;


public class SellerListFragments extends Fragment {
    private Realm realm;
    private SellerListAdapter sellerListAdapter;
    private RecyclerView recyclerView;



    private OnFragmentInteractionListener mListener;

    public SellerListFragments() {
        // Required empty public constructor
    }

    public static SellerListFragments newInstance(String param1, String param2) {
        SellerListFragments fragment = new SellerListFragments();
        Bundle args = new Bundle();

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
        View view = inflater.inflate(R.layout.fragment_seller_list, container, false);
        realm = Realm.getDefaultInstance();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewSellerList);
        setUpRecyclerView();
        return view;
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

    private void setUpRecyclerView() {
        sellerListAdapter = new SellerListAdapter(realm.where(SellerMDL.class).findAllAsync());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sellerListAdapter);
        recyclerView.setHasFixedSize(true);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(getContext(), String.valueOf(position),Toast.LENGTH_LONG).show();
            }
        });

    }
}
