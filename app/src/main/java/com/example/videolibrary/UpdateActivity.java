package com.example.videolibrary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, year_input, director_input, rating_input, duration_input;
    Button update_button, delete_button;

    String id, title, year, director, rating, duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.title_input_update);
        year_input = findViewById(R.id.year_input_update);
        director_input = findViewById(R.id.director_input_update);
        rating_input = findViewById(R.id.rating_input_update);
        duration_input = findViewById(R.id.duration_input_update);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        // First call get and set data method
        getAndSetIntentDate();

        // Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // then call the updated data
                DatabaseHelper DB = new DatabaseHelper(UpdateActivity.this);
                title = title_input.getText().toString().trim();
                year = year_input.getText().toString().trim();
                director = director_input.getText().toString().trim();
                rating = rating_input.getText().toString().trim();
                duration = duration_input.getText().toString().trim();
                DB.updateData(id, title, year, director, rating, duration);
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DatabaseHelper DB = new DatabaseHelper(UpdateActivity.this);
//                DB.deleteOneRow(id);
                confirmDialog();
            }
        });
    }

    void getAndSetIntentDate() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("year") && getIntent().hasExtra("director") && getIntent().hasExtra("rating") && getIntent().hasExtra("duration")) {
            // Getting Data from intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            year = getIntent().getStringExtra("year");
            director = getIntent().getStringExtra("director");
            rating = getIntent().getStringExtra("rating");
            duration = getIntent().getStringExtra("duration");

            // Setting intent Data
            title_input.setText(title);
            year_input.setText(year);
            director_input.setText(director);
            rating_input.setText(rating);
            duration_input.setText(duration);
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper DB = new DatabaseHelper(UpdateActivity.this);
                DB.deleteOneRow(id);
//                finish();
                // replaced with:
                // Refresh Activity. For clear showing of the consequences after the action is taken.
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

}