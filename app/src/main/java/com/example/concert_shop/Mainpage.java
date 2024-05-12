package com.example.concert_shop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Mainpage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TicketAdapter ticketAdapter;
    private final List<Ticket> ticketList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mainpage);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ticketAdapter = new TicketAdapter(this, ticketList);
        recyclerView.setAdapter(ticketAdapter);

        loadTicketsFromFirestore();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadTicketsFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Tickets").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    Ticket ticket = document.toObject(Ticket.class);
                    ticketList.add(ticket);
                }
                ticketAdapter.notifyDataSetChanged();
            } else {
                Log.e("Firestore Error", Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage()));
                Toast.makeText(this, "Error getting documents: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void redirectUser(View view) {
        Intent intent = new Intent(this, AddTicket.class);
        startActivity(intent);
    }

    public void editTicket(View view) {
    }

    public void deleteTicket(View view) {
    }
}
