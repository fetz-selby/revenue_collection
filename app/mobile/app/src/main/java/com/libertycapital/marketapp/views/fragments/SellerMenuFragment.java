package com.libertycapital.marketapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.libertycapital.marketapp.R;
import com.libertycapital.marketapp.models.SellerMDL;
import com.libertycapital.marketapp.models.UserMDL;
import com.libertycapital.marketapp.views.activities.HawkerSellerACT;
import com.libertycapital.marketapp.views.activities.ShopSellerACT;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmAsyncTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class SellerMenuFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.buttonRegisterShopSeller)
    Button buttonRegisterShopSeller;
    @BindView(R.id.buttonRegisterHawker)
    Button buttonRegisterHawker;
    private Realm mRealm;
    private RealmAsyncTask realmAsyncTask;

    public SellerMenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_menu, container, false);
        mRealm = Realm.getDefaultInstance();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonRegisterShopSeller.setOnClickListener(this);
        buttonRegisterHawker.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRegisterShopSeller:
                final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                final Date date = new Date();
                final String id = UUID.randomUUID().toString();
                realmAsyncTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        SellerMDL sellerMDL = realm.createObject(SellerMDL.class, id);
                        UserMDL userMDL = realm.where(UserMDL.class).findFirst();
                        if (userMDL != null) {
                            sellerMDL.setCreatedBy(userMDL);
                        }
                        sellerMDL.setSellerType(getString(R.string.shop_seller));

                        sellerMDL.setCreatedDate(dateFormat.format(date));
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getContext(), "Added successfully", Toast.LENGTH_SHORT).show();

                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }


                });
                startActivity(new Intent(getActivity(), ShopSellerACT.class).putExtra("sellerId", id));
//                startActivity(new Intent(getActivity(), ShopSellerACT.class));
                break;
            case R.id.buttonRegisterHawker:
                startActivity(new Intent(getActivity(), HawkerSellerACT.class));
                break;
            default:


        }
    }
}
