package com.haowei.haowei.umbrella;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by haowei on 8/1/15.
 */
class WebService extends AsyncTask<String, String, String> {
    Activity myActivity = null;

    public WebService(Activity activity){
        myActivity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        String rString = "Need Umbrella: ", result = null;
        HttpURLConnection conn = null;
        try {
            StringBuilder urlString = new StringBuilder("http://api.openweathermap.org/data/2.5/forecast/daily?q=");
            if (params.length > 0 && params[0] != null) {
                urlString.append(params[0]);
            } else {
                urlString.append("Sanfrancisco");
            }
            urlString.append("&mode=json&units=metric&cnt=1");
            URL url = new URL(urlString.toString());
            conn = (HttpURLConnection) url.openConnection();
            result = getURLString(conn.getInputStream());
            result = parseStringToJson(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(conn != null){
                conn.disconnect();
            }
        }
        return rString + result;
    }

    protected String parseStringToJson(String s){
        String result = null;
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            JSONObject jsonWether = jsonArray.getJSONObject(0);
            if(jsonWether.has("rain") || jsonWether.has("snow")){
                result = "Yes\n";
            } else {
                result = "No\n";
            }
            result += jsonWether.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected String getURLString(InputStream in){
        StringBuilder content = new StringBuilder();
        BufferedReader buffReader = null;
        try {
            buffReader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = buffReader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(buffReader != null){
                try {
                    buffReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    @Override
    protected void onPostExecute(String result){
        TextView textView = (TextView) myActivity.findViewById(R.id.hello);
        textView.setText(result);
    }
}
