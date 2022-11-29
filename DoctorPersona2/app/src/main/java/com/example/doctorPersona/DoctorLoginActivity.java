package com.example.doctorPersona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorLoginActivity extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mLoginButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        mEmail = findViewById(R.id.Doctor_Email_EditText);
        mPassword = findViewById(R.id.Doctor_password_EditText);
        mLoginButton = findViewById(R.id.Login1_button);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText() .toString() .trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Please enter Email");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Please enter Password");
                    return;
                }

                if (password.length() < 6){
                    mPassword.setError("Password must have 6 or more characters");
                    return;
                }

                //Authenticate the user
                fAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        checkIfDoctor(authResult.getUser().getUid());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DoctorLoginActivity.this, "Failed to Login", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void checkIfDoctor(String uid){
        DocumentReference df = fStore.collection("Users").document(uid);
        //extract data from document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess:" + documentSnapshot.getData());
                //identify access level
                if(documentSnapshot.getString("isDoctor") != null){
                    //user is doctor
                    startActivity(new Intent(getApplicationContext(),DoctorDash.class));
                    finish();
                }else{
                    if (documentSnapshot.getString("isPatient") != null){
                        Toast.makeText(DoctorLoginActivity.this,"Please sign in from Patient portal",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                }

            }
        });
    }


}