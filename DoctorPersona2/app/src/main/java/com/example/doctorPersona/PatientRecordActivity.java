package com.example.doctorPersona;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PatientRecordActivity extends AppCompatActivity {
    TextView mData,mData1,mData2,mData3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_record);
        mData = findViewById(R.id.DoctorName_TextView);
        mData1= findViewById(R.id.DoctorDOB_TextView);
        mData2= findViewById(R.id.DoctorPrescription_TextView);

        Intent intent = getIntent();
        String patName = intent.getStringExtra("Namex");
        String patDOB = intent.getStringExtra("DOBx");
        String patPers = intent.getStringExtra("Persx");
        mData.setText("Name: "+ patName);
        mData1.setText("DOB: "+patDOB);
        mData2.setText("Prescriptions: "+patPers);
    }
    public void DoctorAppointment(View view) {
        Intent intent = new Intent(PatientRecordActivity.this, DoctorAppointmentActivity.class);
        startActivity(intent);
    }
    public void DoctorBack(View view) {
        Intent intent = new Intent(PatientRecordActivity.this, DoctorDash.class);
        startActivity(intent);
    }


}