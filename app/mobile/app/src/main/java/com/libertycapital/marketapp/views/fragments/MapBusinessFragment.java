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

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private MapboxMap map;
    private FloatingActionButton floatingActionButton;
    private LocationEngine locationEngine;
    private LocationEngineListener locationEngineListener;
    private PermissionsManager permissionsManager;
    private boolean isEndNotified;
    private ProgressBar progressBar;
    private OfflineManager offlineManager;
    private OnFragmentInteractionListener mListener;

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

        // Get the location engine object for later use.
        locationEngine = LocationSource.getLocationEngine(getContext());
        locationEngine.activate();

        mapView.onCreate(savedInstanceState);

        mapView.onCreate(savedInstanceState);
//        mapView.setStyleUrl("assets://mapbox-raster-v8.json");
        mapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(MapboxMap mapboxMap) {
//                // Set up the OfflineManager
//                offlineManager = OfflineManager.getInstance(getActivity());
//
//
//                // Create a bounding box for the offline region
//                LatLngBounds latLngBounds = new LatLngBounds.Builder()
//                        .include(new LatLng(10.974011, -2.791992))
//                        .include(new LatLng(6.108251, 1.163086))
////                        .include(new LatLng(37.6744, -119.6815)) // Southwest
//                        .build();
//
//                // Define the offline region
//                OfflineTilePyramidRegionDefinition definition = new OfflineTilePyramidRegionDefinition(
//                        mapboxMap.getStyleUrl(),
//                        latLngBounds,
//                        10,
//                        20,
//                        getActivity().getResources().getDisplayMetrics().density);
//
//                // Set the metadata
//                byte[] metadata;
//                try {
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put(JSON_FIELD_REGION_NAME, "Ghana");
//                    String json = jsonObject.toString();
//                    metadata = json.getBytes(JSON_CHARSET);
//                } catch (Exception exception) {
//                    Log.e(TAG, "Failed to encode metadata: " + exception.getMessage());
//                    metadata = null;
//                }
//
//                // Create the region asynchronously
//                offlineManager.createOfflineRegion(
//                        definition,
//                        metadata,
//                        new OfflineManager.CreateOfflineRegionCallback() {
//                            @Override
//                            public void onCreate(OfflineRegion offlineRegion) {
//                                offlineRegion.setDownloadState(OfflineRegion.STATE_ACTIVE);
//
//                                // Display the download progress bar
//                                progressBar = (ProgressBar) view.findViewById(R.id.progressBarMap);
//                                startProgress();
//
//                                // Monitor the download progress using setObserver
//                                offlineRegion.setObserver(new OfflineRegion.OfflineRegionObserver() {
//                                    @Override
//                                    public void onStatusChanged(OfflineRegionStatus status) {
//
//                                        // Calculate the download percentage and update the progress bar
//                                        double percentage = status.getRequiredResourceCount() >= 0
//                                                ? (100.0 * status.getCompletedResourceCount() / status.getRequiredResourceCount()) :
//                                                0.0;
//
//                                        if (status.isComplete()) {
//                                            // Download complete
//                                            endProgress("Region downloaded successfully.");
//                                        } else if (status.isRequiredResourceCountPrecise()) {
//                                            // Switch to determinate state
//                                            setPercentage((int) Math.round(percentage));
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onError(OfflineRegionError error) {
//                                        // If an error occurs, print to logcat
//                                        Log.e(TAG, "onError reason: " + error.getReason());
//                                        Log.e(TAG, "onError message: " + error.getMessage());
//                                    }
//
//                                    @Override
//                                    public void mapboxTileCountLimitExceeded(long limit) {
//                                        // Notify if offline region exceeds maximum tile count
//                                        Log.e(TAG, "Mapbox tile count limit exceeded: " + limit);
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void onError(String error) {
//                                Log.e(TAG, "Error: " + error);
//                            }
//                        });


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
//        mapView.onPause();
//        offlineManager.listOfflineRegions(new OfflineManager.ListOfflineRegionsCallback() {
//            @Override
//            public void onList(OfflineRegion[] offlineRegions) {
//                if (offlineRegions.length > 0) {
//                    // delete the last item in the offlineRegions list which will be yosemite offline map
//                    offlineRegions[(offlineRegions.length - 1)].delete(new OfflineRegion.OfflineRegionDeleteCallback() {
//                        @Override
//                        public void onDelete() {
//                            Toast.makeText(getActivity(), "Ghana map deleted", Toast.LENGTH_LONG).show();
//                        }
//
//                        @Override
//                        public void onError(String error) {
//                            Log.e(TAG, "On Delete error: " + error);
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onError(String error) {
//                Log.e(TAG, "onListError: " + error);
//            }
//        });
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        // Move the map camera to where the user location is and then remove the
                        // listener so the camera isn't constantly updating when the user location
                        // changes. When the user disables and then enables the location again, this
                        // listener is registered again and will adjust the camera once again.
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location), 16));
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
