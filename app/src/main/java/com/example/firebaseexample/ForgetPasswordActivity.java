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
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText emailResetET;
    private Button resetBTN;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        emailResetET = findViewById(R.id.emailResetET);
        resetBTN = findViewById(R.id.resetBTN);

        /**
         * When user clicks the {@link resetBTN}, an email is sent to the user's registered
         * mail address and the user is redirected to the SignInActivity
         * or handles the case that the email was not sent
         */
        resetBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = emailResetET.getText().toString();
                auth.sendPasswordResetEmail(userEmail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgetPasswordActivity.this
                                            ,"Check your emails", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(ForgetPasswordActivity.this, SignInActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    // If sign up fails, display a message to the user.
                                    Toast.makeText(ForgetPasswordActivity.this
                                            ,"Email address is invalid", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}