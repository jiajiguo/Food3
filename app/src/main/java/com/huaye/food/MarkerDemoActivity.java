/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huaye.food;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * This shows how to place markers on a map.
 */
public class MarkerDemoActivity extends AppCompatActivity implements OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener {

    private static final LatLng SOUTH_DINNING_HALL = new LatLng(41.6994831, -86.2413696);

    private static final LatLng NORTH_DINNING_HALL = new LatLng(41.7044217, -86.2359478);

    private static final LatLng LIBRARY_CAFE = new LatLng(41.7023435, -86.2340916);

    private static final LatLng GRACE_HALL = new LatLng(41.7048248, -86.2339147);

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marker_demo);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        new OnMapAndViewReadyListener(mapFragment, this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.getUiSettings().setZoomControlsEnabled(false);

        addMarkersToMap();

        mMap.setContentDescription("Map with lots of markers.");

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(LIBRARY_CAFE)
                .include(GRACE_HALL)
                .include(SOUTH_DINNING_HALL)
                .include(NORTH_DINNING_HALL)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
    }

    private void addMarkersToMap() {
        mMap.addMarker(new MarkerOptions()
                .position(SOUTH_DINNING_HALL)
                .title("SOUTH DINNING HALL")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        mMap.addMarker(new MarkerOptions()
                .position(LIBRARY_CAFE)
                .title("LIBRARY CAFE")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                .infoWindowAnchor(0.5f, 0.5f));

        mMap.addMarker(new MarkerOptions()
                .position(NORTH_DINNING_HALL)
                .title("NORTH DINNING HALL")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                .draggable(true));

        mMap.addMarker(new MarkerOptions()
                .position(GRACE_HALL)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .title("GRACE HALL"));
    }
}
