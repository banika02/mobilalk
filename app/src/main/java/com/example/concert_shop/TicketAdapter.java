package com.example.concert_shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private Context context;
    private List<Ticket> ticketList;

    public TicketAdapter(Context context, List<Ticket> ticketList) {
        this.context = context;
        this.ticketList = ticketList;
    }


    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ticketlayout, parent, false);
        return new TicketViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket ticket = ticketList.get(position);
        holder.artistName.setText(ticket.getArtistName());
        holder.venue.setText(ticket.getVenue());
        holder.description.setText(ticket.getDescription());
        holder.price.setText(ticket.getPrice());
        Glide.with(context).load(ticket.getImage()).into(holder.imageView);


        if (ticket.getImage() != null && !ticket.getImage().isEmpty()) {
            Glide.with(context)
                    .load(ticket.getImage())
                    .placeholder(R.drawable.noimage)
                    .into(holder.imageView);
        } else {
            Glide.with(context)
                    .load(R.drawable.noimage)
                    .into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        public TextView artistName, venue, description, price;
        public ImageView imageView;

        public TicketViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.kep);
            artistName = itemView.findViewById(R.id.artist_name);
            venue = itemView.findViewById(R.id.venue);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
        }
    }
}

