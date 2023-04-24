package com.example.firebaseexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.example.firebaseexample.Adapter.MyAdapter;
import com.example.firebaseexample.Model.WoofWalkerUser;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private MyAdapter adapter;
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

        // Add a touch listener to the RecyclerView to start an email intent when the user taps an item's email address
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_UP) {
                    View child = rv.findChildViewUnder(e.getX(), e.getY());
                    if (child != null) {
                        TextView emailTV = child.findViewById(R.id.emailTV);
                        String email = emailTV.getText().toString();
                        if (!email.isEmpty()) {
                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
                            startActivity(Intent.createChooser(emailIntent, "Send email"));
                        }
                    }
                }
                return false;
            }
            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                // Do nothing
            }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                // Do nothing
            }
        });
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
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processSearch(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                processSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void processSearch(String query) {
        FirebaseRecyclerOptions<WoofWalkerUser> options =
                new FirebaseRecyclerOptions.Builder<WoofWalkerUser>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("UsersWW").orderByChild("userEmail").startAt(query)
                                .endAt(query + "\uf8ff"), WoofWalkerUser.class)
                        .build();
        adapter = new MyAdapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
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
