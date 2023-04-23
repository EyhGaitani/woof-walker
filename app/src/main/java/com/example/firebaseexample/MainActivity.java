package com.example.firebaseexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.firebaseexample.Adapter.MyAdapter;
import com.example.firebaseexample.Model.WoofWalkerUser;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    MyAdapter adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    FirebaseUser firebaseUser = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        //configure the adapter by building FirebaseRecyclerOptions
        FirebaseRecyclerOptions<WoofWalkerUser> options =
                new FirebaseRecyclerOptions.Builder<WoofWalkerUser>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("UsersWW"), WoofWalkerUser.class)
                        .build();
        adapter = new MyAdapter(options);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //begin listening for data, call the startListening() method. You may want to call this in your onStart() method
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    // the stopListening() call removes the event listener and all data in the adapter. Call this method when the containing Activity stops
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    //method is called when the activity is created to initialize the contents of the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //method is called when the user selects an item from the options menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_account) {
               startActivity(new Intent(MainActivity.this, MyAccountActivity.class));
            }
        if (item.getItemId() == R.id.action_become_woof_walker) {
               startActivity(new Intent(MainActivity.this, BecomeWoofWalkerActivity.class));
        }
            if (item.getItemId() == R.id.action_log_out) {
                auth.signOut();
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
            }
            return super.onOptionsItemSelected(item);
        }
}
