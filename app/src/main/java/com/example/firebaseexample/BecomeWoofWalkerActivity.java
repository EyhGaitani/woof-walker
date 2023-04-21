package com.example.firebaseexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BecomeWoofWalkerActivity extends AppCompatActivity {

    private EditText firstNameET;
    private EditText lastNameET;
    private EditText emailET;
    private Button okBTN;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    FirebaseUser firebaseUser = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_woof_walker);

        firstNameET = findViewById(R.id.firstNameET);
        lastNameET = findViewById(R.id.lastNameET);
        emailET = findViewById(R.id.emailET);
        okBTN = findViewById(R.id.okBTN);

        getUserInfo();
        //getWoofWalkerUserInfo();

        /**
         * When user clicks the {@link okBTN}  the profile is created
         * and the user is transferred to the MainActivity
         */
        okBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProfile();
                Intent i = new Intent(BecomeWoofWalkerActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    //creates the user Woof Walker account in the Firebase Realtime Database
    public void createProfile() {
        String userFirstName = firstNameET.getText().toString();
        reference.child("UsersWW").child(firebaseUser.getUid()).child("userFirstName").setValue(userFirstName);
        String userLastName = lastNameET.getText().toString();
        reference.child("UsersWW").child(firebaseUser.getUid()).child("userLastName").setValue(userLastName);
        String userEmail = emailET.getText().toString();
        reference.child("UsersWW").child(firebaseUser.getUid()).child("userEmail").setValue(userEmail);
    }

    public void getUserInfo(){
        reference.child("Users").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            //retrieves user data from the Firebase Realtime Database and populates the emailET with the user's email
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userEmail = snapshot.child("userEmail").getValue().toString();
                emailET.setText(userEmail);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //If it fails, display a message to the user.
                Toast.makeText(BecomeWoofWalkerActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**public void getWoofWalkerUserInfo(){
        reference.child("UsersWW").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            //retrieves user data from the Firebase Realtime Database and populates the firstNameET with the user's firsName
            //and the lastNameET with the user's lastName
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userFirstName = snapshot.child("userFirstName").getValue().toString();
                firstNameET.setText(userFirstName);
                String userLastName = snapshot.child("userLastName").getValue().toString();
                lastNameET.setText(userLastName);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //If it fails, display a message to the user.
                Toast.makeText(BecomeWoofWalkerActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }*/


    }

