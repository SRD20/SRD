package com.example.srd;


import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DeleteUsersViewHolder extends RecyclerView.ViewHolder {
    View mView;
    TextView emp_id,emp_name;
    ImageButton edit,delete;
    public DeleteUsersViewHolder(@NonNull View itemView) {
        super(itemView);
        emp_id = itemView.findViewById(R.id.delcard_eid);
        emp_name = itemView.findViewById(R.id.delcard_ename);
        edit = itemView.findViewById(R.id.edit_btn);
        delete = itemView.findViewById(R.id.delete_btn);
    }

}
