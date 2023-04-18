package com.example.firebaseexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private EditText emailET;
    private EditText passwordET;
    private Button signInBTN;
    private TextView signUpTV;
    private TextView forgotPasswordTV;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        signInBTN = findViewById(R.id.signInBTN);
        signUpTV = findViewById(R.id.signUpTV);
        forgotPasswordTV = findViewById(R.id.forgotPasswordTV);


        /**
         * retrieves the email and password input from the user
         * and authenticates the user through the google api
         */
        signInBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMail = emailET.getText().toString();
                String userPassword = passwordET.getText().toString();

                //using an if-else to make sure that the user enters email & password
               if(!userMail.equals("") && !userPassword.equals("")){
                   signInFirebase(userMail, userPassword);
               }else {
                   Toast.makeText(SignInActivity.this, "Please enter an email and password", Toast.LENGTH_SHORT).show();
               }
            }
        });

        /**
         * The user is redirected to the {@link SignUpActivity} after clicking the {@link signUpTV}
         */
        signUpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        /**
         * The user is redirected to the {@link ForgetPasswordActivity} after clicking the {@link forgotPasswordTV}
         */
        forgotPasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignInActivity.this, ForgetPasswordActivity.class);
                startActivity(i);
            }
        });
    }

    public void signInFirebase (String userMail, String userPassword) {
        auth.signInWithEmailAndPassword(userMail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // If sign up is successful, redirect to main activity
                            Intent i = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignInActivity.this, "Mail or Password is not correct", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * User recognition process
     * and the user is redirected to the {@link MainActivity}
     */
    @Override
    protected void onStart() {
        super.onStart();
        //user recognition process
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            Intent i = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
