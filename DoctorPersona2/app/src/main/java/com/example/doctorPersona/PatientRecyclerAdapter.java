package com.example.doctorPersona;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PatientRecyclerAdapter extends FirestoreRecyclerAdapter<NewPatient, PatientRecyclerAdapter.PatientViewHolder> {



    public PatientRecyclerAdapter(@NonNull FirestoreRecyclerOptions<NewPatient> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull PatientViewHolder holder, int position, @NonNull NewPatient model) {
        holder.textName.setText(model.getName());
        holder.textDate.setText(model.getdate());
        holder.textPers.setText(model.getPrescription());
    }



    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item,
                parent, false);
        return new PatientViewHolder(v);
    }


    class PatientViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textDate;
        TextView textPers;
        public PatientViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.patient_name);
            textDate = itemView.findViewById(R.id.patient_perscr);
            textPers = itemView.findViewById(R.id.patiebt_dob);
        }
    }











}

