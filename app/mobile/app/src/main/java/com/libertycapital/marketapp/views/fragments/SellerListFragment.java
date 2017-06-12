package com.libertycapital.marketapp.views.fragments;

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
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import io.realm.Realm;

/**
 * A placeholder fragment containing a simple view.
 */
public class SellerListFragment extends Fragment {
    private Realm realm;
    private SellerListAdapter sellerListAdapter;
    private RecyclerView recyclerView;


    public SellerListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        realm = Realm.getDefaultInstance();
        View view = inflater.inflate(R.layout.fragment_seller_list_act, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewSellerList);
        setUpRecyclerView();
        return view;
    }

    private void setUpRecyclerView() {
        sellerListAdapter = new SellerListAdapter(realm.where(SellerMDL.class).findAllAsync());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sellerListAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());
        recyclerView.addItemDecoration(new VerticalDividerItemDecoration.Builder(getActivity()).build());
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(getContext(), String.valueOf(position),Toast.LENGTH_LONG).show();
            }
        });

    }
}
