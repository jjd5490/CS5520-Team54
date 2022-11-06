package com.example.team54;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class StickerRecyclerAdapter extends RecyclerView.Adapter<StickerRecyclerAdapter.ViewHolder> {

    public List<MessageModel> messageList;
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

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageModel message = messageList.get(position);
        holder.senderDetail.setText(message.getSenderID());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-YYYY hh:mm:ss");
        Long time = Long.parseLong(message.getDateTime());
        holder.timeDetail.setText(dateFormat.format(time));
        int drawableID = Integer.parseInt(message.getResourceID());
        Log.d("**************************", String.valueOf(drawableID));
        holder.stickerDetail.setImageResource(drawableID);
    }

    @Override
    public int getItemCount() {
        if (messageList != null) {
            return messageList.size();
        } else {
            return 0;
        }
    }

    public void addMessage(MessageModel m) {
        this.messageList.add(m);
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