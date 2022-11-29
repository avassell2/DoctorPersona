package com.example.doctorPersona;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DoctorDash extends AppCompatActivity {
    private String patName;
    private String patPers;
    private String patDOB;
    TextView mData,mData1,mData2,mData3;
    EditText mName;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    Button mSearchButton;
    private PatientRecyclerAdapter mAdapter;
    private CollectionReference patRef = fStore.collection("NewPatient");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dash);


        mData = findViewById(R.id.NameData_textView);
        mData1= findViewById(R.id.TimeData_textView);
        mData2= findViewById(R.id.DateData_textView);
        mData3= findViewById(R.id.ReasonData_textView);
        mName = findViewById(R.id.Name_editText);
        mSearchButton = findViewById(R.id.SearchButton);
        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        Query query = fStore.collection("NewPatient")
                .orderBy("Name", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<NewPatient> options = new FirestoreRecyclerOptions.Builder<NewPatient>()
                .setQuery(query, NewPatient.class)
                .build();

        mAdapter = new PatientRecyclerAdapter(options);
        recyclerView.setAdapter(mAdapter);


        mName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("TAG","Searchbox has changed to: " + s.toString());


                Query query = fStore.collection(("NewPatient")).whereEqualTo("Name", s.toString()).orderBy(
                        "Name", Query.Direction.ASCENDING
                );FirestoreRecyclerOptions<NewPatient> options = new FirestoreRecyclerOptions.Builder<NewPatient>()
                        .setQuery(query, NewPatient.class)
                        .build();
                mAdapter.updateOptions(options);

                fStore.collection("NewPatient").whereEqualTo("Name",  mName.getText().toString()
                ).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot documentSnapshot2: task.getResult()){
                                        patName = documentSnapshot2.getString("Name");
                                        patDOB = documentSnapshot2.getString("DOB");
                                        patPers = documentSnapshot2.getString("Prescription");
                                    }
                                }
                            }
                        });

            }
        });

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // continue work here
                Intent intent = new Intent(DoctorDash.this, PatientRecordActivity.class);
                intent.putExtra("Namex", patName);
                intent.putExtra("Persx", patPers);
                intent.putExtra("DOBx", patDOB);
                startActivity(intent);
            }
        });

        fStore.collection("Appointment").orderBy("date", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                String name = documentSnapshot.getString("name");
                                String date = documentSnapshot.getString("date");
                                String time = documentSnapshot.getString("time");
                                String reason = documentSnapshot.getString("reason");
                                mData.setText("Patient Name: " +name);
                                mData1.setText("Time: " + time);
                                mData2.setText("Date: " + date);
                                mData3.setText("Reason: " + reason);
                            }
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    public void Exit(View view) {
        Intent intent = new Intent(DoctorDash.this, MainActivity.class);
        startActivity(intent);
    }
    public void Search(View view) {

        Intent intent = new Intent(DoctorDash.this, PatientRecordActivity.class);
        startActivity(intent);
    }
    public void New(View view) {
        Intent intent = new Intent(DoctorDash.this, CreateNewPatientActivity.class);
        startActivity(intent);
    }

}