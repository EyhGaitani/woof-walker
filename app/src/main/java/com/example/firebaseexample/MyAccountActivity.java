package com.example.firebaseexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class MyAccountActivity extends AppCompatActivity {

    private EditText myEmailET;
    private EditText myPasswordET;
    private Button updateBTN;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    FirebaseUser firebaseUser = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);

        myEmailET = findViewById(R.id.myEmailET);
        myPasswordET = findViewById(R.id.myPasswordET);
        updateBTN = findViewById(R.id.updateBTN);

        getUserInfo();

        /**
         * When user clicks the {@link updateBTN}  the profile is updated
         */
        updateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }

    //updates the user email and password in the Firebase Realtime Database
    public void updateProfile () {
        String userEmail = myEmailET.getText().toString();
        reference.child("Users").child(firebaseUser.getUid()).child("userEmail").setValue(userEmail);
        String userPassword = myPasswordET.getText().toString();
        reference.child("Users").child(firebaseUser.getUid()).child("userPassword").setValue(userPassword);
    }

    public void getUserInfo(){
        reference.child("Users").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
           //retrieves user data from the Firebase Realtime Database and populates the myEmailET and myPasswordET with the user's email and password
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userEmail = snapshot.child("userEmail").getValue().toString();
                String userPassword = snapshot.child("userPassword").getValue().toString();
                myEmailET.setText(userEmail);
                myPasswordET.setText(userPassword);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //If it fails, display a message to the user.
                Toast.makeText(MyAccountActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}