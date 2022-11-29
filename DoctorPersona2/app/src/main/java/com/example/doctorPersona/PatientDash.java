package com.example.doctorPersona;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PatientDash extends AppCompatActivity {

    TextView mData,mData1,mData2,mData3;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dash);

        mData = findViewById(R.id.NameData_textView);
        mData1= findViewById(R.id.TimeData_textView);
        mData2= findViewById(R.id.DateData_textView);
        mData3= findViewById(R.id.ReasonData_textView);
        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();
        String uid = user.getUid();

        fStore.collection("Appointment").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    String name = documentSnapshot.getString("name");
                    String date = documentSnapshot.getString("date");
                    String time = documentSnapshot.getString("time");
                    String reason = documentSnapshot.getString("reason");
                    mData.setText("Hello " +name);
                    mData1.setText("Time: " + time);
                    mData2.setText("Date: " + date);
                    mData3.setText("Reason: " + reason);
                }
            }
        });


    }
    public void Prescription(View view) {
        Intent intent = new Intent(PatientDash.this, PatientPrescriptionActivity.class);
        startActivity(intent);
    }
    public void Appointment(View view) {
        Intent intent = new Intent(PatientDash.this, AppointmentActivity.class);
        startActivity(intent);
    }

    public void Exit(View view) {
        Intent intent = new Intent(PatientDash.this, MainActivity.class);
        startActivity(intent);
    }
}