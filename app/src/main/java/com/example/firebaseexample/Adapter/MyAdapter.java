package com.example.firebaseexample.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firebaseexample.Model.WoofWalkerUser;
import com.example.firebaseexample.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MyAdapter extends FirebaseRecyclerAdapter<WoofWalkerUser, MyAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     * @param options
     */
    public MyAdapter(@NonNull FirebaseRecyclerOptions<WoofWalkerUser> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull WoofWalkerUser WoofWalkerUser) {
        holder.firstNameTV.setText(WoofWalkerUser.getUserFirstName());
        holder.lastNameTV.setText(WoofWalkerUser.getUserLastName());
        holder.emailTV.setText(WoofWalkerUser.getUserEmail());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_row,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView firstNameTV;
        TextView lastNameTV;
        TextView emailTV;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            firstNameTV = itemView.findViewById(R.id.firstNameTV);
            lastNameTV = itemView.findViewById(R.id.lastNameTV);
            emailTV = itemView.findViewById(R.id.emailTV);
        }
    }
}
