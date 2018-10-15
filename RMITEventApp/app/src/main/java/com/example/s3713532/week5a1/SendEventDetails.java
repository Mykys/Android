package com.example.s3713532.week5a1;

import android.os.AsyncTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class SendEventDetails extends AsyncTask<String, Void, String> {

    private String json;

    public SendEventDetails () {

    }

    @Override
    protected String doInBackground(String... strings) {

        json = HttpHandler.post("https://pjrt2j99zf.execute-api.eu-west-1.amazonaws.com/api/events", strings[0]);

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    // Converts key and values into correct json format
    public String wwwEncodeMap(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
