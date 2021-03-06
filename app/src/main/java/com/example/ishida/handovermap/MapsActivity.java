package com.example.ishida.handovermap;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.ishida.handover.HandOver;
import com.example.ishida.handover.HandOverCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.IndoorLevel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Map;

public class MapsActivity extends Activity implements HandOverCallback, GoogleMap.OnCameraChangeListener, GoogleMap.OnMapLoadedCallback, GoogleMap.OnIndoorStateChangeListener {
    private static final String TAG = "HandOver Map";

    private static final String ZOOM = "zoom";
    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";
    private static final String BEARING = "bearing";
    private static final String TILT = "tilt";
    private static final String LEVEL = "level";

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private HandOver ho;

    private boolean buildingFocused = false;
    private boolean buildingLevelHandOver = false;
    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        Log.d(TAG, "Init Handover");
        // for the testing purpose, we start a service here
        //Intent serviceIntent = new Intent();
        //serviceIntent.setClassName("com.example.ishida.handover", "com.example.ishida.handover.HandOverService");
        //this.startService(serviceIntent);

        ho = HandOver.getHandOver(this);
        ho.registerCallback(this);

        String action = getIntent().getAction();
        if (action.equals("com.example.ishida.handover.RECOVER")) {
            ho.restore();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        ho.bind();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        ho.unbind();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.setOnCameraChangeListener(this);
        mMap.setOnIndoorStateChangeListener(this);
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng));
            }
        });
    }

    @Override
    public void saveActivity(final Map<String, Object> dictionary) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                CameraPosition cameraPos = mMap.getCameraPosition();
                float zoom = cameraPos.zoom;
                double longitude = cameraPos.target.longitude;
                double latitude = cameraPos.target.latitude;
                float bearing = cameraPos.bearing;
                float tilt = cameraPos.tilt;

                dictionary.put(ZOOM, zoom);
                dictionary.put(LONGITUDE, longitude);
                dictionary.put(LATITUDE, latitude);
                dictionary.put(BEARING, bearing);
                dictionary.put(TILT, tilt);
                if (buildingFocused) {
                    dictionary.put(LEVEL, level);
                }

                Log.d(TAG, "saveActivity: " + dictionary);
                synchronized (this) {
                    this.notify();
                }
            }
        };
        synchronized (runnable) {
            runOnUiThread(runnable);
            try {
                runnable.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void restoreActivity(final Map<String, Object> dictionary) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                float zoom = (float)dictionary.get(ZOOM);
                //float f = (float)dictionary.get("longitude");
                double longitude = (double)dictionary.get(LONGITUDE);
                //f = (float)dictionary.get("latitude");
                double latitude = (double)dictionary.get(LATITUDE);
                float bearing = (float)dictionary.get(BEARING);
                float tilt = (float)dictionary.get(TILT);
                if (dictionary.containsKey(LEVEL)) {
                    level = (int)dictionary.get(LEVEL);
                    buildingLevelHandOver = true;
                }

                CameraPosition cameraPos = new CameraPosition.Builder()
                        .target(new LatLng(latitude, longitude)).zoom(zoom)
                        .bearing(bearing).tilt(tilt).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPos));
            }
        };
        runOnUiThread(runnable);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        Log.d(TAG, "onCameraChanged");
        ho.activityChanged();

        IndoorBuilding building = mMap.getFocusedBuilding();
        if (building != null && buildingLevelHandOver) { // we received level which is changed
            buildingLevelHandOver = false;
            List<IndoorLevel> levels = building.getLevels();
            IndoorLevel level = levels.get(this.level);
            level.activate();
        }
    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public void onIndoorBuildingFocused() {
        IndoorBuilding building = mMap.getFocusedBuilding();
        if (building != null) {
            if (buildingLevelHandOver) { // we received level which is changed
                buildingLevelHandOver = false;
                List<IndoorLevel> levels = building.getLevels();
                IndoorLevel level = levels.get(this.level);
                level.activate();
                return;
            } else { // we will send level which is changed
                int level = building.getActiveLevelIndex();
                Log.d(TAG, "Focused Level = " + level);
                buildingFocused = true;
                this.level = level;
            }
        } else {
            Log.d(TAG, "null");
            buildingFocused = false;
        }
    }

    @Override
    public void onIndoorLevelActivated(IndoorBuilding indoorBuilding) {
        int level = indoorBuilding.getActiveLevelIndex();
        Log.d(TAG, "Level = " +  level);
        if (buildingFocused) {
            this.level = level;
            ho.activityChanged();
        }
    }
}
