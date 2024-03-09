package com.example.tutorgame.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.tutorgame.Adapters.SetAdapter3;
import com.example.tutorgame.Models.SetModel;
import com.example.tutorgame.databinding.ActivitySets3Binding;

import java.util.ArrayList;

public class SetsActivity3 extends AppCompatActivity {

    ActivitySets3Binding binding;
    ArrayList<SetModel>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySets3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.mathematicsSetsRecy.setLayoutManager(linearLayoutManager);

        list.add(new SetModel("SET-1"));
        list.add(new SetModel("SET-2"));
        list.add(new SetModel("SET-3"));

        SetAdapter3 adapter = new SetAdapter3(this,list);
        binding.mathematicsSetsRecy.setAdapter(adapter);
    }
}