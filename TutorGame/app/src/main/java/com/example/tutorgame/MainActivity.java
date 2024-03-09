package com.example.tutorgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

import com.example.tutorgame.Activities.SetsActivity;
import com.example.tutorgame.Activities.SetsActivity2;
import com.example.tutorgame.Activities.SetsActivity3;

public class MainActivity extends AppCompatActivity {

    CardView history, science, mathematics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        history=findViewById(R.id.history);
        science=findViewById(R.id.science);
        mathematics=findViewById(R.id.mathematics);

        history.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SetsActivity.class)));

        science.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SetsActivity2.class)));

        mathematics.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SetsActivity3.class)));
    }
}