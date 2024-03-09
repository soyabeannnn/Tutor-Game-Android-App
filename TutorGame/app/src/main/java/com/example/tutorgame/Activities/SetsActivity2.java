package com.example.tutorgame.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.tutorgame.Adapters.SetAdapter2;
import com.example.tutorgame.Models.SetModel;
import com.example.tutorgame.databinding.ActivitySets2Binding;


import java.util.ArrayList;

public class SetsActivity2 extends AppCompatActivity {

    ActivitySets2Binding binding;
    ArrayList<SetModel>list; //store data to display at recycle view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySets2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.scienceSetsRecy.setLayoutManager(linearLayoutManager);

        list.add(new SetModel("SET-1"));
        list.add(new SetModel("SET-2"));
        list.add(new SetModel("SET-3"));

        SetAdapter2 adapter = new SetAdapter2(this,list);
        binding.scienceSetsRecy.setAdapter(adapter);

    }
}