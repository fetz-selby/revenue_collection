package com.libertycapital.marketapp.views.fragments;

import android.content.Context;
import android.content.Intent;
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
import android.view.inputmethod.InputMethodManager;
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
import com.libertycapital.marketapp.views.activities.HomeACT;

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
        mProgressBar.setVisibility(View.INVISIBLE);
        editTextUsername.addTextChangedListener(new LoginFragment.MyTextWatcher(editTextUsername));
        editTextPassword.addTextChangedListener(new LoginFragment.MyTextWatcher(editTextPassword));

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                usernameError = GenUtils.isEmpty(editTextUsername, textInputLayoutUsername, "Enter your username");
                passwordError = GenUtils.isEmpty(editTextPassword, textInputLayoutPassword, "Enter your password");


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
                                final String message = result.getString("message");
                                if (user.equals("true")) {
                                    final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                    final Date date = new Date();


                                    realmAsyncTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {


                                            try {

                                                final JSONObject agent = result.getJSONObject("agent");
                                                UserMDL olduser = realm.where(UserMDL.class).equalTo("webId", agent.getString("id")).findFirst();
                                                if (olduser == null) {
                                                    String id = UUID.randomUUID().toString();
                                                    UserMDL userMDL = realm.createObject(UserMDL.class, id);
                                                    userMDL.setWebId(agent.get("id").toString());
                                                    userMDL.setEmail(agent.getString("email"));
                                                    userMDL.setFirstName(agent.getString("firstname"));
                                                    userMDL.setLastName(agent.getString("surname"));
                                                    userMDL.setPhone(agent.getString("phone"));
                                                    userMDL.setWebId(agent.getString("_id"));
                                                    userMDL.setDistrictMDL(agent.getString("district"));
                                                    userMDL.setDateAdded(agent.getString("createdDate"));
                                                    userMDL.setDateModified(agent.getString("modifiedDate"));
                                                } else {
                                                    realm.copyToRealmOrUpdate(olduser);

                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


                                        }
                                    }, new Realm.Transaction.OnSuccess() {
                                        @Override
                                        public void onSuccess() {
                                            Toast.makeText(getContext(), "Added successfully", Toast.LENGTH_SHORT).show();
                                            RealmResults<UserMDL> r = mRealm.where(UserMDL.class)
                                                    .findAll();
                                            if (!r.isEmpty()) {
                                                Log.d("User total", String.valueOf(r.size()));
                                                Log.d("User content", String.valueOf(r.toString()));
                                            }


                                        }
                                    }, new Realm.Transaction.OnError() {
                                        @Override
                                        public void onError(Throwable error) {
                                            Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                                            Log.d("REal error", error.getMessage());

                                        }
                                    });

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

        startActivity(new Intent(getActivity(), HomeACT.class));
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

    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
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
