package com.libertycapital.marketapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.libertycapital.marketapp.R;
import com.libertycapital.marketapp.models.ContactMDL;
import com.libertycapital.marketapp.models.SellerMDL;
import com.libertycapital.marketapp.utils.GenUtils;
import com.libertycapital.marketapp.utils.ItemClickSupport;
import com.libertycapital.marketapp.views.activities.SellerProfileACT;
import com.libertycapital.marketapp.views.adapters.SellerListAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * A placeholder fragment containing a simple view.
 */
public class SellerListFragment extends Fragment {
    RealmResults<SellerMDL> results;
    private Realm realm;
    private SellerListAdapter sellerListAdapter;
    private RecyclerView recyclerView;
//    List<SellerMDL> sellerList;


    public SellerListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        realm = Realm.getDefaultInstance();
        View view = inflater.inflate(R.layout.fragment_seller_list_act, container, false);
        setHasOptionsMenu(true);
        setRecyclerView(view);

        return view;
    }

    private void setRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewSellerList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());
        recyclerView.addItemDecoration(new VerticalDividerItemDecoration.Builder(getActivity()).build());
        List<SellerMDL> sellerMDLs = setData();

        sellerListAdapter = new SellerListAdapter(getActivity(), realm, sellerMDLs);
        recyclerView.setAdapter(sellerListAdapter);
        GenUtils.getToastMessage(getContext(), sellerMDLs.size() + "");

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                GenUtils.getToastMessage(getContext(), String.valueOf(position));

                setSellerIntent(position, v);
            }
        });
    }

    private void setSellerIntent(int position, View v) {
        Intent intentSeller = new Intent(v.getContext(), SellerProfileACT.class);
        String sellerId = results.get(position).getId();
        intentSeller.putExtra(getString(R.string.seller_id_key), sellerId);
        startActivity(intentSeller);
    }

    private List<SellerMDL> setData() {
        results = realm.where(SellerMDL.class).findAll();

        results = realm.where(SellerMDL.class).findAllSorted("createdDate", Sort.DESCENDING);
        return results;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_seller_list_act, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                sellerListAdapter.setFilter(setQuery(query));

                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {

                sellerListAdapter.setFilter(setQuery(newText));

                return true;
            }
        });
    }

    private RealmResults setQuery(String query) {


        results = realm.where(SellerMDL.class)
                .contains("type", query, Case.INSENSITIVE)
                .or()
                .contains("lastname", query, Case.INSENSITIVE)
                .or()
                .contains("surname", query, Case.INSENSITIVE)
                .or()
                .contains("market", query, Case.INSENSITIVE)
                .or()
                .contains("landmark", query, Case.INSENSITIVE)
                .or()
                .contains("phone",query, Case.INSENSITIVE)
                .findAll();
        return results;
    }

}
