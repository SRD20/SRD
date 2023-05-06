package com.example.srd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DeleteUsersFragment extends DialogFragment {
    View delete_diag;
    String[] dummy1 = {"abcd","edede","adsas","asdasd","ewrr","yjdygj","sdfgdg","sgdfgsdfg","sgfsdg","adsfsd",
                        "owoiefh","adugakdsg","adgjsad"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        delete_diag = inflater.inflate(R.layout.delete_users,null);
        delete_diag.setClipToOutline(true);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.layout_bg);
        RecyclerView rv = delete_diag.findViewById(R.id.delete_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(linearLayoutManager);
        DeleteUsersAdapter deleteUsersAdapter = new DeleteUsersAdapter(getActivity().getApplicationContext(),dummy1);
        rv.setAdapter(deleteUsersAdapter);
        return delete_diag;
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
