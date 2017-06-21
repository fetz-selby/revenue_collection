package com.libertycapital.marketapp.views.fragments;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

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
import com.libertycapital.marketapp.models.SellerMDL;
import com.libertycapital.marketapp.utils.Constants;
import com.libertycapital.marketapp.utils.GenUtils;
import com.libertycapital.marketapp.utils.VolleyRequests;
import com.libertycapital.marketapp.views.activities.SellerListACT;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmAsyncTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p>
 * to handle interaction events.
 * Use the {@link FinalStepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinalStepFragment extends Fragment {


    public static final String TAG = FinalStepFragment.class.getName();
    @BindView(R.id.buttonSubmit)
    Button buttonSubmit;
    Realm realm;
    @BindView(R.id.progressBarSubmit)
    ProgressBar mProgressBar;
    VolleyRequests mVolleyRequest;
    RealmAsyncTask realmAsyncTask;
    String firstname, surname, type, phone, mmNetwork;
    private OnFragmentInteractionListener mListener;

    public FinalStepFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FinalStepFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FinalStepFragment newInstance(String param1, String param2) {
        FinalStepFragment fragment = new FinalStepFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        mVolleyRequest = new VolleyRequests(getActivity());

        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_final_step, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressBar.setVisibility(View.INVISIBLE);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SellerMDL sellerMDL = realm.where(SellerMDL.class).findAllSorted("createdDate").last();

                firstname = sellerMDL.getFirstname();
                surname = sellerMDL.getSurname();
                type = sellerMDL.getType();
                phone = sellerMDL.getPhone();
                mmNetwork = sellerMDL.getMobileNetwork();
                if (!sellerMDL.isHasSynced()) {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("firstname", firstname);
                    params.put("surname", surname);
                    params.put("type", type);
                    params.put("phone", phone);
                    params.put("mmNetwork", mmNetwork);
                    mVolleyRequest.postData(Constants.SELLERS_URL, params, new VolleyRequests.VolleyPostCallBack() {


//                    {"success":true,"message":"Registration successful"}

                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onSuccess(final JSONObject result) {
                            Log.d(TAG, result.toString());
                            try {

                                final String message = result.getString("message");
                                final String success = result.getString("success");

//                            sellerMDL.setHasSynced(true);
                                realmAsyncTask = realm.executeTransactionAsync(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        SellerMDL sellerMDL = realm.where(SellerMDL.class).findAllSorted("createdDate").last();

                                        sellerMDL.setHasSynced(true);

                                    }
                                });

                                msg("Awesome");
                                startActivity(new Intent(getActivity(), SellerListACT.class));
                                getActivity().finish();


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
                            msg(message);
                            mProgressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onStart() {
                            mProgressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFinish() {
                            mProgressBar.setVisibility(View.INVISIBLE);
                        }
                    });

                }else{
                    GenUtils.getToastMessage(getContext(),"already synced");
                }

            }


        });
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    private void msg(String content) {

        try {
            new MaterialDialog.Builder(getContext())
                    .title("Sign In")
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

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();

    }

    public void sendNotification(View view) {

//Get an instance of NotificationManager//

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getContext())
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");


// Gets an instance of the NotificationManager service//

        NotificationManager mNotificationManager =

                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

//When you issue multiple notifications about the same type of event, it’s best practice for your app to try to update an existing notification with this new information, rather than immediately creating a new notification. If you want to update this notification at a later date, you need to assign it an ID. You can then use this ID whenever you issue a subsequent notification. If the previous notification is still visible, the system will update this existing notification, rather than create a new one. In this example, the notification’s ID is 001//

//        NotificationManager.notify().


        mNotificationManager.notify(001, mBuilder.build());
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
