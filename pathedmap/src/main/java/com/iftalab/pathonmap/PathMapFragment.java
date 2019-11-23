package com.iftalab.pathonmap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class PathMapFragment extends Fragment implements OnMapReadyCallback, PathControlListener {

    private static final String MAP_VIEW_BUNDLE_KEY = "MAP_VIEW_BUNDLE_KEY";
    private MapStateController mapStateController;

    public PathMapFragment() {
    }

    public static PathMapFragment newInstance() {
        return new PathMapFragment();
    }

    private Bundle mapViewBundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
    }

    private MapView mapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pathed_map, container, false);
        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapStateController = new MapStateController(googleMap);
        if (getActivity() instanceof PathController) {
            ((PathController) getActivity()).onReadyForControl();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void setPath(List<LatLng> pathData) {
        mapStateController.setPath(pathData);
    }

    @Override
    public void moveFocusToStartOfPath() {
        mapStateController.moveFocusToStartOfPath();
    }

    @Override
    public void moveFocusToEndOfPath() {
        mapStateController.moveFocusToEndOfPath();
    }

    @Override
    public void setZoomLevel(float zoomLevel) {
        mapStateController.setZoomLevel(zoomLevel);
    }

    @Override
    public void setPathColor(int pathColor) {
        mapStateController.setPathColor(pathColor);
    }
}
