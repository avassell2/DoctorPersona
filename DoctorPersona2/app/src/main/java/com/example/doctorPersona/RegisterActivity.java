package com.example.doctorPersona;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText mFullName, mEmail, mPassword;
    Button mRegisterBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    CheckBox mDoctor_checkBox, mPatient_checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.FullName_EditText);
        mEmail    = findViewById(R.id.Email_EditText);
        mPassword = findViewById(R.id.Password_EditText);
        mRegisterBtn = findViewById(R.id.Register2_button);
        mDoctor_checkBox = findViewById(R.id.Doctor_checkBox);
        mPatient_checkBox = findViewById(R.id.Patient_checkBox);

        //two checkbox *cannot* be selected at the same time
        mDoctor_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (compoundButton.isChecked()){
                    mPatient_checkBox.setChecked(false);
                }
            }
        });

        mPatient_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (compoundButton.isChecked()){
                    mDoctor_checkBox.setChecked(false);
                }
            }
        });



        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText() .toString().trim();
                String password = mPassword.getText().toString().trim();
                String name = mFullName.getText().toString();

                //radio button validation
                if(!(mDoctor_checkBox.isChecked() || mPatient_checkBox.isChecked())){
                    Toast.makeText(RegisterActivity.this, "Please select account type", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(name)){
                    mFullName.setError("Please enter Full Name");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Please enter Email");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Please enter Password");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password must have 6 or more characters");
                    return;
                }


                //Register the user in firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                        //set up fireStore
                        userID = fAuth.getCurrentUser().getUid();

                        DocumentReference df = fStore
                                .collection("Users").document(userID);

                        Map<String,Object> userInfo = new HashMap<>();
                        userInfo.put("FullName", name);
                        userInfo.put("Email", email);

                        //specify by level from check box
                        if(mDoctor_checkBox.isChecked()){
                            userInfo.put("isDoctor","1");
                        }
                        if(mPatient_checkBox.isChecked()){
                            userInfo.put("isPatient","1");
                        }



                        df.set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: ");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: " + e.toString());
                            }
                        });
                        //Send to desire screen
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Failed to Create Account", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
    public void Back(View view) {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }
}