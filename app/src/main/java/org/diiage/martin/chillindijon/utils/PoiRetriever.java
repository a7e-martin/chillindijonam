package org.diiage.martin.chillindijon.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.diiage.martin.chillindijon.models.Location;
import org.diiage.martin.chillindijon.models.Poi;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by alexandre on 16/03/18.
 */

public class PoiRetriever extends AsyncTask<String, Void, ArrayList<Poi>> {

    public interface AsyncResponse{
        void processFinish(ArrayList<Poi> result);
    }

    public AsyncResponse delegate = null;

    @Override
    protected ArrayList<Poi> doInBackground(String... apiUrl) {

        InputStream inputStream = null;
        ArrayList<Poi> result = new ArrayList<Poi>();
        try{
            URL baseUrl = new URL(apiUrl[0]);
            inputStream = baseUrl.openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String lineBuffer = null;
            while((lineBuffer = bufferedReader.readLine()) != null){
                stringBuilder.append(lineBuffer);
            }
            String data = stringBuilder.toString();

            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Poi poi = new Poi();
                poi.setId(jsonObject.getString("id"));
                poi.setType(jsonObject.getString("type"));
                poi.setName(jsonObject.getString("name"));

                //Location
                JSONObject locationJsonObject = jsonObject.getJSONObject("location");

                Location l = new Location();
                l.setAddress(locationJsonObject.getString("adress"));
                l.setPostalCode(locationJsonObject.getString("postalCode"));
                android.location.Location position = new android.location.Location("");
                position.setLatitude(locationJsonObject.getJSONObject("position").getDouble("lat"));
                position.setLongitude(locationJsonObject.getJSONObject("position").getDouble("lon"));
                l.setPosition(position);
                l.setCity(locationJsonObject.getString("city"));

                poi.setLocation(l);

                result.add(poi);
            }

            inputStream.close();
            return result;
        } catch(Exception e){
            return null;
        } finally{
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Poi> pois) {
        delegate.processFinish(pois);
    }
}
