package com.example.team54;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.List;

public class StickerActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private String userID;
    private TextView usernameDisplay;
    public UserModel user;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);
        createChannel();
        recyclerView = findViewById(R.id.receipt_history_recyclerView);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        userID = getIntent().getExtras().getString("UID");
        db = FirebaseDatabase.getInstance();
        usernameDisplay = findViewById(R.id.username_display);
        loadUser(new UserCallback() {
            @Override
            public void onGetUser(UserModel userObject) {
                user = userObject;
                String userDisplayText = "User: " + user.getName();
                usernameDisplay.setText(userDisplayText);
                List<MessageModel> messagesReceived = user.getMessagesReceived();
                if (messagesReceived != null) {
                    Collections.sort(messagesReceived);
                    adapter = new StickerRecyclerAdapter(messagesReceived, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        db.getReference().child("Users/" + userID)
                .child("messagesReceived")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        db.getReference().child("Users/" + userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                user = task.getResult().getValue(UserModel.class);
                                InboxModel inboxModel = user.getInbox();
                                if (inboxModel != null) {
                                    long lastSent = Long.valueOf(inboxModel.getDate());
                                    if ((System.currentTimeMillis() - lastSent) < 5000) {
                                        notifyUser(inboxModel.getSenderID(), inboxModel.getResourceID());
                                    }
                                }
                                List<MessageModel> messagesReceived = user.getMessagesReceived();
                                if (messagesReceived != null) {
                                    Collections.sort(messagesReceived);
                                    StickerRecyclerAdapter newAdapter = new StickerRecyclerAdapter(messagesReceived, getApplicationContext());
                                    recyclerView.setAdapter(newAdapter);
                                }
                            }
                        });
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backHome = new Intent(this, MainActivity.class);
        backHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(backHome);
    }

    public void loadUser(UserCallback callback) {
        db.getReference().child("Users").child(userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d("**************************", task.getResult().toString());
                    user = task.getResult().getValue(UserModel.class);
                    if (user != null) {
                        callback.onGetUser(task.getResult().getValue(UserModel.class));
                    }
                }
            }
        });
    }

    public void testChildListener(View view) {
        db.getReference().child("Users/" + userID)
                .runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        UserModel user = currentData.getValue(UserModel.class);
                        if (user != null) {
                            user.receiveMessage(new MessageModel("Team54", userID, String.valueOf(R.drawable.glasses), String.valueOf(System.currentTimeMillis())));
                            user.setInbox(new InboxModel(
                                    "Team54",
                                    String.valueOf(R.drawable.glasses),
                                    String.valueOf(System.currentTimeMillis())
                            ));
                            currentData.setValue(user);
                            return Transaction.success(currentData);
                        }
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                    }
                });
    }

    public void openSendMessage(View view) {
        Intent messageIntent = new Intent(getApplicationContext(), SendMessage.class);
        Bundle userData = new Bundle();
        userData.putString("UID", userID);
        messageIntent.putExtras(userData);
        startActivity(messageIntent);
    }

    public void openHistory(View view) {
        Intent historyIntent = new Intent(getApplicationContext(), HistoryActivity.class);
        Bundle userData = new Bundle();
        userData.putString("UID", userID);
        historyIntent.putExtras(userData);
        startActivity(historyIntent);
    }

    public void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel msgChannel = new NotificationChannel("100",
                    "Sticker Inbox",
                    NotificationManager.IMPORTANCE_HIGH);
            msgChannel.setDescription("Notifies users when friends send them a sticker");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(msgChannel);
        }
    }

    public void notifyUser(String sender, String resourceID) {
        Bitmap emojiIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                Integer.parseInt(resourceID));
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "100")
                .setContentTitle(sender + " sent you a new sticker!!!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.team54_icon_foreground)
                .setLargeIcon(emojiIcon)
                .setAutoCancel(true);
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(1, builder.build());
    }
}