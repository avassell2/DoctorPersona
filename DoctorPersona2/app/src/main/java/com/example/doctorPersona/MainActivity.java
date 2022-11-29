package com.example.doctorPersona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void DoctorLogin(View view) {
        Intent intent = new Intent(MainActivity.this, DoctorLoginActivity.class);
        startActivity(intent);
    }
    public void PatientLogin(View view) {
        Intent intent = new Intent(MainActivity.this, PatientLoginActivity.class);
        startActivity(intent);
    }
    public void CalendarView(View view) {
        Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
        startActivity(intent);
    }
    public void RegisterView(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}