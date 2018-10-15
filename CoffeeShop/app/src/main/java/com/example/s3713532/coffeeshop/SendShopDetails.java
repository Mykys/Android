package com.example.s3713532.coffeeshop;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by s3713532 on 4/21/18.
 */

public class SendShopDetails extends AsyncTask<String, Void, String> {

    private GoogleMap mMap;
    private String json;

    public SendShopDetails (GoogleMap mMap) {
        this.mMap = mMap;
    }

    @Override
    protected String doInBackground(String... strings) {

        json = HttpHandler.post("http://bestlab.us:8080/places", strings[0]);

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Gson gson = new Gson();
        Shop shop = gson.fromJson(json, Shop.class);

        LatLng newShop = new LatLng(shop.getLat(), shop.getLon());
        mMap.addMarker(new MarkerOptions()
                .position(newShop)
                .title(shop.getName())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.kitty)));
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
