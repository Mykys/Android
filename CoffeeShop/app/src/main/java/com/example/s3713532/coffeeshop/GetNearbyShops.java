package com.example.s3713532.coffeeshop;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s3713532 on 4/21/18.
 */

public class GetNearbyShops extends AsyncTask<Void, Void, Void> {

    private GoogleMap mMap;
    private double lat, lon, radius;
    private String json;
    private int state;
    private String filterItem;

    private CameraZoomView screen;
    private FilterShops filter;

    public GetNearbyShops (GoogleMap mMap, double lat, double lon, double radius, int state, String filterItem) {
        this.mMap = mMap;
        this.lat = lat;
        this.lon = lon;
        this.radius = radius;
        this.state = state;
        this.screen = new CameraZoomView(mMap);
        this.filter = new FilterShops(mMap, screen);
        this.filterItem = filterItem;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        json = HttpHandler.get("http://bestlab.us:8080/places");

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        // Retrieves all shops in database
        Gson gson = new Gson();
        Shop[] shops = gson.fromJson(json, Shop[].class);

        List<Shop> nearbyShopList = filter.findNearbyShops(shops, lat, lon, radius);
        List<Shop> shopsWithinZoomLevel = screen.findShopsWithinZoomLevel(nearbyShopList);

        // Find nearby shops
        if (state == 1) {
            filter.ShowNearbyShops(shopsWithinZoomLevel);
        }

        // Filter by price
        if (state == 2) {
            int price = Integer.parseInt(filterItem);
            List<Shop> filteredShops = filter.findShopWithinPriceRange(shopsWithinZoomLevel, price);
            filter.ShowNearbyShops(filteredShops);
        }

        // Filter by review
        if (state == 3) {
            List<Shop> filteredShops = filter.findShopWithReview(shopsWithinZoomLevel, filterItem);
            filter.ShowNearbyShops(filteredShops);
        }

        // Filter by distance
        if (state == 4) {
            int radius = Integer.parseInt(filterItem);
            nearbyShopList = filter.findNearbyShops(shops, lat, lon, radius);
            shopsWithinZoomLevel = screen.findShopsWithinZoomLevel(nearbyShopList);
            filter.ShowNearbyShops(shopsWithinZoomLevel);
        }

    }

}
