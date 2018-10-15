package com.example.s3713532.coffeeshop;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s3713532 on 4/22/18.
 */

public class FilterShops {

    private GoogleMap mMap;
    private CameraZoomView screen;


    public FilterShops (GoogleMap mMap, CameraZoomView screen) {
        this.mMap = mMap;
        this.screen = screen;
    }

    // Find nearby places from chosen location
    public List<Shop> findNearbyShops(Shop[] shops, double currLat, double currLon, double radius) {

        List<Shop> nearbyShopList = new ArrayList<>();

        for (Shop shop : shops) {
            double dist = screen.distance(shop.getLat(), shop.getLon(), currLat, currLon) * 1.609344; // kilometers
            if (dist <= radius) {
                nearbyShopList.add(shop);
            }
        }

        return nearbyShopList;
    }

    // Show nearby shops
    public void ShowNearbyShops(List<Shop> nearbyShopsList) {

        for (Shop shop : nearbyShopsList) {

            String snippet = "Address: " + shop.getAddress() + "\n" +
                    "Price: " + shop.getPrice() + "\n" +
                    "Impression: " + shop.getImpression() + "\n" +
                    "Style: " + shop.getStyle() + "\n";


            LatLng latLng = new LatLng(shop.getLat(), shop.getLon());
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title(shop.getName())
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.kitty));
            mMap.addMarker(markerOptions);
        }
    }

    // Shops within price range
    public List<Shop> findShopWithinPriceRange (List<Shop> nearbyShopList, int priceRange) {

        List<Shop> shopsWithinPriceRange = new ArrayList<>();

        for (Shop shop : nearbyShopList) {
            if (shop.getPrice() <= priceRange) {
                shopsWithinPriceRange.add(shop);
            }
        }
        return shopsWithinPriceRange;
    }

    // Shops with chosen review
    public List<Shop> findShopWithReview (List<Shop> nearbyShopList, String review) {

        List<Shop> shopsWithReview = new ArrayList<>();

        for (Shop shop : nearbyShopList) {
            if (shop.getImpression().equals(review)) {
                shopsWithReview.add(shop);
            }
        }
        return shopsWithReview;
    }

}
