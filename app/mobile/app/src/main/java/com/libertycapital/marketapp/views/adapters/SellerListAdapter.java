package com.libertycapital.marketapp.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.libertycapital.marketapp.R;
import com.libertycapital.marketapp.models.SellerMDL;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by root on 6/11/17.
 */

public class SellerListAdapter extends RealmRecyclerViewAdapter<SellerMDL, SellerListAdapter.SellerListListViewHolder> {
    public SellerListAdapter(OrderedRealmCollection<SellerMDL> data) {
        super(data, true);
    }

    @Override
    public SellerListListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_seller, parent, false);
        return new SellerListListViewHolder(v);
    }


    @Override
    public void onBindViewHolder(SellerListListViewHolder holder, int position) {
        final SellerMDL sellerMDL = getData().get(position);
        holder.data = sellerMDL;

        StringBuilder sb = new StringBuilder();
        sb.append(sellerMDL.getFirstname());
        sb.append(" ");
        sb.append(sellerMDL.getLastname());
        holder.sellerName.setText(sb.toString());
        holder.sellerType.setText(sellerMDL.getSellerType());


    }

    public class SellerListListViewHolder extends RecyclerView.ViewHolder {
        public SellerMDL data;
        TextView sellerName;
        TextView sellerType;
        Button buttonViewSellerDetail;

        public SellerListListViewHolder(View itemView) {
            super(itemView);
            sellerName = (TextView) itemView.findViewById(R.id.textViewSellerName);
            sellerType = (TextView) itemView.findViewById(R.id.textViewSellerType);
            buttonViewSellerDetail = (Button) itemView.findViewById(R.id.buttonViewDetails);

        }
    }
}
