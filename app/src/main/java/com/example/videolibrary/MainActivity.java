package com.example.videolibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageview;
    TextView no_data;

    DatabaseHelper DB;
    ArrayList<String> movie_id, movie_title, movie_year, movie_director, movie_rating, movie_duration;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        DB = new DatabaseHelper(MainActivity.this);
        movie_id = new ArrayList<>();
        movie_title = new ArrayList<>();
        movie_year = new ArrayList<>();
        movie_director = new ArrayList<>();
        movie_rating = new ArrayList<>();
        movie_duration = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this, this, movie_id, movie_title, movie_year, movie_director, movie_rating, movie_duration);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1) {
            recreate();
        }
    }

    void storeDataInArrays() {
        Cursor cursor = DB.readAllData();
        // Check if cursor count gets equal to zero. Means there is no data.
        if(cursor.getCount() == 0) {
//            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        } else {
            // Read all the data from our cursor
            while(cursor.moveToNext()) {
                movie_id.add(cursor.getString(0));
                movie_title.add(cursor.getString(1));
                movie_year.add(cursor.getString(2));
                movie_director.add(cursor.getString(3));
                movie_rating.add(cursor.getString(4));
                movie_duration.add(cursor.getString(5));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all) {
//            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
//            DatabaseHelper DB = new DatabaseHelper(this);
//            DB.deleteAllData();
//            // Refresh Activity. For clear showing of the consequences after the action is taken.
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//            finish();
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper DB = new DatabaseHelper(MainActivity.this);
                DB.deleteAllData();
                // Refresh Activity. For clear showing of the consequences after the action is taken.
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
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