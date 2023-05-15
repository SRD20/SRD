package com.example.srd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.HashMap;


public class EditUserDialogFragment extends DialogFragment {
    View mView;
    EditText name,ph_no;
    Button update, cancel_upd;
    String uname,phno,eid;
    UpdateCallback upcb;

    public EditUserDialogFragment(String eid,String name,String phno,UpdateCallback upcb){
        this.eid = eid;
        uname = name;
        this.phno = phno;
        this.upcb = upcb;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.addusers1,null);
        name = mView.findViewById(R.id.username_update);
        ph_no = mView.findViewById(R.id.phn_no_update);
        update = mView.findViewById(R.id.update_user);
        cancel_upd = mView.findViewById(R.id.cancel_update);
        name.setText(uname);
        ph_no.setText(phno);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedname = name.getText().toString();
                String updatedphno = ph_no.getText().toString();
                if(!uname.equals(updatedname) && !phno.equals(updatedphno)) {
                    DBHelper dbhelper = DBHelper.getDBInstance(getActivity().getApplicationContext());
                    dbhelper.updateUser(eid,updatedname,updatedphno);
                    //Firebase RTDB update
                    firebaseUpdate(eid,updatedname,updatedphno);
                    upcb.updateRecyclerView();
                }
                dismiss();
            }
        });

        cancel_upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return mView;
    }

    private void firebaseUpdate(String eid,String updatedname, String updatedphno) {
        HashMap<String,Object> hm = new HashMap<>();
        hm.put("name",updatedname);
        hm.put("phoneNumber",updatedphno);
        DAOEmployee daoEmployee = new DAOEmployee();
        daoEmployee.updateEmployee(eid,hm);
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    public interface UpdateCallback{
        void updateRecyclerView();
    }
}
