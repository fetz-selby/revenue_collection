package com.libertycapital.marketapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.libertycapital.marketapp.R;
import com.libertycapital.marketapp.models.SellerMDL;
import com.libertycapital.marketapp.models.UserMDL;
import com.libertycapital.marketapp.views.activities.PaymentACT;
import com.libertycapital.marketapp.views.activities.SellerMenuACT;
import com.libertycapital.marketapp.views.activities.SellerProfileACT;
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
public class HomeFragment extends Fragment {
    @BindView(R.id.buttonRegisterSeller)
    Button buttonRegisterSeller;
    @BindView(R.id.buttonCollectTaxes)
    Button buttonCollectTaxes;
    private Realm mRealm;
    private RealmAsyncTask realmAsyncTask;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        mRealm = Realm.getDefaultInstance();
        buttonRegisterSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SellerMenuACT.class));
//                final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//                final Date date = new Date();
//                final String id = UUID.randomUUID().toString();
//                realmAsyncTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
//                    @Override
//                    public void execute(Realm realm) {
//
//                        SellerMDL sellerMDL = realm.createObject(SellerMDL.class, id);
//                        UserMDL userMDL = mRealm.where(UserMDL.class).findFirst();
//                        if (userMDL != null) {
//                            sellerMDL.setCreatedBy(userMDL);
//                        }
//
//                        sellerMDL.setCreatedDate(dateFormat.format(date));
//                    }
//                }, new Realm.Transaction.OnSuccess() {
//                    @Override
//                    public void onSuccess() {
//                        Toast.makeText(getContext(), "Added successfully", Toast.LENGTH_SHORT).show();
//
//                    }
//                }, new Realm.Transaction.OnError() {
//                    @Override
//                    public void onError(Throwable error) {
//                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
//
//                    }
//
//
//                });
//                startActivity(new Intent(getActivity(), ShopSellerACT.class).putExtra("sellerId", id));


            }
        });
        buttonCollectTaxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SellerProfileACT.class));
            }
        });
        return view;
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
}
