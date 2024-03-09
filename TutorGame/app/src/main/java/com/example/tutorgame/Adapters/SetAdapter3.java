package com.example.tutorgame.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorgame.Activities.QuestionActivity3;
import com.example.tutorgame.Models.SetModel;
import com.example.tutorgame.R;
import com.example.tutorgame.databinding.ItemSetBinding;

import java.util.ArrayList;

public class SetAdapter3 extends RecyclerView.Adapter<SetAdapter3.viewHolder>{

    Context context;
    ArrayList<SetModel> list;

    public SetAdapter3(Context context, ArrayList<SetModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SetAdapter3.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_set, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final SetModel model = list.get(position);

        holder.binding.setName.setText(model.getSetName());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, QuestionActivity3.class);
            intent.putExtra("set",model.getSetName());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ItemSetBinding binding;
        public viewHolder(@NonNull View itemView){
            super(itemView);

            binding = ItemSetBinding.bind(itemView);
        }
    }

}