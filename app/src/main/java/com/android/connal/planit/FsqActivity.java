package com.android.connal.planit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.app.ListActivity;
import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

public class
FsqActivity extends ListActivity {
    ArrayList<FoursquareVenue> venuesList;

    // the foursquare client_id and the client_secret
    final String CLIENT_ID = "MC55ZQD0IW1KBLKRI3DBE3Z4FFRWOSIP4443HWTGABWTCAVI";
    final String CLIENT_SECRET = "QTIXPKKDVKRIPUH5GGFJGAFNIZFYMHD13OLM5JDGLQG0WPCW";
   static  Map<Integer, FoursquareVenue> scoreMap;
    // we will need to take the latitude and the longitude from a certain point
    // this is the center of New York
    final String radius = "100";
    private Bundle extras;
    String latitude;
    String longtitude;

    ArrayAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fsq);
        extras  = getIntent().getExtras();
        latitude = String.valueOf(extras.getDouble("EXTRA_LAT"));
        longtitude  = String.valueOf(extras.getDouble("EXTRA_LON"));
        //latitude = "40.7463956";
        //longtitude = "-73.9852992";
        // start the AsyncTask that makes the call for the venus search.
        new fourquare().execute();
    }

    private class fourquare extends AsyncTask<View, Integer, String > {

        String temp;

        @Override
        protected String doInBackground(View... urls) {
            // make Call to the url
            temp = makeCall("https://api.foursquare.com/v2/venues/search?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=20130815&ll=" + latitude + "," + longtitude + "&radius=" + radius);

            return temp;
        }

        @Override
        protected void onPreExecute() {
            // we can start a progress bar here
        }

        @Override
        protected void onPostExecute(String result) {
            if (temp == null) {
                // we have an error to the call
                // we can also stop the progress bar
            } else {
                // all things went right

                // parseFoursquare venues search result
                venuesList =  parseFoursquare(temp);
                Integer[] scoreSorter = new ArrayList<Integer>(scoreMap.keySet()).toArray(new Integer[scoreMap.size()]);
                Arrays.sort(scoreSorter, Collections.reverseOrder());
                String venue;
                StringBuilder sb = new StringBuilder("");
                sb.append(scoreMap.get(scoreSorter[0]).getName());
                //System.out.println("Num: " + venuesList.size());
                for (int i = 1; i < scoreSorter.length; i++) {
                    // make a list of the venus that are loaded in the list.
                    // show the name, the category and the city
                    sb.append(","+scoreMap.get(scoreSorter[i]).getName());
                   // venue = venuesList.get(scoreSorter[i]).getName() + ", " + venuesList.get(i).getCategory() + "" + venuesList.get(i).getCity();
                    //System.out.println("Venue: " + venuesList.get(i));
                }

                // set the results to the list
                // and show them in the xml
//                myAdapter = new ArrayAdapter(FsqActivity.this, R.layout.activity_fsq, android.R.id.list, listTitle);
//                setListAdapter(myAdapter);
                Intent intent = new Intent(FsqActivity.this, ScheduleEventListActivity.class);
                Bundle bun = new Bundle();
                bun.putString("Venues", sb.toString());
                intent.putExtras(bun);
                startActivity(intent);

            }
        }
    }

    public static String makeCall(String url) {

        System.out.println(url);
        // string buffers the url
        StringBuffer buffer_string = new StringBuffer(url);
        String replyString = "";

        // instanciate an HttpClient
        HttpClient httpclient = new DefaultHttpClient();
        // instanciate an HttpGet
        HttpGet httpget = new HttpGet(buffer_string.toString());

        try {
            // get the responce of the httpclient execution of the url
            HttpResponse response = httpclient.execute(httpget);
            InputStream is = response.getEntity().getContent();

            // buffer input stream the result
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] contents = new byte[1024];
//
//            int bytesRead = 0;
//            String strFileContents = "";
//            while((bytesRead = is.read(contents)) != -1) {
//                strFileContents += new String(contents, 0, bytesRead);
//            }
//
//            System.out.print(strFileContents);
            //
            ByteArrayBuffer baf = new ByteArrayBuffer(20);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }
            // the result as a string is ready for parsing
            replyString = new String(baf.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // trim the whitespaces
        return replyString.trim();
    }

    private static ArrayList<FoursquareVenue> parseFoursquare(final String response) {

        ArrayList<FoursquareVenue> temps = new ArrayList<FoursquareVenue>();
        FoursquareVenue poi;
        scoreMap = new HashMap<Integer, FoursquareVenue>();
        try {
            //System.out.println("Response: "+response);
            // make an jsonObject in order to parse the response
            JSONObject jsonObject = new JSONObject(response);
            int score = 0;
            // make an jsonObject in order to parse the response
            if (jsonObject.has("response")) {
                if (jsonObject.getJSONObject("response").has("venues")) {
                    JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("venues");
                    //System.out.println("JSON: " + jsonArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        poi = new FoursquareVenue();
                        //System.out.println("Name: " + jsonArray.getJSONObject(i).getString("name"));
                        poi.setCategory(jsonArray.getJSONObject(i).getJSONArray("categories").getJSONObject(0).getString("name"));
                        poi.setName(jsonArray.getJSONObject(i).getString("name"));
                        try {
                            poi.setDistance(Integer.parseInt(jsonArray.getJSONObject(i).getJSONObject("location").getString("distance")));
                        } catch(Exception e){
                            poi.setDistance(800);
                        }
                        try {
                            poi.setCheckins(Integer.parseInt(jsonArray.getJSONObject(i).getJSONObject("stats").getString("checkinsCount")));
                        }catch(Exception e){
                            poi.setCheckins(0);
                        }
                        try {
                            poi.setHere(Integer.parseInt(jsonArray.getJSONObject(i).getJSONObject("hereNow").getString("count")));
                        }catch (Exception e){
                            poi.setHere(0);
                        }
                        score = poi.getDistance() + poi.getHere() + Math.max(1, poi.getCheckins())/10;
                        while(scoreMap.containsKey(score))
                            score--;
                        scoreMap.put(score, poi);
                        temps.add(poi);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
        return temps;

    }
}




