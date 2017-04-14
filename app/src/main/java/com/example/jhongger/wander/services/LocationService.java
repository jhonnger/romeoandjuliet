package com.example.jhongger.wander.services;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class LocationService  implements LocationListener,Runnable   {

    public static final Long MIN_TIME_LOCATION_UPDATE = 2000L; // 2 seg
    public  static final Float MIN_DISTANCE_LOCATION_UPDATE = 0f;
    private LocationManager locationManager;
    private Location currentLocation;
    private Boolean isGPSEnabled;
    private Boolean isNetworkEnabled;
    private Location location;
    Context mContext;
    private GoogleMap mMap;

    public LocationService(Context mContext) {
        this.mContext = mContext;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
    }


    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        // Do nothing
    }

    @Override
    public void onProviderEnabled(String s) {
        // Do nothing
    }

    @Override
    public void onProviderDisabled(String s) {
        // Do nothing
    }


    public Location getLocation() {
        try {
            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                Toast.makeText(mContext, "Active el GPS para una mejor experiencia.", Toast.LENGTH_SHORT).show();
                System.out.println("error");
            } else {

                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_LOCATION_UPDATE,
                            MIN_DISTANCE_LOCATION_UPDATE, this);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_LOCATION_UPDATE,
                                MIN_DISTANCE_LOCATION_UPDATE, this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    @Override
    public void run()
    {
         currentLocation = getLocation();
        if (mMap!=null && currentLocation!=null){
            LatLng actual = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            GoogleMap.CancelableCallback cancelableCallback = new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {
                    new ReportService().traerDenuncias(mMap.getProjection().getVisibleRegion().latLngBounds);
                }

                @Override
                public void onCancel() {

                }
            };
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(actual,10), 2000, cancelableCallback);
        }
    }

    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
    }
}