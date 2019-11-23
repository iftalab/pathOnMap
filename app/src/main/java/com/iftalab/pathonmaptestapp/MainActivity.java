package com.iftalab.pathonmaptestapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.iftalab.pathonmap.PathControlListener;
import com.iftalab.pathonmap.PathController;
import com.iftalab.pathonmap.PathMapFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements PathController {
    private static final String TAG = "oooo";
    private Fragment pathMapFragmnet;
    private Button bChangeColor, bChangeZoom, bMofeToFirst, bMoveToLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pathMapFragmnet = PathMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.mapHolder, pathMapFragmnet)
                .commit();
        if (pathMapFragmnet instanceof PathControlListener) {
            pathControlListener = (PathControlListener) pathMapFragmnet;
        }

        bChangeColor = findViewById(R.id.bChangeColor);
        bChangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePathColor(v);
            }
        });
        bMofeToFirst = findViewById(R.id.bMoveToFirst);
        bMofeToFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveFocusToFirst(v);
            }
        });
        bMoveToLast = findViewById(R.id.bMoveToLast);
        bMoveToLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveFocusToLast(v);
            }
        });
        bChangeZoom = findViewById(R.id.bChangeZoom);
        bChangeZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeZoom(v);
            }
        });

    }

    private PathControlListener pathControlListener;

    @Override
    public void onReadyForControl() {
        List<LatLng> pathData = new ArrayList<>();
        pathData.add(new LatLng(23.825052, 90.433409));
        pathData.add(new LatLng(23.824796, 90.433434));
        pathData.add(new LatLng(23.824619, 90.431406));
        pathData.add(new LatLng(23.826765, 90.431216));
        if (pathControlListener != null) {
            pathControlListener.setPath(pathData);
        }
    }

    public void moveFocusToLast(View view) {
        if (pathControlListener != null) {
            pathControlListener.moveFocusToEndOfPath();
        }
    }

    private Random random = new Random();
    private int colors[] = {Color.GREEN, Color.BLACK, Color.RED, Color.YELLOW};

    public void changePathColor(View view) {
        if (pathControlListener != null) {
            int index = random.nextInt(colors.length);
            Log.d(TAG, "changePathColor: " + index);
            pathControlListener.setPathColor(colors[index]);
        }
    }

    public void moveFocusToFirst(View view) {
        if (pathControlListener != null) {
            pathControlListener.moveFocusToStartOfPath();
        }
    }

    private float[] zooms = {17.0f, 13.0f, 20.0f};

    public void changeZoom(View view) {
        if (pathControlListener != null) {
            pathControlListener.setZoomLevel(zooms[random.nextInt(zooms.length)]);
        }
    }
}
