package com.example.jhongger.wander.services;


import com.example.jhongger.wander.BasicNameValue;
import com.example.jhongger.wander.entities.Report;
import com.example.jhongger.wander.entities.Type;
import com.example.jhongger.wander.util.Constants;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportService {

    public List<Report> traerDenuncias(LatLngBounds curScreen){
        List<Report> reports ;
        reports=null;
        String response;
        System.out.println("Trayendo denuncias...");

        try{
            List<BasicNameValue> params = new ArrayList<>();
            ConexionHttpService conexionHttpService = new ConexionHttpService(Constants.API_URL.REPORT_GET_BY_FILTERS,"POST",params);

            params.add(new BasicNameValue("filter[turnos]", "todos"));
            params.add(new BasicNameValue("filter[fechas]", "todos"));
            params.add(new BasicNameValue("fechas", "todos"));
            params.add(new BasicNameValue("bounds[north]", curScreen.northeast.latitude + ""));
            params.add(new BasicNameValue("bounds[east]", curScreen.northeast.longitude + ""));
            params.add(new BasicNameValue("bounds[south]", curScreen.southwest.latitude + ""));
            params.add(new BasicNameValue("bounds[west]", curScreen.southwest.longitude + ""));
            response=conexionHttpService.conectar();
            JSONObject json_object = new JSONObject(response);
            JSONArray reportsJson = json_object.getJSONArray("reports");
            int i;
            reports = new ArrayList<>();
            for ( i = 0; i < reportsJson.length(); i++) {
                JSONObject item = reportsJson.getJSONObject(i);
                Report report = new Report();
                report.setId(item.getLong("id"));
                report.setDescription(item.getString("description"));
                report.setAddress(item.getString("address"));
                report.setLatitude(item.getDouble("latitude"));
                report.setLongitude(item.getDouble("longitude"));
                report.setType( new Type(item.getJSONObject("type").getString("name")));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try
                {
                    report.setEventDate(simpleDateFormat.parse(item.getString("eventDate")));
                }
                catch (ParseException ex)
                {
                    System.out.println("Exception "+ex);
                }
                //report.setEventDate(new Date(item.getString("eventDate")));

                reports.add(report);

               // System.out.println(item);
            }
            System.out.println("cantidad: "+i);
        }catch (Exception e){
            System.out.println("error");
            e.printStackTrace();
        }
        return reports;
    }

}
