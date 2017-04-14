package com.example.jhongger.wander;

import android.content.Context;

import com.example.jhongger.wander.services.LocationService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;


public class MapaReports implements OnMapReadyCallback,ClusterManager.OnClusterClickListener<MyItem>, ClusterManager.OnClusterInfoWindowClickListener<MyItem>, ClusterManager.OnClusterItemClickListener<MyItem>, ClusterManager.OnClusterItemInfoWindowClickListener<MyItem> {

    private GoogleMap mMap;
    private LocationService locationService;
    private Context mContext;
    static LatLngBounds curScreen;
    private ClusterManager mClusterManager;

    public MapaReports(Context context) {
        this.mContext = context;
        locationService = new LocationService(context);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationService.setmMap(mMap);


        LatLng peru = new LatLng(-9.1952321, -74.9904165);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(peru,5));
        locationService.run();
        curScreen = mMap.getProjection().getVisibleRegion().latLngBounds;

        mClusterManager = new ClusterManager<MyItem>(mContext, mMap);
        mClusterManager.setRenderer(new PersonRenderer(mContext,mMap,mClusterManager));
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                System.out.println("idle");

                curScreen = mMap.getProjection().getVisibleRegion().latLngBounds;
                new ThreadReports(mMap,mClusterManager).execute();
                mClusterManager.cluster();

            }
        });
        new ThreadReports(mMap,mClusterManager).execute();
    }

    @Override
    public boolean onClusterClick(Cluster<MyItem> cluster) {
        return false;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<MyItem> cluster) {

    }

    @Override
    public boolean onClusterItemClick(MyItem myItem) {
        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(MyItem myItem) {

    }
}
