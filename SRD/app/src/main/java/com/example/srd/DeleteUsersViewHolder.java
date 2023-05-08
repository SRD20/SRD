package com.example.srd;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class DeleteUsersViewHolder extends RecyclerView.ViewHolder {
    View mView;
    TextView emp_id,emp_name;
    ImageButton edit,delete;
    Context ctx;
    FragmentManager frg;
    public DeleteUsersViewHolder(@NonNull View itemView, Context c,FragmentManager f) {
        super(itemView);
        ctx = c;
        frg = f;
        emp_id = itemView.findViewById(R.id.delcard_eid);
        emp_name = itemView.findViewById(R.id.delcard_ename);
        edit = itemView.findViewById(R.id.edit_btn);
        delete = itemView.findViewById(R.id.delete_btn);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show users edit dialog
                showEditUsersDialog();
                Toast.makeText(ctx, "Edit user", Toast.LENGTH_SHORT).show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dialog for deleting the user
                showDeleteUserDialog();
                Toast.makeText(ctx, "Delete user", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDeleteUserDialog() {
        //alertdialog is enough. upon confirmation fire delete query.
    }

    private void showEditUsersDialog() {
        EditUserDialogFragment editUsersDialog =new EditUserDialogFragment(emp_name.getText().toString(),emp_id.getText().toString());
        editUsersDialog.setCancelable(true);
        editUsersDialog.show(frg, "AddUsers");
    }
}
