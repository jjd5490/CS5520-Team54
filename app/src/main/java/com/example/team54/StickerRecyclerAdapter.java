package com.example.team54;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StickerRecyclerAdapter extends RecyclerView.Adapter<StickerRecyclerAdapter.ViewHolder> {

    private List<MessageModel> messageList;
    private Context context;
    private final int[] stickerIDs = {
            R.drawable.angry,
            R.drawable.funny,
            R.drawable.glasses,
            R.drawable.scream,
            R.drawable.sick,
            R.drawable.sleepy,
            R.drawable.smile,
            R.drawable.thinking,
            R.drawable.unamused,
            R.drawable.wink
    };

    public StickerRecyclerAdapter(List<MessageModel> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sticker_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.senderDetail.setText(messageList.get(position).getSenderID());
        holder.timeDetail.setText(messageList.get(position).getRecipientID());
        holder.stickerDetail.setImageResource(R.drawable.funny);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView senderDetail;
        TextView timeDetail;
        ImageView stickerDetail;
        ViewHolder(View itemView) {
            super(itemView);
            senderDetail = itemView.findViewById(R.id.from_view);
            timeDetail = itemView.findViewById(R.id.time_view);
            stickerDetail = itemView.findViewById(R.id.imageView);
        }
    }


}