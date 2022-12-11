package com.example.team54;

import android.content.Context;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
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
    boolean op_ready;
    String role;
    String op_role;
    String opponent_word;
    TextView wait_label;
    TextView letterBankLabel;
    TextView user_label;
    int balance = 500;
    ImageView winGraphic;

    Button submit;
    Button buyFirstLetterB;
    Button buyLastLetterB;
    Button buyWordLengthB;
    Button buyVowelCountB;
    Button buyConsCountB;
    List<Button> hintButtonList;

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

    ProgressBar loadingAnimation;

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
        ready = false;

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

        loadingAnimation = findViewById(R.id.progressBar);
        wait_label = findViewById(R.id.waiting_label);
        wait_label.setVisibility(View.INVISIBLE);
        loadingAnimation.setVisibility(View.INVISIBLE);
        letterBankLabel = findViewById(R.id.letter_bank_label);
        submit = findViewById(R.id.button3);
        user_label = findViewById(R.id.user_label);
        user_label.setText(username + ": " + balance);
        winGraphic = findViewById(R.id.win_graphic);
        winGraphic.setVisibility(View.INVISIBLE);

        buyFirstLetterB = findViewById(R.id.first_letter_button);
        buyLastLetterB = findViewById(R.id.last_letter_button);
        buyWordLengthB = findViewById(R.id.word_length_button);
        buyVowelCountB = findViewById(R.id.vowel_count_button);
        buyConsCountB = findViewById(R.id.cons_count_button);
        hintButtonList = new ArrayList<>();
        hintButtonList.add(buyFirstLetterB);
        hintButtonList.add(buyLastLetterB);
        hintButtonList.add(buyWordLengthB);
        hintButtonList.add(buyVowelCountB);
        hintButtonList.add(buyConsCountB);
        toggleButtonVisibility(hintButtonList, false, View.INVISIBLE);
        winGraphic.bringToFront();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ready && op_ready) {
                    guessWord(view);
                } else {
                    submitWord(view);
                }
             }
        });

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
                op_ready = snapshot.getValue(boolean.class);
                if (op_ready && ready) {
                    toggleButtonVisibility(hintButtonList, true, View.VISIBLE);
                    submit.setText("Guess");
                    wait_label.setVisibility(View.INVISIBLE);
                    loadingAnimation.setVisibility(View.INVISIBLE);
                    Toast.makeText(GamePlayActivity.this, "Everyone is ready!",
                            Toast.LENGTH_LONG).show();
                    db.getReference("Games/" + gameKey).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            gameData = task.getResult().getValue(WCGameModel.class);
                            if (role.equals("host")) {
                                opponent_word = gameData.getGuest_data().getWord();
                            } else {
                                opponent_word = gameData.getHost_data().getWord();
                            }
                            renderOpponentData();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        db.getReference("Games/" + gameKey).child(op_role + "_data").child("won").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean w = snapshot.getValue(boolean.class);
                System.out.println("Op role = " + op_role);
                System.out.println("Opponent win" + w);
                if (w) {
                    Toast.makeText(GamePlayActivity.this, "Sorry, you lost.", Toast.LENGTH_LONG).show();
                    try {
                        Thread.sleep(3500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void guessWord(View view) {
        word = getString(view);
        String op_word;
        if (role.equals("host")) {
            op_word = gameData.getGuest_data().getWord();
        } else {
            op_word = gameData.getHost_data().getWord();
        }
        if (word.equals(op_word)) {
            toggleButtonVisibility(hintButtonList, false, View.INVISIBLE);
            winGraphic.setVisibility(View.VISIBLE);
            db.getReference("Games/" + gameKey).child(role + "_data").child("won").setValue(true);
        } else {
            Toast.makeText(GamePlayActivity.this, "Incorrect. Keep trying", Toast.LENGTH_LONG).show();
        }
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
                        op_ready = g.getGuest_ready();
                    } else {
                        data = g.getGuest_data();
                        data.setWord(word);
                        data.setVowel_positions(vowels);
                        data.setConsonant_positions(consonants);
                        g.setGuest_data(data);
                        g.setGuest_ready(ready);
                        op_ready = g.getHost_ready();
                    }
                    currentData.setValue(g);
                    return Transaction.success(currentData);
                }

                @Override
                public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                    if (op_ready) {
                        toggleButtonVisibility(hintButtonList, true, View.VISIBLE);
                        wait_label.setVisibility(View.INVISIBLE);
                        loadingAnimation.setVisibility(View.INVISIBLE);
                        Toast.makeText(GamePlayActivity.this, "Everyone is ready", Toast.LENGTH_LONG).show();
                        gameData = currentData.getValue(WCGameModel.class);
                        if (role.equals("host")) {
                            opponent_word = gameData.getGuest_data().getWord();
                        } else {
                            opponent_word = gameData.getHost_data().getWord();
                        }
                        renderOpponentData();
                    } else {
                        if (ready) {
                            wait_label.setVisibility(View.VISIBLE);
                            loadingAnimation.setVisibility(View.VISIBLE);
                        }
                    }
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
            int randV = (int)(Math.random() * 28);
            vowel_positions[i] = randV;
        }
        for (int j = 0; j < 7; j++) {
            int randC = (int)(Math.random() * 53);
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

        for (ImageView l : imageViews) {
            l.setClickable(true);
            l.setVisibility(View.VISIBLE);
        }
    }

    public void renderOpponentData() {
        List<Integer> op_vowels;
        List<Integer> op_consonants;
        if (role.equals("host")) {
            op_vowels = gameData.getGuest_data().vowel_positions;
            op_consonants = gameData.getGuest_data().consonant_positions;
        } else {
            op_vowels = gameData.getHost_data().vowel_positions;
            op_consonants = gameData.getHost_data().consonant_positions;
        }
        getOpponentVowels(op_vowels);
        getOpponentConsonants(op_consonants);
        wordBuildLayout.removeAllViews();
        letterBankLabel.setText("Opponent's Letters");
        drawLetterBank();
    }

    public void getOpponentVowels(List<Integer> vowels) {
        for (int i = 0; i <vowels.size(); i++) {
            vowel_positions[i] = vowels.get(i);
        }
    }

    public void getOpponentConsonants(List<Integer> consonants) {
        for (int i = 0; i < consonants.size(); i++) {
            consonant_positions[i] = consonants.get(i);
        }
    }

    public void initializeLookups() {

        for (int i = 0; i < 28; i++) {
            if (i < 5) {
                vowelLookup.put(i, "A");
            } else if (i < 13) {
                vowelLookup.put(i, "E");
            } else if (i < 18) {
                vowelLookup.put(i, "I");
            } else if (i < 23) {
                vowelLookup.put(i, "O");
            } else if (i < 26) {
                vowelLookup.put(i, "U");
            } else {
                vowelLookup.put(i, "Y");
            }
        }

        for (int j = 0; j < 53; j++) {
            if (j < 2) {
                consonantLookup.put(j, "B");
            } else if (j < 5) {
                consonantLookup.put(j, "C");
            } else if (j < 9) {
                consonantLookup.put(j, "D");
            } else if (j < 12) {
                consonantLookup.put(j, "F");
            } else if (j < 14) {
                consonantLookup.put(j, "G");
            } else if (j < 18) {
                consonantLookup.put(j, "H");
            } else if (j < 19) {
                consonantLookup.put(j, "J");
            } else if (j < 20) {
                consonantLookup.put(j, "K");
            } else if (j < 24) {
                consonantLookup.put(j, "L");
            } else if (j < 27) {
                consonantLookup.put(j, "M");
            } else if (j < 32) {
                consonantLookup.put(j, "N");
            } else if (j < 34) {
                consonantLookup.put(j, "P");
            } else if (j < 35) {
                consonantLookup.put(j, "Q");
            } else if (j < 39) {
                consonantLookup.put(j, "R");
            } else if (j < 43) {
                consonantLookup.put(j, "S");
            } else if (j < 48) {
                consonantLookup.put(j, "T");
            } else if (j < 49) {
                consonantLookup.put(j, "V");
            } else if (j < 51) {
                consonantLookup.put(j, "W");
            } else if (j < 52) {
                consonantLookup.put(j, "X");
            } else {
                consonantLookup.put(j, "Z");
            }
        }

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

    public void buyVowelCount(View view) {
        int cost = 200;
        int newBalance = balance - cost;
        int count = 0;
        Collection<String> vowels = vowelLookup.values();
        for (int i = 0; i < opponent_word.length(); i++) {
            if (vowels.contains(String.valueOf(opponent_word.charAt(i)))) {
                count++;
            }
        }
        String msg ="There are " + count + " vowels";
        if (newBalance >= 0) {
            balance = newBalance;
            updateBalance();
            buyVowelCountB.setClickable(false);
            buyVowelCountB.setVisibility(View.INVISIBLE);
            Toast.makeText(GamePlayActivity.this, msg, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(GamePlayActivity.this, "You don't have enough coins",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void buyConsonantCount(View view) {
        int cost = 200;
        int newBalance = balance - cost;
        int count = 0;
        Collection<String> cons = consonantLookup.values();
        for (int i = 0; i < opponent_word.length(); i++) {
            if (cons.contains(String.valueOf(opponent_word.charAt(i)))) {
                count++;
            }
        }
        String msg ="There are " + count + " consonants";
        if (newBalance >= 0) {
            balance = newBalance;
            updateBalance();
            buyConsCountB.setClickable(false);
            buyConsCountB.setVisibility(View.INVISIBLE);
            Toast.makeText(GamePlayActivity.this, msg, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(GamePlayActivity.this, "You don't have enough coins",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void buyWordLength(View view) {
        int cost = 200;
        int newBalance = balance - cost;
        String msg = "Word length = " + String.valueOf(opponent_word.length());
        if (newBalance >= 0) {
            balance = newBalance;
            updateBalance();
            buyWordLengthB.setClickable(false);
            buyWordLengthB.setVisibility(View.INVISIBLE);
            Toast.makeText(GamePlayActivity.this, msg, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(GamePlayActivity.this, "You don't have enough coins",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void buyFirstLetter(View view) {
        int cost = 400;
        int newBalance = balance - cost;
        String msg = "First letter = " + opponent_word.substring(0, 1);
        if (newBalance >= 0) {
            balance = newBalance;
            updateBalance();
            buyFirstLetterB.setVisibility(View.INVISIBLE);
            buyFirstLetterB.setClickable(false);
            Toast.makeText(GamePlayActivity.this, msg, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(GamePlayActivity.this, "You don't have enough coins",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void buyLastLetter(View view) {
        int cost = 100;
        int newBalance = balance - cost;
        String msg = "Last Letter = " + opponent_word.substring(opponent_word.length() - 1);
        if (newBalance >= 0) {
            balance = newBalance;
            updateBalance();
            buyLastLetterB.setVisibility(View.INVISIBLE);
            buyLastLetterB.setClickable(false);
            Toast.makeText(GamePlayActivity.this, msg, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(GamePlayActivity.this, "You don't have enough coins",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void toggleButtonVisibility(List<Button> buttons, boolean b, int vis) {
        for (Button button : buttons) {
            button.setClickable(b);
            button.setVisibility(vis);
        }
    }

    public void updateBalance() {
        user_label.setText(username + ": " + balance);
    }
}