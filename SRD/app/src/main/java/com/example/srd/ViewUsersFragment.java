package com.example.srd;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewUsersFragment extends DialogFragment {
    View view_user_diag;
    Cursor usersCursor;
    ArrayList<EmployeeModel> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_user_diag = inflater.inflate(R.layout.delete_users,null);
        view_user_diag.setClipToOutline(true);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.layout_bg);
        list = new ArrayList<>();

        RecyclerView rv = view_user_diag.findViewById(R.id.delete_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(linearLayoutManager);
        ViewUsersAdapter viewUsersAdapter = new ViewUsersAdapter(getActivity(),getActivity().getSupportFragmentManager(),list);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference(ConstantsClass.CONST_ROOT).child(ConstantsClass.CONST_LOGIN_NODE).child(ConstantsClass.CONST_LOGIN_USER);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("SRD_test","addValueeventlistener called : snapshot "+snapshot.toString());
                list.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    EmployeeModel emp = ds.getValue(EmployeeModel.class);
                    list.add(emp);
                }
                rv.setAdapter(viewUsersAdapter);
                //viewUsersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view_user_diag;
    }
    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }


}
