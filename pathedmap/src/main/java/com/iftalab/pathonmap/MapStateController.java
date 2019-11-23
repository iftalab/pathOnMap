package com.iftalab.pathonmap;

import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapStateController implements PathControlListener {
    private GoogleMap map;
    private List<LatLng> pathData = new ArrayList<>();
    private LatLng focus;
    private int pathColor = Color.BLACK;
    private float zoomLevel = 17.0f;

    public MapStateController(GoogleMap map) {
        this.map = map;
        UiSettings uiSettings = this.map.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(false);
        uiSettings.setIndoorLevelPickerEnabled(false);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(false);
        this.map.setOnCameraMoveListener(cameraMoveListener);
    }

    private GoogleMap.OnCameraMoveListener cameraMoveListener = new GoogleMap.OnCameraMoveListener() {
        @Override
        public void onCameraMove() {
            CameraPosition cameraPosition = map.getCameraPosition();
            zoomLevel = cameraPosition.zoom;
        }
    };

    @Override
    public void setPath(List<LatLng> pathData) {
        LatLng currentLastPoint = pathData.size() > 0 ? pathData.get(pathData.size() - 1) : null;
        this.pathData = pathData;
        updatePath();
        if (focus == null || focus.equals(currentLastPoint)) {
            moveFocusToEndOfPath();
            updateCamera();
        }
    }

    private void updatePath() {
        if (pathData != null) {
            map.clear();
            map.addPolyline(
                    new PolylineOptions()
                            .add(pathData.toArray(new LatLng[pathData.size()]))
                            .width(5)
                            .color(pathColor)
            );
        }
    }

    private void updateCamera() {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(focus, zoomLevel));
    }

    @Override
    public void moveFocusToStartOfPath() {
        if (pathData.size() > 0) {
            focus = pathData.get(0);
        }
        updateCamera();
    }

    @Override
    public void moveFocusToEndOfPath() {
        if (pathData.size() > 0) {
            focus = pathData.get(pathData.size() - 1);
        }
        updateCamera();
    }

    @Override
    public void setZoomLevel(float zoomLevel) {
        this.zoomLevel = zoomLevel;
        updateCamera();
    }

    @Override
    public void setPathColor(int pathColor) {
        this.pathColor = pathColor;
        updatePath();
    }
}
