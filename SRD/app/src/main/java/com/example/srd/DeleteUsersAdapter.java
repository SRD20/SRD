package com.example.srd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DeleteUsersAdapter extends RecyclerView.Adapter<DeleteUsersViewHolder> {
    Context context;
    String[] dummy;
    public DeleteUsersAdapter(Context c, String[] data){
        context = c;
        dummy = data;
    }
    @NonNull
    @Override
    public DeleteUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context = parent.getContext();
        View view  = LayoutInflater.from(context).inflate(R.layout.delete_users_card_layout,parent,false);
        return new DeleteUsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteUsersViewHolder holder, int position) {
        holder.emp_name.setText(dummy[position]);
        holder.emp_id.setText("e"+position);
    }

    @Override
    public int getItemCount() {
        return dummy.length;
    }
}
