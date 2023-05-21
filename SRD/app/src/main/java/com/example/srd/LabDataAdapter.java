package com.example.srd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class LabDataAdapter extends FirebaseRecyclerAdapter<LabDataModel,LabDataAdapter.LabDataViewHolder> {

    private Context ctx;
    DataChangedCallback cb;

    public LabDataAdapter(@NonNull FirebaseRecyclerOptions<LabDataModel> options) {
        super(options);
    }
    public LabDataAdapter(@NonNull FirebaseRecyclerOptions<LabDataModel> options,DataChangedCallback cb) {
        super(options);
        this.cb = cb;
    }
    @Override
    protected void onBindViewHolder(@NonNull LabDataViewHolder holder, int position, @NonNull LabDataModel model) {
        holder.name.setText(model.getEmpId());
        holder.temp.setText(model.getTemp());
        holder.humidity.setText(model.getHumidity());
        holder.time.setText(model.getTime());
    }

    @NonNull
    @Override
    public LabDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lab_data_row,parent,false);
        return new LabDataViewHolder(view);
    }

    @Override
    public void onDataChanged() {
        if(getItemCount() == 0)
            cb.onDataChangedCallback();
    }


    static class LabDataViewHolder extends RecyclerView.ViewHolder{
        TextView temp,humidity,name,time;
        public LabDataViewHolder(@NonNull View itemView) {
            super(itemView);
            temp = itemView.findViewById(R.id.temp_tv);
            humidity = itemView.findViewById(R.id.humidity_tv);
            name = itemView.findViewById(R.id.emp_name);
            time = itemView.findViewById(R.id.time_tv);
        }
    }
    public interface DataChangedCallback{
        void onDataChangedCallback();
    }
}
