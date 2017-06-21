package com.libertycapital.marketapp.views.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.libertycapital.marketapp.R;
import com.libertycapital.marketapp.models.LocationMDL;
import com.libertycapital.marketapp.models.SellerMDL;
import com.libertycapital.marketapp.utils.GenUtils;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationSource;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.offline.OfflineManager;
import com.mapbox.services.android.telemetry.location.LocationEngine;
import com.mapbox.services.android.telemetry.location.LocationEngineListener;
import com.mapbox.services.android.telemetry.permissions.PermissionsListener;
import com.mapbox.services.android.telemetry.permissions.PermissionsManager;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.Sort;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapBusinessFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapBusinessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapBusinessFragment extends Fragment implements PermissionsListener {

    public static final int PERMISSION_ACCESS_COARSE_LOCATION = 99;
    public static final String TAG = MapBusinessFragment.class.getName();
    // JSON encoding/decoding
    public static final String JSON_CHARSET = "UTF-8";
    public static final String JSON_FIELD_REGION_NAME = "FIELD_REGION_NAME";
    @BindView(R.id.mapView)
    MapView mapView;
    Realm mRealm;
    RealmAsyncTask realmAsyncTask;
    private MapboxMap map;
    private FloatingActionButton floatingActionButton;
    private LocationEngine locationEngine;
    private LocationEngineListener locationEngineListener;
    private PermissionsManager permissionsManager;
    private boolean isEndNotified;
    private ProgressBar progressBar;
    private OfflineManager offlineManager;
    private OnFragmentInteractionListener mListener;
    Location myLocation;
    double longi ;
    double lati;

    public MapBusinessFragment() {
        // Required empty public constructor
    }


    public static MapBusinessFragment newInstance(String param1, String param2) {
        MapBusinessFragment fragment = new MapBusinessFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(getContext(), getString(R.string.access_token));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_map_business, container, false);
        ButterKnife.bind(this, view);
        mRealm = Realm.getDefaultInstance();

        // Get the location engine object for later use.
        locationEngine = LocationSource.getLocationEngine(getContext());
        locationEngine.activate();

        mapView.onCreate(savedInstanceState);


        mapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(MapboxMap mapboxMap) {


                map = mapboxMap;
            }
        });
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.location_toggle_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (map != null) {
                    toggleGps(!map.isMyLocationEnabled());
                }
            }
        });
        return view;
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

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        if (realmAsyncTask != null && !realmAsyncTask.isCancelled()) {
            realmAsyncTask.cancel();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
        mapView.onDestroy();
        // Ensure no memory leak occurs if we register the location listener but the call hasn't
        // been made yet.
        if (locationEngineListener != null) {
            locationEngine.removeLocationEngineListener(locationEngineListener);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private void toggleGps(boolean enableGps) {
        if (enableGps) {
            // Check if user has granted location permission
            permissionsManager = new PermissionsManager(this);
            if (!PermissionsManager.areLocationPermissionsGranted(getContext())) {
                permissionsManager.requestLocationPermissions(getActivity());
            } else {
                enableLocation(true);
            }
        } else {
            enableLocation(false);
        }


    }

    private void enableLocation(boolean enabled) {
        if (enabled) {
            // If we have the last location of the user, we can move the camera to that position.
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Enable GPS")
                        .setMessage("Kindly enable location")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSION_ACCESS_COARSE_LOCATION);
                            }
                        })
                        .create()
                        .show();

            }
            Location lastLocation = locationEngine.getLastLocation();
            if (lastLocation != null) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation), 16));



            }

            locationEngineListener = new LocationEngineListener() {
                @Override
                public void onConnected() {
                    // No action needed here.
                }

                @Override
                public void onLocationChanged(final Location location) {
                    if (location != null) {
                        // Move the map camera to where the user location is and then remove the
                        // listener so the camera isn't constantly updating when the user location
                        // changes. When the user disables and then enables the location again, this
                        // listener is registered again and will adjust the camera once again.
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location), 16));
                         longi = location.getLongitude();
                        lati = location.getLatitude();
                        GenUtils.getToastMessage(getContext(),longi + "|" +  lati);

                        mRealm.executeTransaction(new Realm.Transaction() {

                            @Override
                            public void execute(Realm realm) {
                                myLocation = location;

                                SellerMDL sellerMDL = realm.where(SellerMDL.class).findAllSorted("createdDate").last();

                                String id = UUID.randomUUID().toString();
                                LocationMDL locationMDL = realm.createObject(LocationMDL.class, id);
                                locationMDL.setLatitude(lati);

                                locationMDL.setLongitude(longi);
                                GenUtils.getToastMessage(getContext(),locationMDL.toString() + "data");
                                sellerMDL.setLongitude(longi);
                                sellerMDL.setLatitude(lati);
                                sellerMDL.setLocationMDL(locationMDL);


                            } });


                        SellerMDL sellerMDL = mRealm.where(SellerMDL.class).findAllSorted("createdDate").last();
                        GenUtils.getToastMessage(getContext(), sellerMDL.toString());



                        locationEngine.removeLocationEngineListener(this);

                    }
                }
            };
            locationEngine.addLocationEngineListener(locationEngineListener);
            floatingActionButton.setImageResource(R.drawable.ic_location_disabled_24dp);
        } else {
            floatingActionButton.setImageResource(R.drawable.ic_my_location_24dp);
        }
        // Enable or disable the location layer on the map
        map.setMyLocationEnabled(enabled);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(getContext(), "This app needs location permissions in order to show its functionality.",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocation(true);
        } else {
            Toast.makeText(getContext(), "You didn't grant location permissions.",
                    Toast.LENGTH_LONG).show();
            getActivity().finish();
        }
    }

    // Progress bar methods
    private void startProgress() {

        // Start and show the progress bar
        isEndNotified = false;
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void setPercentage(final int percentage) {
        progressBar.setIndeterminate(false);
        progressBar.setProgress(percentage);
    }

    private void endProgress(final String message) {
        // Don't notify more than once
        if (isEndNotified) {
            return;
        }

        // Stop and hide the progress bar
        isEndNotified = true;
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.GONE);

        // Show a toast
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

//    private void storeData( Location location) {
//
//        realmAsyncTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                double longi = location.getLongitude();
//                double lati = location.getLatitude();
//                SellerMDL sellerMDL = realm.where(SellerMDL.class).findAllSorted("createdDate").last();
//
//                String id = UUID.randomUUID().toString();
//                LocationMDL locationMDL = realm.createObject(LocationMDL.class, id);
//                locationMDL.setLatitude(longi);
////                GenUtils.getToastMessage(getContext() , "" +sellerMDL.getLocationMDL().getLatitude());
//                locationMDL.setLongitude(location.getLongitude());
//                sellerMDL.setLocationMDL(locationMDL);
//
//
//            }
//        }, new Realm.Transaction.OnSuccess() {
//            @Override
//            public void onSuccess() {
//
//                GenUtils.getToastMessage(getContext(), "Added successfully");
//
//
//
//
//            }
//        }, new Realm.Transaction.OnError() {
//            @Override
//            public void onError(Throwable error) {
//                GenUtils.getToastMessage(getContext(), error.getMessage());
//            }
//        });
////        setViewpager(sellerMDL,mViewPager);
//
//    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
