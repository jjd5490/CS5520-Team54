package com.example.team54;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GamePlayActivity extends AppCompatActivity {

    int[] vowel_positions = new int[4];
    int[] consonant_positions = new int[7];
    HashMap<Integer, String> vowelLookup = new HashMap<>();
    HashMap<Integer, String> consonantLookup = new HashMap<>();
    HashMap<String, Integer> imageLookup = new HashMap<>();
    String username;
    String gameKey;
    WCGameModel gameData;
    FirebaseDatabase db;
    String word;
    Boolean ready;
    String role;
    String op_role;

    ImageView LetterBank1;
    ImageView LetterBank2;
    ImageView LetterBank3;
    ImageView LetterBank4;
    ImageView LetterBank5;
    ImageView LetterBank6;
    ImageView LetterBank7;
    ImageView LetterBank8;
    ImageView LetterBank9;
    ImageView LetterBank10;
    ImageView LetterBank11;
    List<ImageView> imageViews;
    List<ImageView> imageStack;

    Vibrator vibe;
    VibrationEffect vibrationEffect;

    int cursor = 0;
    LinearLayout wordBuildLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        username = getIntent().getStringExtra("Username");
        gameKey = getIntent().getStringExtra("GameKey");
        role = getIntent().getStringExtra("Role");
        if (role.equals("host")) {
            op_role = "guest";
        } else {
            op_role = "host";
        }
        db = FirebaseDatabase.getInstance();
        ready = true;

        // Randomly generate 4 vowels and 7 consonants for the "letter bank"
        generateRandomLetters();
        initializeLookups();

        // Define image views for the "letter bank"
        LetterBank1 = findViewById(R.id.letter_bank_1);
        LetterBank2 = findViewById(R.id.letter_bank_2);
        LetterBank3 = findViewById(R.id.letter_bank_3);
        LetterBank4 = findViewById(R.id.letter_bank_4);
        LetterBank5 = findViewById(R.id.letter_bank_5);
        LetterBank6 = findViewById(R.id.letter_bank_6);
        LetterBank7 = findViewById(R.id.letter_bank_7);
        LetterBank8 = findViewById(R.id.letter_bank_8);
        LetterBank9 = findViewById(R.id.letter_bank_9);
        LetterBank10 = findViewById(R.id.letter_bank_10);
        LetterBank11 = findViewById(R.id.letter_bank_11);

        imageViews = new ArrayList<>();
        imageStack = new ArrayList<>();

        imageViews.add(LetterBank1);
        imageViews.add(LetterBank2);
        imageViews.add(LetterBank3);
        imageViews.add(LetterBank4);
        imageViews.add(LetterBank5);
        imageViews.add(LetterBank6);
        imageViews.add(LetterBank7);
        imageViews.add(LetterBank8);
        imageViews.add(LetterBank9);
        imageViews.add(LetterBank10);
        imageViews.add(LetterBank11);

        // Set ImageView resources for letter bank
        drawLetterBank();

        wordBuildLayout = findViewById(R.id.string_container);

        // Vibrator object to play on letter click
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            vibrationEffect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK);
        }

        for (ImageView v : imageViews) {
            setLetterClickListeners(v);
        }

        db.getReference("Games/" + gameKey).child(op_role + "_ready").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean op_ready = snapshot.getValue(boolean.class);
                if (op_ready && ready) {
                    Toast.makeText(GamePlayActivity.this, "Everyone is ready!",
                            Toast.LENGTH_LONG).show();
                    db.getReference("Games/" + gameKey).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void submitWord(View view) {
        word = getString(view);
        List<Integer> vowels = new ArrayList<>();
        List<Integer> consonants = new ArrayList<>();
        for (int i : vowel_positions) {
            vowels.add(i);
        }
        for (int j : consonant_positions) {
            consonants.add(j);
        }
        if (!word.equals("")) {
            ready = true;
            db.getReference("Games/" + gameKey).runTransaction(new Transaction.Handler() {
                @NonNull
                @Override
                public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                    WCGameModel g = currentData.getValue(WCGameModel.class);
                    UserDataModel data;
                    if (role.equals("host")) {
                        data = g.getHost_data();
                        data.setWord(word);
                        data.setVowel_positions(vowels);
                        data.setConsonant_positions(consonants);
                        g.setHost_data(data);
                        g.setHost_ready(ready);
                    } else {
                        data = g.getGuest_data();
                        data.setWord(word);
                        data.setVowel_positions(vowels);
                        data.setConsonant_positions(consonants);
                        g.setGuest_data(data);
                        g.setGuest_ready(ready);
                    }
                    currentData.setValue(g);
                    return Transaction.success(currentData);
                }

                @Override
                public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                }
            });
        }
    }

    public void setLetterClickListeners(ImageView v) {
        int position = imageViews.indexOf(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.setVisibility(View.INVISIBLE);
                v.setClickable(false);
                vibe.vibrate(vibrationEffect);
                TextView t = new TextView(GamePlayActivity.this);
                String letter;
                if (position < 4) {
                    letter = vowelLookup.get(vowel_positions[position]);
                } else {
                    letter = consonantLookup.get(consonant_positions[position - 4]);
                }
                t.setText(letter);
                t.setTextSize(32);
                wordBuildLayout.addView(t);
                imageStack.add(v);
            }
        });
    }

    public String getString(View view) {
        String word = "";
        if (wordBuildLayout != null) {
            Integer numLetters = wordBuildLayout.getChildCount();
            if (numLetters > 0) {
                for (int i = 0; i < numLetters; i++) {
                    TextView t = (TextView) wordBuildLayout.getChildAt(i);
                    if (t != null) {
                        String l = (String)t.getText();
                        if (l != null) {
                            word = word + l;
                        }
                    }
                }
            }
        }
        return word;
    }

    public void removeLetter(View view) {
        int stackSize = imageStack.size();
        if (stackSize > 0) {
            ImageView v = imageStack.get(stackSize - 1);
            imageStack.remove(stackSize - 1);
            v.setVisibility(View.VISIBLE);
            v.setClickable(true);
            int count = wordBuildLayout.getChildCount() - 1;
            wordBuildLayout.removeViewAt(count);
        }
    }

    public void generateRandomLetters() {
        for (int i = 0; i < 4; i++) {
            int randV = (int)(Math.random() * 6);
            vowel_positions[i] = randV;
        }
        for (int j = 0; j < 7; j++) {
            int randC = (int)(Math.random() * 20);
            consonant_positions[j] = randC;
            System.out.println(randC);
        }
    }

    public void drawLetterBank() {
        List<Integer> vowelList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            String v = vowelLookup.get(vowel_positions[i]);
            Integer res = imageLookup.get(v);
            vowelList.add(res);
        }
        List<Integer> consList = new ArrayList<>();
        for (int j = 0; j < 7; j++) {
            String c = consonantLookup.get(consonant_positions[j]);
            Integer res = imageLookup.get(c);
            consList.add(res);
        }

        LetterBank1.setImageResource(vowelList.get(0));
        LetterBank2.setImageResource(vowelList.get(1));
        LetterBank3.setImageResource(vowelList.get(2));
        LetterBank4.setImageResource(vowelList.get(3));

        LetterBank5.setImageResource(consList.get(0));
        LetterBank6.setImageResource(consList.get(1));
        LetterBank7.setImageResource(consList.get(2));
        LetterBank8.setImageResource(consList.get(3));
        LetterBank9.setImageResource(consList.get(4));
        LetterBank10.setImageResource(consList.get(5));
        LetterBank11.setImageResource(consList.get(6));
    }

    public void initializeLookups() {
        vowelLookup.put(0, "A");
        vowelLookup.put(1, "E");
        vowelLookup.put(2, "I");
        vowelLookup.put(3, "O");
        vowelLookup.put(4, "U");
        vowelLookup.put(5, "Y");

        consonantLookup.put(0, "B");
        consonantLookup.put(1, "C");
        consonantLookup.put(2, "D");
        consonantLookup.put(3, "F");
        consonantLookup.put(4, "G");
        consonantLookup.put(5, "H");
        consonantLookup.put(6, "J");
        consonantLookup.put(7, "K");
        consonantLookup.put(8, "L");
        consonantLookup.put(9, "M");
        consonantLookup.put(10, "N");
        consonantLookup.put(11, "P");
        consonantLookup.put(12, "Q");
        consonantLookup.put(13, "R");
        consonantLookup.put(14, "S");
        consonantLookup.put(15, "T");
        consonantLookup.put(16, "V");
        consonantLookup.put(17, "W");
        consonantLookup.put(18, "X");
        consonantLookup.put(19, "Z");

        imageLookup.put("A", R.drawable.a);
        imageLookup.put("B", R.drawable.b);
        imageLookup.put("C", R.drawable.c);
        imageLookup.put("D", R.drawable.d);
        imageLookup.put("E", R.drawable.e);
        imageLookup.put("F", R.drawable.f);
        imageLookup.put("G", R.drawable.g);
        imageLookup.put("H", R.drawable.h);
        imageLookup.put("I", R.drawable.i);
        imageLookup.put("J", R.drawable.j);
        imageLookup.put("K", R.drawable.k);
        imageLookup.put("L", R.drawable.l);
        imageLookup.put("M", R.drawable.m);
        imageLookup.put("N", R.drawable.n);
        imageLookup.put("O", R.drawable.o);
        imageLookup.put("P", R.drawable.p);
        imageLookup.put("Q", R.drawable.q);
        imageLookup.put("R", R.drawable.r);
        imageLookup.put("S", R.drawable.s);
        imageLookup.put("T", R.drawable.t);
        imageLookup.put("U", R.drawable.u);
        imageLookup.put("V", R.drawable.v);
        imageLookup.put("W", R.drawable.w);
        imageLookup.put("X", R.drawable.x);
        imageLookup.put("Y", R.drawable.y);
        imageLookup.put("Z", R.drawable.z);
    }
}