package com.libertycapital.marketapp.views.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.DatabaseErrorHandler;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.libertycapital.marketapp.R;
import com.libertycapital.marketapp.models.PaymentMDL;
import com.libertycapital.marketapp.models.SellerMDL;
import com.libertycapital.marketapp.models.UserMDL;
import com.libertycapital.marketapp.utils.Constants;
import com.libertycapital.marketapp.utils.GenUtils;
import com.libertycapital.marketapp.utils.VolleyRequests;
import com.libertycapital.marketapp.views.activities.SellerListACT;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmAsyncTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class SellerProfileFragment extends Fragment {
    public static final String TAG = FinalStepFragment.class.getName();
    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewSellerType)
    TextView textViewSellerType;
    @BindView(R.id.textViewPhone)
    TextView textViewPhone;
    @BindView(R.id.textViewNetwork)
    TextView textViewNetwork;
    @BindView(R.id.textViewMarket)
    TextView textViewMarket;
    @BindView(R.id.textViewLandmark)
    TextView textViewLandmark;

    @BindView(R.id.buttonDailyTax)
    Button buttonDailyTax;
    @BindView(R.id.buttonYearlyTax)
    Button buttonYearlyTax;

    Realm realm;
    RealmAsyncTask realmAsyncTask;
    @BindView(R.id.progressBarSubmit)
    ProgressBar mProgressBar;
    VolleyRequests mVolleyRequest;
    SellerMDL seller;
    Bundle b;
    ProgressDialog progress;
    double longitude, latitude;
    public SellerProfileFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_profile_act, container, false);
        realm = Realm.getDefaultInstance();
        ButterKnife.bind(this, view);
        mProgressBar.setVisibility(View.INVISIBLE);
        mVolleyRequest = new VolleyRequests(getActivity());
        Intent intentCategory = getActivity().getIntent();
        b = intentCategory.getExtras();
        GenUtils.getToastMessage(getContext(), b.getString(getString(R.string.seller_id_key)));
        seller = realm.where(SellerMDL.class).equalTo("id", b.getString(getString(R.string.seller_id_key))).findFirst();
        textViewName.setText(seller.getFirstname() + " " + seller.getSurname());
        getActivity().setTitle(seller.getFirstname() + " " + seller.getSurname());
        textViewSellerType.setText(seller.getSellerType());
        textViewPhone.setText(seller.getPhone());
        textViewNetwork.setText(seller.getMobileNetwork());
        textViewMarket.setText(seller.getMarket());
        textViewLandmark.setText(seller.getLandmark());
        progress = new ProgressDialog(getActivity());

        progress.setMessage("Transaction in Progress");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(0);



        buttonDailyTax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog();
            }
        });

        buttonYearlyTax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getDialog();

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        seller = realm.where(SellerMDL.class).equalTo("id", b.getString(getString(R.string.seller_id_key))).findFirst();

                        longitude = seller.getLocationMDL().getLongitude();
                        latitude = seller.getLocationMDL().getLatitude();
                    }
                });
                GenUtils.getToastMessage(getContext(), String.valueOf(latitude) +"|"+ String.valueOf(longitude));
//                Uri gmmIntentUri = Uri.parse("geo:"+  longitude +"," + latitude+ "");
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                if (mapIntent.resolveActivity( getActivity().getPackageManager()) != null) {
//                    startActivity(mapIntent);
//                }
                Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }


            }
        });

        return view;
    }

    private void getDialog() {
        new MaterialDialog.Builder(getContext())
                .title("Enter Amount")

                .inputType(InputType.TYPE_CLASS_NUMBER)
                .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        GenUtils.getToastMessage(getContext(), input.toString());

                        final String amount = input.toString();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            SellerMDL seller = realm.where(SellerMDL.class).equalTo("id", b.getString(getString(R.string.seller_id_key))).findFirst();

                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            Date date = new Date();
                            System.out.println(dateFormat.format(date));
                            String id = UUID.randomUUID().toString();
                            PaymentMDL paymentMDL = realm.createObject(PaymentMDL.class, id);
                            paymentMDL.setAmount(Float.parseFloat(amount));
                            paymentMDL.setSellerMDL(seller);
                            paymentMDL.setPaymentType("daily");
                            paymentMDL.setDateCreated(dateFormat.format(date));
                        }
                    } );

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("type", "daily");
                        params.put("amount", amount);
                        params.put("sellerId", "123456789010");

                        mVolleyRequest.postData(Constants.PAYMENT_URL, params, new VolleyRequests.VolleyPostCallBack() {


//                    {"success":true,"message":"Registration successful"}

                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onSuccess(final JSONObject result) {
                                Log.d(TAG, result.toString());
                                try {

                                    final String message = result.getString("message");
                                    final String success = result.getString("success");
                                    msg("success");

//                            sellerMDL.setHasSynced(true);
//                                    realmAsyncTask = realm.executeTransactionAsync(new Realm.Transaction() {
//                                        @Override
//                                        public void execute(Realm realm) {
//                                            SellerMDL sellerMDL = realm.where(SellerMDL.class).findAllSorted("createdDate").last();
//
//                                            sellerMDL.setHasSynced(true);
//
//                                        }
//                                    });



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onError(VolleyError error) {

                                Log.e("Shit", error.toString());
                                String message = null;
                                if (error instanceof NetworkError) {
                                    message = "Cannot connect to Internet...Please check your connection!";
                                } else if (error instanceof ServerError) {
                                    message = "The server could not be found. Please try again after some time!!";
                                } else if (error instanceof AuthFailureError) {
                                    message = "Cannot connect to Internet...Please check your connection!";
                                } else if (error instanceof ParseError) {
                                    message = "Parsing error! Please try again after some time!!";
                                } else if (error instanceof NoConnectionError) {
                                    message = "Cannot connect to Internet...Please check your connection!";
                                } else if (error instanceof TimeoutError) {
                                    message = "Connection TimeOut! Please check your internet connection.";

                                }
//                                msg(message);
                                mProgressBar.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onStart() {
//                                mProgressBar.setVisibility(View.VISIBLE);
                                progress.show();
                            }

                            @Override
                            public void onFinish() {

//                                mProgressBar.setVisibility(View.INVISIBLE);
                                progress.hide();
                            }
                        });

                    }

                }).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void msg(String content) {

        try {
            new MaterialDialog.Builder(getContext())
                    .title("Payment")
                    .content(content)
                    .positiveText("OK")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            // TODO

                        }
                    }).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
