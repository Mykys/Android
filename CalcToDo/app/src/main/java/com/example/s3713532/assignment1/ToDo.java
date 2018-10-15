package com.example.s3713532.assignment1;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ToDo extends AppCompatActivity {

    EditText editText;
    ListView listView;
    ArrayAdapter<String> adapter;
    Button buttonAddItem;
    SharedPreferences data;
    String selectedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        setTitle("To Do List");

        editText = findViewById(R.id.editText);
        listView = findViewById(R.id.listView);
        buttonAddItem = findViewById(R.id.buttonAddItem);

        data = PreferenceManager.getDefaultSharedPreferences(this);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        LoadPreferences();

        Button buttonT2M = findViewById(R.id.buttonT2M);
        Button buttonT2C = findViewById(R.id.buttonT2C);

        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = editText.getText().toString();
                if (!task.equals("")) {
                    adapter.add(task);
                    adapter.notifyDataSetChanged();
                    editText.setText(null);
                } else {
                    Toast.makeText(ToDo.this, "You have not entered a task", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedValue = listView.getItemAtPosition(i).toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(ToDo.this);
                builder.setMessage("Delete task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                adapter.remove(selectedValue);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).create().show();
            }
        });

        buttonT2M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToDo.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonT2C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToDo.this, Calculator.class);
                startActivity(intent);
            }
        });
    }

    protected void SavePreferences() {
        SharedPreferences.Editor editor = data.edit().clear();
        for (int i = 0; i < adapter.getCount(); i++) {
            editor.putString(String.valueOf(i), adapter.getItem(i));
        }
        editor.apply();
    }

    protected void LoadPreferences() {
        for (int i = 0; i < data.getAll().size(); i++) {
            String str = data.getString(String.valueOf(i), "");
            adapter.add(str);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        SavePreferences();
    }
}