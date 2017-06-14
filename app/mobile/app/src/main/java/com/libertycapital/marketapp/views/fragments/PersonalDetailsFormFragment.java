package com.libertycapital.marketapp.views.fragments;


import android.content.ActivityNotFoundException;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.libertycapital.marketapp.R;
import com.libertycapital.marketapp.models.SellerMDL;
import com.libertycapital.marketapp.utils.GenUtils;
import com.libertycapital.marketapp.views.activities.HawkerSellerACT;
import com.libertycapital.marketapp.views.activities.ShopSellerACT;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmAsyncTask;


public class PersonalDetailsFormFragment extends Fragment {
    private static final String TAG = PersonalDetailsFormFragment.class.getSimpleName();
    private static final int SELECT_FILE = 100;
    private static final int REQUEST_CAMERA = 101;
    private static final int PIC_CROP = 102;
    private static final int RESULT_OK = 0;
    static ViewPager mViewPager = HawkerSellerACT.viewPager;
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
    @BindView(R.id.imageViewSeller)
    ImageView imageProfile;
    Realm mRealm;
    RealmAsyncTask realmAsyncTask;

    private Uri mPhotoURI, mCroppedURI;
    private String data;
    private String encodedImage = "";
    private File compressedImage;
    private boolean editTextLnameError, editTextFnameError;
    private byte[] b;


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
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                    StoreData();
//To go to the next page
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
//        compressImage(image);

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

            b = baos.toByteArray();

            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);


            imageProfile.setImageBitmap(bitmap);

            Log.d(TAG, "Ecoded image: " + encodedImage);
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/saved_images");
            myDir.mkdirs();
            Random generator = new Random();
            int n = 10000;
            n = generator.nextInt(n);
            String fname = "Image-" + n + ".jpg";
            compressedImage = new File(myDir, fname);
            try {
                FileOutputStream out = new FileOutputStream(compressedImage);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
                mCroppedURI = Uri.fromFile(compressedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
//           if (file.exists ()) file.delete ();
//            tr y {
//                FileOutputStream out = new FileOutputStream(file);
//                photo.compress(Bitmap.CompressFormat.JPEG, 90, out);
//                out.flush();
//                out.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
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

    private void StoreData() {
        SellerMDL sellerMDL;
        realmAsyncTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SellerMDL sellerMDL = realm.where(SellerMDL.class).findAllSorted("createdDate").last();

                sellerMDL.setFirstname(editTextFname.getText().toString());
                sellerMDL.setLastname(editTextLname.getText().toString());
                if (mCroppedURI != null) {
                    sellerMDL.setPhotoUri(mCroppedURI.getPath());
                }

                if (!editTextOname.getText().toString().isEmpty()) {
                    sellerMDL.setOtherName(editTextOname.getText().toString());
                }

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

                GenUtils.getToastMessage(getContext(), getResources().getString(R.string.store_db_successfull));

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

                GenUtils.getToastMessage(getContext(), error.getMessage());

            }
        });
    }


//    not working

    private void setViewpager(SellerMDL sellerMDL, ViewPager viewPager) {
        if (sellerMDL.getSellerType().equalsIgnoreCase(getString(R.string.hawker_string))) {
            viewPager = HawkerSellerACT.viewPager;
        } else if (sellerMDL.getSellerType().equalsIgnoreCase(getString(R.string.hawker_string))) {
            viewPager = ShopSellerACT.viewPager;
        }
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
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

