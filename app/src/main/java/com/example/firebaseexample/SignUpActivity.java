package com.example.firebaseexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText signupEmailET;
    private EditText signupPasswordET;
    private Button signUpBTN;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupEmailET = findViewById(R.id.myEmailET);
        signupPasswordET = findViewById(R.id.myPasswordET);
        signUpBTN = findViewById(R.id.signUpBTN);

        /**
         * When user clicks the {@link signUpBTN}
         * a new user's account is created in Firebase Authentication system
         */
        signUpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = signupEmailET.getText().toString();
                String userPassword = signupPasswordET.getText().toString();
                //using an if-else to make sure that the user enters email & password
                if(!userEmail.equals("") && !userPassword.equals("")){
                    signUpFirebase(userEmail, userPassword);
                }else {
                    Toast.makeText(SignUpActivity.this, "Please enter an email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signUpFirebase (String userEmail, String userPassword) {
        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            reference.child("Users").child(auth.getUid()).child("userEmail").setValue(userEmail);
                            reference.child("Users").child(auth.getUid()).child("userPassword").setValue(userPassword);
                            // If sign up is successful, redirect to main activity
                            Toast.makeText(SignUpActivity.this
                            ,"Your account has been created", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            // If sign up fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this
                                    ,"Your account has NOT been created", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }
