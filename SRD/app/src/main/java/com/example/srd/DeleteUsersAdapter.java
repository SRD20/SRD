package com.example.srd;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DeleteUsersAdapter extends RecyclerView.Adapter<DeleteUsersViewHolder> {
    Context context;
    Cursor csr;
    DBHelper dbHelper;

    public DeleteUsersAdapter(Context c){
        this.context = c;
        dbHelper = DBHelper.getDBInstance(c);
    }
    @NonNull
    @Override
    public DeleteUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context = parent.getContext();
        View view  = LayoutInflater.from(context).inflate(R.layout.delete_users_card_layout,parent,false);
        Log.d("SRD_test","oncreateviewholder called");
        if(csr==null)
            csr = dbHelper.getCursor();
        return new DeleteUsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteUsersViewHolder holder, int position) {
        if(!csr.moveToPosition(position)) return;
        Log.d("SRD_test","in onbindviewer :"+csr.getString(0)+","+csr.getString(1));
        holder.emp_id.setText(csr.getString(0));
        holder.emp_name.setText(csr.getString(1));
    }

    @Override
    public int getItemCount() {
        Log.d("SRD_test","getItemCount :"+dbHelper.getRowCount());
        return (int)dbHelper.getRowCount();
    }

    public void updateCursor(Cursor nCursor){
        if(csr!=null) csr.close();
        csr = nCursor;
        if(nCursor!=null) notifyDataSetChanged();
    }
}
