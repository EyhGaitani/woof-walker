package com.example.firebaseexample.Adapter;

import android.graphics.Color;
import android.graphics.Paint;
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
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query.
     * @param options
     */
    public MyAdapter(@NonNull FirebaseRecyclerOptions<WoofWalkerUser> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull WoofWalkerUser woofWalkerUser) {
        holder.firstNameTV.setText(woofWalkerUser.getUserFirstName());
        holder.lastNameTV.setText(woofWalkerUser.getUserLastName());
        holder.emailTV.setText(woofWalkerUser.getUserEmail());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_row, parent,false);
        return new MyAdapter.myViewHolder(view);
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

            //emailTV design
            emailTV.setPaintFlags(emailTV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            emailTV.setTextColor(Color.BLUE);
        }
    }
}
