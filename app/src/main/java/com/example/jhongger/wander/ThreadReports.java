package com.example.jhongger.wander;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.example.jhongger.wander.entities.Report;
import com.example.jhongger.wander.services.ReportService;
import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.clustering.ClusterManager;

import java.util.List;

/**
 * Created by jhongger on 02/03/2017.
 */

public class ThreadReports extends AsyncTask<Void, Integer, Void> {


    ReportService reportService;
    private ClusterManager mClusterManager;
    List<Report> reports;
    private GoogleMap mMap;

    public ThreadReports(GoogleMap mMap,ClusterManager mClusterManager){
        super();
        reportService = new ReportService();
        this.mMap = mMap;
        this.mClusterManager = mClusterManager;
    }

    //Before running code in the separate thread
    @Override
    protected void onPreExecute() {
        //Initialize the ViewSwitcher object

    }

    //The code to be executed in a background thread.
    @Override
    protected Void doInBackground(Void... params) {
            /* This is just a code that delays the thread execution 4 times,
             * during 850 milliseconds and updates the current progress. This
             * is where the code that is going to be executed on a background
             * thread must be placed.
             */reportService = new ReportService();

        reports= reportService.traerDenuncias(MapaReports.curScreen);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable(){
            @Override
            public void run() {

                mMap.clear();

                //mMap.setOnCameraChangeListener(mClusterManager);

                mClusterManager.clearItems();

                for(Report report:reports){
                    MyItem offsetItem = new MyItem(report.getLatitude(), report.getLongitude());
                    offsetItem.setTitle(report.getType().getName());
                    offsetItem.setSnippet(report.getDescription());
                    mClusterManager.addItem(offsetItem);

                       /* mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(report.getLatitude(), report.getLongitude()))
                                .draggable(false)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.hurto)));*/
                }
                mClusterManager.cluster();
            }
            // your UI code here
        });

        return null;
    }

    //Update the TextView and the progress at progress bar
    @Override
    protected void onProgressUpdate(Integer... values) {
    }

    //After executing the code in the thread
    @Override
    protected void onPostExecute(Void result) {

    }
}