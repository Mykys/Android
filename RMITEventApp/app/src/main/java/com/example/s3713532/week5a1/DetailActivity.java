package com.example.s3713532.week5a1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    TextView title, date, location, description;
    ImageView imageView, logo;
    Button favouriteBtn;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Event details");

        imageView = findViewById(R.id.event_image_header);
        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        location = findViewById(R.id.location);
        description = findViewById(R.id.description);
        logo = findViewById(R.id.logo);
        favouriteBtn = findViewById(R.id.favouriteBtn);

        final Event event = (Event) getIntent().getSerializableExtra("clickedEvent");

        String eventTitle = event.getTitle();
        String eventDate = event.getDate();
        String eventLocation = event.getLocation();
        String eventDescription = event.getDescription();
        String eventImage = event.getImage();

        title.setText(eventTitle);
        date.setText(eventDate);
        location.setText(eventLocation);
        description.setText(eventDescription);

        //Image
        if (!eventImage.startsWith("http")) {

            String base64Image = eventImage;
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);

            Glide.with(this)
                    .load(decodedString)
                    .placeholder(R.drawable.load)
                    .into(imageView);
        } else {

            Glide.with(this)
                    .load(eventImage)
                    .placeholder(R.drawable.load)
                    .into(imageView);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        favouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, FavouriteEvents.class);
                intent.putExtra("selectedEvent", event);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }
}
