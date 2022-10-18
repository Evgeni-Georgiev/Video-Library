package com.example.videolibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    EditText title_input, year_input, director_input, rating_input, duration_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        title_input = findViewById(R.id.title_input);
        year_input = findViewById(R.id.year_input);
        director_input = findViewById(R.id.director_input);
        rating_input = findViewById(R.id.rating_input);
        duration_input = findViewById(R.id.duration_input);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper DB = new DatabaseHelper(AddActivity.this);
                DB.addBook(title_input.getText().toString().trim(),
                        Integer.valueOf(year_input.getText().toString().trim()),
                        director_input.getText().toString().trim(),
                        Integer.valueOf(rating_input.getText().toString().trim()),
                        Integer.valueOf(duration_input.getText().toString().trim())
                );
            }
        });

    }
}