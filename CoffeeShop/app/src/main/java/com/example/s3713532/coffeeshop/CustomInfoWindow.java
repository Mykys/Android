package com.example.s3713532.coffeeshop;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by s3713532 on 4/22/18.
 */

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

    private View mWindow;
    private Context mContext;

    public CustomInfoWindow(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    private void rendowWindowText(Marker marker, View view) {

        String title = marker.getTitle();
        TextView tvTitle = view.findViewById(R.id.title);

        if (!title.equals("")) {
            tvTitle.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView tvSnippet = view.findViewById(R.id.snippet);

        if (!snippet.equals("")) {
            tvSnippet.setText(snippet);
        }

        String imageUrl1 = "https://github.com/square/picasso/issues/899";
        String imageUrl2 = "https://github.com/square/picasso/issues/899";
        ImageView imageView1 = view.findViewById(R.id.photo1);
        ImageView imageView2 = view.findViewById(R.id.photo2);

        if (!imageUrl1.equals("")) {
            Picasso.with(mContext)
                    .load(imageUrl1)
                    .placeholder(R.drawable.coffee)
                    .resize(50, 50)
                    .centerCrop()
                    .into(imageView1, new MarkerCallback(marker));
        }

        if (!imageUrl2.equals("")) {
            Picasso.with(mContext)
                    .load(imageUrl2)
                    .placeholder(R.drawable.coffee)
                    .resize(50, 50)
                    .centerCrop()
                    .into(imageView2, new MarkerCallback(marker));
        }
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    static class MarkerCallback implements Callback {

        Marker marker = null;

        MarkerCallback(Marker marker) {
            this.marker = marker;
        }

        @Override
        public void onError() {

        }

        @Override
        public void onSuccess() {
            if (marker == null) {
                return;
            }

            if (!marker.isInfoWindowShown())

            {
                return;
            }

            marker.hideInfoWindow();
            marker.showInfoWindow();
        }
    }
}
