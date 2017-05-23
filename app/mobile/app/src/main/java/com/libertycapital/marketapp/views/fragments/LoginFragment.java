package com.libertycapital.marketapp.views.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.libertycapital.marketapp.models.UserMDL;
import com.libertycapital.marketapp.utils.Constants;
import com.libertycapital.marketapp.utils.GenUtils;
import com.libertycapital.marketapp.utils.VolleyRequests;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends Fragment {
    public static final String TAG = LoginFragment.class.getName();
    @BindView(R.id.buttonLogin)
    Button buttonLogin;
    @BindView(R.id.textInputLayoutUsername)
    TextInputLayout textInputLayoutUsername;
    @BindView(R.id.textInputLayoutPassword)
    TextInputLayout textInputLayoutPassword;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.editTextUsername)
    EditText editTextUsername;
    @BindView(R.id.progressBarLogin)
    ProgressBar mProgressBar;
    VolleyRequests mVolleyRequest;
    Realm mRealm;
    RealmAsyncTask realmAsyncTask;
    SharedPreferences prefs;
    private boolean usernameError, passwordError;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        mVolleyRequest = new VolleyRequests(getActivity());
        mRealm = Realm.getDefaultInstance();
        prefs = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(usernameError && passwordError)) {
                    Snackbar.make(v, "Login Error", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", editTextUsername.getText().toString().trim());
                    params.put("password", editTextPassword.getText().toString().trim());


                    mVolleyRequest.postData(Constants.LOGIN_URL, params, new VolleyRequests.VolleyPostCallBack() {

                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onSuccess(final JSONObject result) {
                            Log.d(TAG, result.toString());
                            try {
                                String user = result.getString("success");
                                String message = result.getString("message");
                                if (user.equals("true")) {

//                                    get data

//
//                                    public KauthUserMDL onSync() {
//                                        try {
//                                            if (this.blnIsNew){
//                                                this.realmMLD = mRealm.createObject(KauthUserMDL.class);
//                                                this.realmMLD.setLocalGuid(UUID.randomUUID().toString());
//                                            }else{
//                                                try{
//                                                    this.realmMLD = mRealm.where(KauthUserMDL.class).equalTo(Constants.strColWebGuid, jsonItem.getString("pk")).findFirst();
//                                                }catch (JSONException e){
//                                                    Log.e("getUniqueWebRecod", e.getMessage());
//                                                }
//                                            }
//
//                                            Date dateJoined = GenUtils.string2Date(this.jsonItem.getString("date_joined"));
//                                            if ( !(dateJoined == null) ) {
//                                                this.realmMLD.setDateJoined(dateJoined);
//                                            }
//
//                                            Date lastLogin = GenUtils.string2Date(this.jsonItem.getString("last_login"));
//                                            if ( !(lastLogin == null) ) {
//                                                this.realmMLD.setLastLogin(lastLogin);
//                                            }
//
//                                            this.realmMLD.setWebGuid(this.jsonItem.getString("pk"));
//                                            this.realmMLD.setIsActive(this.jsonItem.getBoolean("is_active"));
//                                            this.realmMLD.setIsStaff(this.jsonItem.getBoolean("is_staff"));
//                                            this.realmMLD.setUsername(this.jsonItem.getString("username"));
//                                            this.realmMLD.setEmail(this.jsonItem.getString("email"));
//
//                                        } catch (Exception e) {
//                                            Log.e(TAG, this.realmClass.getSimpleName() + "DraftObject: " + e.getMessage());
//                                        }
//                                        return this.realmMLD;
//                                    }
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("email", editTextUsername.getText().toString().trim());
                                    params.put("password", editTextPassword.getText().toString().trim());
                                    final UserMDL userMDL = new UserMDL();

                                  final RealmResults<UserMDL> users = mRealm.where(UserMDL.class).findAll();
                                    users.size();
                                    if (users.size() == 0) {
                                        try {

                                            userMDL.setId(result.getJSONObject("agent").get("id").toString());
                                            userMDL.setFirstName(result.getJSONObject("agent").get("firstname").toString());
                                            userMDL.setLastName(result.getJSONObject("agent").get("surname").toString());
                                            userMDL.setEmail(result.getJSONObject("agent").get("email").toString());
                                            userMDL.setDistrictMDL(result.getJSONObject("agent").get("district").toString());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        realmAsyncTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                realm.copyToRealmOrUpdate(userMDL);

                                            }
                                        }, new Realm.Transaction.OnSuccess() {
                                            @Override
                                            public void onSuccess() {
                                                Toast.makeText(getContext(), "Added successfully", Toast.LENGTH_SHORT).show();

                                            }
                                        }, new Realm.Transaction.OnError() {
                                            @Override
                                            public void onError(Throwable error) {
                                                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                                                Log.d("REal error", error.getMessage());

                                            }
                                        });

                                    }


                                    setUserlogin();


                                } else {
                                    msg("Username or password do not exist, try again");
                                }
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


                }

            }
        });

        return view;
    }

    public void setUserlogin() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(Constants.prefsLogin, true);
        editor.apply();

//        Intent intent = new Intent(getActivity(), HomeActivity.class);
//        startActivity(intent);
        getActivity().finish();

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
        if (realmAsyncTask != null && !realmAsyncTask.isCancelled()) {
            realmAsyncTask.cancel();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();

    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            switch (view.getId()) {
                case R.id.editTextUsername:
                    GenUtils.isEmpty(editTextUsername, textInputLayoutUsername, "Enter your username");
                    break;
                case R.id.editTextPassword:
                    GenUtils.isEmpty(editTextPassword, textInputLayoutPassword, "Enter your password");
                    break;
                default:
            }
        }
    }
}
