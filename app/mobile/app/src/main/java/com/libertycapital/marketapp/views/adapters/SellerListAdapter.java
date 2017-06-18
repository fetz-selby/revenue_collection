package com.libertycapital.marketapp.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.libertycapital.marketapp.R;
import com.libertycapital.marketapp.models.SellerMDL;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by root on 6/11/17.
 */

public class SellerListAdapter extends RealmRecyclerViewAdapter<SellerMDL, SellerListAdapter.SellerListListViewHolder> {
    Context context;
    public SellerListAdapter(OrderedRealmCollection<SellerMDL> data) {
        super(data, true);
    }


    @Override
    public SellerListListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_seller, parent, false);
        context = parent.getContext();
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
        ImageView imageView = holder.imageViewProfile;
//        holder.imageViewProfile;

        Glide.with(context)
                .load(sellerMDL.getPhotoUri())
                .placeholder(R.drawable.ic_avatar)
                .into(imageView);


    }

    public class SellerListListViewHolder extends RecyclerView.ViewHolder {
        public SellerMDL data;
        TextView sellerName;
        TextView sellerType;
        ImageView imageViewProfile;
//        Button buttonViewSellerDetail;

        public SellerListListViewHolder(View itemView) {
            super(itemView);
            sellerName = (TextView) itemView.findViewById(R.id.textViewSellerName);
            sellerType = (TextView) itemView.findViewById(R.id.textViewSellerType);
            imageViewProfile = (ImageView) itemView.findViewById(R.id.imageViewProfile);
//            buttonViewSellerDetail = (Button) itemView.findViewById(R.id.buttonViewDetails);

        }
    }
}
