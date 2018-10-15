package com.example.s3713532.week5a1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.LinkedHashMap;
import java.util.Map;

public class AddNewEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add your event");

        final EditText title = findViewById(R.id.title);
        final EditText location = findViewById(R.id.location);
        final EditText date = findViewById(R.id.date);
        final EditText time = findViewById(R.id.time); // Combine date and time
        final EditText description = findViewById(R.id.description);
        final EditText imageUrl = findViewById(R.id.imageurl);
        Button addBtn = findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> postData = new LinkedHashMap<>();

                try {
                    postData.put("title", title.getText().toString());
                    postData.put("location", location.getText().toString());
                    postData.put("date", date.getText().toString() + " at " + time.getText().toString());
                    postData.put("description", description.getText().toString());
                    postData.put("image", imageUrl.getText().toString());
                    postData.put("image_url", "http://localhap.blob.core.windows.net/shared/event-placeholder.jpg");

                    SendEventDetails details = new SendEventDetails();
                    details.execute(details.wwwEncodeMap(postData));

                    Toast.makeText(AddNewEvent.this, "You have successfully added an event!", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
