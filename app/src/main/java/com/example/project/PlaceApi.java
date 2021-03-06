package com.example.project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PlaceApi {

    public ArrayList<String> autoComplete(String input){
        ArrayList<String> arrayList=new ArrayList();
        HttpURLConnection connection=null;
        StringBuilder jsonResult=new StringBuilder();
        try {
            //https://api.tomtom.com/search/2/autocomplete/<query>.<ext>?key=<Your_API_Key>&language=<language>
            //StringBuilder sb=new StringBuilder("https://maps.googleapis.com/maps/api/place/autocomplete/json?");
            StringBuilder sb=new StringBuilder("https://api.tomtom.com/search/2/autocomplete/");
            sb.append(input+".");
            sb.append("json?key=");
            sb.append("QYbB9Q5GbCsw9Wlk1vXRWtXTBGgsbwMn");
            sb.append("&language=en-US");

            URL url=new URL(sb.toString());
            connection=(HttpURLConnection)url.openConnection();
            InputStreamReader inputStreamReader=new InputStreamReader(connection.getInputStream());

            int read;

            char[] buff=new char[1024];
            while ((read=inputStreamReader.read(buff))!=-1){
                jsonResult.append(buff,0,read);
            }
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if(connection!=null){
                connection.disconnect();
            }
        }

        try {
            JSONObject jsonObject=new JSONObject(jsonResult.toString());
            JSONArray prediction=jsonObject.getJSONArray("results");
            for(int i=0;i<prediction.length();i++){
                JSONArray seg=prediction.getJSONObject(i).getJSONArray("segments");
                arrayList.add(seg.getJSONObject(0).getString("value"));
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return arrayList;
    }
}
