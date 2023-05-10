package com.example.srd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class DeleteUsersAdapter extends RecyclerView.Adapter<DeleteUsersViewHolder> {
    Context context;
    Cursor csr;
    DBHelper dbHelper;
    FragmentManager frag;
    EditUserDialogFragment.UpdateCallback callback;
    public DeleteUsersAdapter(Context c,FragmentManager f){
        this.context = c;
        frag = f;
        dbHelper = DBHelper.getDBInstance(c);
        csr = dbHelper.getCursor();
    }
    @NonNull
    @Override
    public DeleteUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context = parent.getContext();
        View view  = LayoutInflater.from(context).inflate(R.layout.delete_users_card_layout,parent,false);
        Log.d("SRD_test","oncreateviewholder called");
        return new DeleteUsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteUsersViewHolder holder, int position) {
        if(!csr.moveToPosition(position)) return;
        Log.d("SRD_test","in onbindviewer :"+csr.getString(0)+","+csr.getString(1));
        holder.emp_id.setText(csr.getString(0));
        holder.emp_name.setText(csr.getString(1));
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback = new EditUserDialogFragment.UpdateCallback() {
                    @Override
                    public void updateRecyclerView() {
                        Log.d("SRD_test","updateRecyclerView callback");
                        csr = dbHelper.getCursor();
                        notifyDataSetChanged();
                    }
                };
                EditUserDialogFragment editUsersDialog =new EditUserDialogFragment(holder.emp_id.getText().toString(),holder.emp_name.getText().toString(),holder.emp_id.getText().toString(),callback);
                editUsersDialog.setCancelable(true);
                editUsersDialog.show(frag, "AddUsers");
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteUserDialog(holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("SRD_test","getItemCount :"+dbHelper.getRowCount());
        return (int)dbHelper.getRowCount();
    }

    private void showDeleteUserDialog(DeleteUsersViewHolder holder) {
        //alertdialog is enough. upon confirmation fire delete query.
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle("Delete confirmation")
                .setMessage("Are you sure you want to delete this user ?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                dbHelper.deleteUser(holder.emp_id.getText().toString());
                dialogInterface.dismiss();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                csr = dbHelper.getCursor();
                Log.d("SRD_test","delete alertdialog onDismiss");
                notifyDataSetChanged();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();

    }
}
