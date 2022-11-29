package com.example.doctorPersona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateNewPatientActivity extends AppCompatActivity {
    EditText mName, mDOB, mPrescription;
    Button mAddButton;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_patient);

        mName = findViewById(R.id.editText);
        mDOB = findViewById(R.id.editText2);
        mPrescription = findViewById(R.id.editText3);
        mAddButton = findViewById(R.id.Add_button);

        fStore = FirebaseFirestore.getInstance();

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString().trim();
                String date = mDOB.getText().toString();
                String Prescription = mPrescription.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    mName.setError("Please enter your name");
                    return;
                }
                if (TextUtils.isEmpty(date)) {
                    mDOB.setError("Please enter the DOB");
                    return;
                }
                if (TextUtils.isEmpty(Prescription)) {
                    mPrescription.setError("Please enter the Prescription");
                    return;
                }
                Map<String, String> data = new HashMap<>();
                data.put("Name", name);
                data.put("DOB", date);
                data.put("Prescription", Prescription);

                fStore.collection("NewPatient").document(name).set(data);
                finish();
            }

        });
    }
    public void Back1(View view) {
        Intent intent = new Intent(CreateNewPatientActivity.this, DoctorDash.class);
        startActivity(intent);
    }
    public void Add(View view) {
        Intent intent = new Intent(CreateNewPatientActivity.this, DoctorDash.class);
        startActivity(intent);
    }
}