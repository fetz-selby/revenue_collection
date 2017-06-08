package com.libertycapital.marketapp.views.fragments;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.libertycapital.marketapp.R;
import com.libertycapital.marketapp.models.SellerMDL;
import com.libertycapital.marketapp.utils.GenUtils;
import com.libertycapital.marketapp.views.activities.ShopSellerACT;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmAsyncTask;


public class PersonalDetailsFormFragment extends Fragment {
    private static final String TAG = PersonalDetailsFormFragment.class.getSimpleName();
    private static final int SELECT_FILE = 100;
    private static final int REQUEST_CAMERA = 101;
    private static final int PIC_CROP = 102;
    static ViewPager mViewPager = ShopSellerACT.viewPager;
    @BindView(R.id.editTextFname)
    EditText editTextFname;
    @BindView(R.id.editTextLname)
    EditText editTextLname;
    @BindView(R.id.editTextOname)
    EditText editTextOname;
    @BindView(R.id.textInputLayoutFname)
    TextInputLayout textInputLayoutFname;
    @BindView(R.id.textInputLayoutLname)
    TextInputLayout textInputLayoutLname;
    @BindView(R.id.textInputLayoutOname)
    TextInputLayout textInputLayoutOname;
    @BindView(R.id.floatingActionButtonPdetails)
    FloatingActionButton floatingActionButtonPDetails;
    Realm mRealm;
    RealmAsyncTask realmAsyncTask;
    private ImageView imageProfile;
    private Uri mPhotoURI;
    private String data;
    private String encodedImage = "";
    private boolean editTextLnameError, editTextFnameError;


    public PersonalDetailsFormFragment() {
        // Required empty public constructor
    }


    public static PersonalDetailsFormFragment newInstance(String param1, String param2) {
        PersonalDetailsFormFragment fragment = new PersonalDetailsFormFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_details_form, container, false);
        ButterKnife.bind(this, view);
        mRealm = Realm.getDefaultInstance();
        imageProfile = (ImageView) view.findViewById(R.id.imageViewSeller);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String newString;
//        if (savedInstanceState == null) {
//            Bundle extras = getActivity().getIntent().getExtras();
//            if(extras == null) {
//                newString= null;
//            } else {
//                newString= extras.getString("sellerId");
//            }
//        } else {
//            newString= (String) savedInstanceState.getSerializable("sellerId");
//        }
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext())
                        .title(R.string.seller_pic_label)
                        .items(R.array.items_picture_menu)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                Toast.makeText(getContext(), " " + which, Toast.LENGTH_LONG).show();

                                switch (which) {
//                                    case 0:
//                                        galleryIntent();
//                                        break;
                                    case 0:
                                        cameraIntent();
                                        break;
                                }
                                return true;
                            }
                        })

                        .show();

            }
        });

        floatingActionButtonPDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextFnameError = GenUtils.isEmpty(editTextFname, textInputLayoutFname, "Firstname required");
                editTextLnameError = GenUtils.isEmpty(editTextLname, textInputLayoutLname, "Lastname required");


                if (!(editTextFnameError && editTextLnameError)) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                } else {


                    realmAsyncTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            SellerMDL sellerMDL = realm.where(SellerMDL.class).findAllSorted("createdDate").last();
                            sellerMDL.setFirstname(editTextFname.getText().toString());
                            sellerMDL.setLastname(editTextLname.getText().toString());
                            if (!editTextOname.getText().toString().isEmpty()) {
                                sellerMDL.setOtherName(editTextOname.getText().toString());
                            }

                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {

                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {

                        }
                    });

//To go to the next page
//                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);

                }

            }
        });
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

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {

        Intent intent = new Intent();

        File photoFile = null;

        try {
            photoFile = createImageFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Photo File " + photoFile);

        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(intent, REQUEST_CAMERA);

    }

    private File createImageFile() throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        mPhotoURI = Uri.fromFile(image);

        return image;
    }

    private void performCrop(Uri picUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", true);
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 128);
            cropIntent.putExtra("outputY", 128);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, PIC_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            GenUtils.getToastMessage(getContext(), errorMessage);
        }
    }

    private void onCroppedFinished(Bitmap bitmap) {


        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();

            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
            imageProfile.setImageBitmap(bitmap);
            Log.d(TAG, "Ecoded image: " + encodedImage);
            //mRemove.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_FILE:
                Uri imageUri = data.getData();
                Log.d(TAG, "Select Croped Uri: " + imageUri.getEncodedPath());
                performCrop(imageUri);
                break;
            case REQUEST_CAMERA:
                performCrop(mPhotoURI);
                break;
            case PIC_CROP:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    Bitmap selectedBitmap = extras.getParcelable("data");
                    onCroppedFinished(selectedBitmap);
                }
                // get the cropped bitmap

                break;
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
                case R.id.editTextFname:
                    GenUtils.isEmpty(editTextFname, textInputLayoutFname, "Firstname  required");
                    break;
                case R.id.editTextLname:
                    GenUtils.isEmpty(editTextLname, textInputLayoutLname, "Lastname  required");
                    break;

                default:
            }
        }
    }


}
