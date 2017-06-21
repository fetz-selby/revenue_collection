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

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by root on 6/11/17.
 */

public class SellerListAdapter extends RecyclerView.Adapter<SellerListAdapter.SellerListListViewHolder> {
    Context context;
    List<SellerMDL> sellers;
    Realm realm;

    public SellerListAdapter(Context context, Realm realm, List<SellerMDL> sellers) {
        this.context = context;
        this.realm = realm;
        this.sellers = sellers;

    }

    @Override
    public SellerListListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_seller, parent, false);
        context = parent.getContext();
        return new SellerListListViewHolder(v);
    }


    //
//    @Override
//    public void onBindViewHolder(SubCategoryViewHolder subCategoryViewHolder, int position) {
//        final SubCategoryViewHolder mvh = subCategoryViewHolder;
//        final SubCategoryModel subCategory = getItem(position);
//        subCategoryViewHolder.mtxtSubCategoryId.setText(subCategory.getId());
//        subCategoryViewHolder.mtxtSubCategoryName.setText(subCategory.getName());
//        subCategoryViewHolder.mtxtSubCategoryPosition.setText(String.valueOf(position + 1) );
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return subCategories.size();
//    }
//
//    public void setFilter( List<SubCategoryModel> subCategoriesFiltered ) {
//        subCategories = new ArrayList<>();
//        subCategories.addAll(subCategoriesFiltered);
//        notifyDataSetChanged();
//    }
    public SellerMDL getItem(int position) {
        if (this.sellers == null || this.sellers.get(position) == null) {
            return null;
        }
        return this.sellers.get(position);
    }


    @Override
    public void onBindViewHolder(SellerListListViewHolder holder, int position) {
        final SellerListListViewHolder mvh = holder;
        final SellerMDL sellerMDL = getItem(position);
//        holder.data = sellerMDL;

        StringBuilder sb = new StringBuilder();
        sb.append(sellerMDL.getFirstname());
        sb.append(" ");
        sb.append(sellerMDL.getSurname());
        holder.sellerName.setText(sb.toString());
        holder.sellerType.setText(sellerMDL.getSellerType());
        ImageView imageView = holder.imageViewProfile;
//        holder.imageViewProfile;

        Glide.with(context)
                .load(sellerMDL.getPhotoUri())
                .placeholder(R.drawable.ic_avatar)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return sellers.size();
    }

//    @Override
//    public void onBindViewHolder(SubCategoryViewHolder subCategoryViewHolder, int position) {
//        final SubCategoryViewHolder mvh = subCategoryViewHolder;
//        final SubCategoryModel subCategory = getItem(position);
//        subCategoryViewHolder.mtxtSubCategoryId.setText(subCategory.getId());
//        subCategoryViewHolder.mtxtSubCategoryName.setText(subCategory.getName());
//        subCategoryViewHolder.mtxtSubCategoryPosition.setText(String.valueOf(position + 1) );
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return subCategories.size();
//    }

    //    @Override
//    public Filter getFilter() {
//        SellerFilter filter = new SellerFilter(this);
//        return filter;
//    }
//
//    public void filterResults(String text) {
////        text = text == null ? null : text.toLowerCase().trim();
////        if(text == null || "".equals(text)) {
////            updateData(realm.where(SellerMDL.class).findAll());
////        } else {
//            updateData(realm.where(SellerMDL.class)
//                    .contains("sellerType", text, Case.INSENSITIVE) // TODO: change field
//                    .findAll());
////        }
//    }
//    private class SellerFilter
//            extends Filter {
//        private final SellerListAdapter adapter;
//
//        private SellerFilter(SellerListAdapter adapter) {
//            super();
//            this.adapter = adapter;
//        }
//
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            return new FilterResults();
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            adapter.filterResults(constraint.toString());
//        }
//    }
//
    public void setFilter(List<SellerMDL> sellersFiltered) {
        sellers = new ArrayList<>();
        sellers.addAll(sellersFiltered);
        notifyDataSetChanged();
    }


//    public void filter(String text) {
//        items.clear();
//        if(text.isEmpty()){
//            items.addAll(itemsCopy);
//        } else{
//            text = text.toLowerCase();
//            for(PhoneBookItem item: itemsCopy){
//                if(item.name.toLowerCase().contains(text) || item.phone.toLowerCase().contains(text)){
//                    items.add(item);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }

//    public void setFilter( List<SubCategoryModel> subCategoriesFiltered ) {
//        subCategories = new ArrayList<>();
//        subCategories.addAll(subCategoriesFiltered);
//        notifyDataSetChanged();
//    }

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
