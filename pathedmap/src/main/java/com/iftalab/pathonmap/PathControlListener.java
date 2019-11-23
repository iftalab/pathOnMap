package com.iftalab.pathonmap;

import androidx.annotation.ColorInt;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface PathControlListener {

    void setPath(List<LatLng> pathData);

    void moveFocusToStartOfPath();

    void moveFocusToEndOfPath();

    void setZoomLevel(float zoomLevel);

    void setPathColor(@ColorInt int pathColor);
}
