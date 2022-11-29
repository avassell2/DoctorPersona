package com.example.doctorPersona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DoctorAppointmentActivity extends AppCompatActivity {
    EditText mName, mDate, mTime, mReason;
    Button mSaveButton;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        mName = findViewById(R.id.Name_editText);
        mDate = findViewById(R.id.Date_editText);
        mTime = findViewById(R.id.Time_editText);
        mReason = findViewById(R.id.Reason_editText);
        mSaveButton = findViewById(R.id.Save_button);

        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = mAuth.getCurrentUser();
                String uid = user.getUid();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");



                String name = mName.getText().toString().trim();
                String date = mDate.getText().toString();
                String time = mTime.getText().toString();
                String reason = mReason.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    mName.setError("Please enter your name");
                    return;
                }
                if (TextUtils.isEmpty(date)) {
                    mDate.setError("Please enter your wanted date");
                    return;
                }
                if (TextUtils.isEmpty(time)) {
                    mDate.setError("Please enter your wanted time");
                    return;
                }
                if (TextUtils.isEmpty(reason)) {
                    mDate.setError("Please enter your reason");
                    return;
                }
                Map<String, String> data = new HashMap<>();
                data.put("name", name);
                data.put("date", date);
                data.put("time", time);
                data.put("reason", reason);

                fStore.collection("Appointment").document(uid).set(data);
                finish();


            }
        });
    }
    public void Back(View view) {
        Intent intent = new Intent(DoctorAppointmentActivity.this, DoctorDash.class);
        startActivity(intent);
    }
    public void Save(View view) {
        Intent intent = new Intent(DoctorAppointmentActivity.this, DoctorDash.class);
        startActivity(intent);
    }

}