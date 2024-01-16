package com.example.generatoreparole;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    boolean isFirstStart = true;
    CountDownTimer countDownTimer;
    boolean isTimerRunning = false;
    long timeRemaining = 0; // memorizza il tempo rimanente quando il timer è in pausa
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //controllo per capire se sia il primo avvio del timer

        TextView time = findViewById(R.id.timeTextView);

        TextView wordTextView = findViewById(R.id.parolaGenerata);

        ArrayList<String> words;
        // Carica le parole dal file di testo
        words = loadWords();

        TextView counterTextView = findViewById(R.id.counterTextView);

        AppCompatImageButton playButton = findViewById(R.id.playButton);

        // Genera una parola casuale al click
        playButton.setOnClickListener(view -> {
            // Riduci leggermente l'alpha del colore del pulsante
            playButton.getBackground().setAlpha(128); // Imposta un valore tra 0 (trasparente) e 255 (opaco)

            if(!isTimerRunning) {
                startTimer(time, playButton, timeRemaining);
                if (isFirstStart) {
                    wordTextView.setText(getRandomWord(words));
                    isFirstStart = false;
                }
                isTimerRunning = true;
            }
            // Ripristina l'alpha del colore del pulsante dopo un breve ritardo
            playButton.postDelayed(() -> {
                playButton.getBackground().setAlpha(255); // Ripristina l'opacità completa
            }, 200); // Ritardo di 200 millisecondi (puoi regolare questo valore secondo le tue preferenze)
        });

        AppCompatImageButton correctButton = findViewById(R.id.correctButton);

        correctButton.setOnClickListener(view -> {
            // Riduci leggermente l'alpha del colore del pulsante
            correctButton.getBackground().setAlpha(128); // Imposta un valore tra 0 (trasparente) e 255 (opaco)

            if (isTimerRunning) {
                // Se il timer è in esecuzione, pausa il timer
                countDownTimer.cancel();
                isTimerRunning = false;
                //playButton.setImageResource(R.drawable.correct_icon); cambiamento icona quando è in pausa
                playButton.setEnabled(true);
                wordTextView.setText(getRandomWord(words));
                if (counter >= 0) {
                    counter++;
                    getCounter(counterTextView, counter);
                }
            }
            // Ripristina l'alpha del colore del pulsante dopo un breve ritardo
            correctButton.postDelayed(() -> {
                correctButton.getBackground().setAlpha(255); // Ripristina l'opacità completa
            }, 200); // Ritardo di 200 millisecondi (puoi regolare questo valore secondo le tue preferenze)
        });

        AppCompatImageButton errorButton = findViewById(R.id.errorButton);

        errorButton.setOnClickListener(view -> {
            // Riduci leggermente l'alpha del colore del pulsante
            errorButton.getBackground().setAlpha(128); // Imposta un valore tra 0 (trasparente) e 255 (opaco)

            if (isTimerRunning) {
                // Se il timer è in esecuzione, pausa il timer
                countDownTimer.cancel();
                isTimerRunning = false;
                playButton.setEnabled(true);
                wordTextView.setText(getRandomWord(words));
                if (counter > 0) {
                    counter--;
                    getCounter(counterTextView, counter);
                }
            }
            // Ripristina l'alpha del colore del pulsante dopo un breve ritardo
            errorButton.postDelayed(() -> {
                errorButton.getBackground().setAlpha(255); // Ripristina l'opacità completa
            }, 200); // Ritardo di 200 millisecondi (puoi regolare questo valore secondo le tue preferenze)
        });

        AppCompatImageButton skipButton = findViewById(R.id.skipButton);

        skipButton.setOnClickListener(view -> {
            // Riduci leggermente l'alpha del colore del pulsante
            skipButton.getBackground().setAlpha(128); // Imposta un valore tra 0 (trasparente) e 255 (opaco)

            // Aggiungi qui la logica per l'azione del pulsante
            // Se il timer è in esecuzione, pausa il timer
            countDownTimer.cancel();
            isTimerRunning = false;
            playButton.setEnabled(true);
            wordTextView.setText(getRandomWord(words));
            // Ripristina l'alpha del colore del pulsante dopo un breve ritardo
            skipButton.postDelayed(() -> {
                skipButton.getBackground().setAlpha(255); // Ripristina l'opacità completa
            }, 200); // Ritardo di 200 millisecondi (puoi regolare questo valore secondo le tue preferenze)
        });

    }
    private ArrayList<String> loadWords() {
        ArrayList<String> words = new ArrayList<>();
        try {
            InputStream inputStream = getAssets().open("1000_parole_italiane_comuni.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return words;
    }

    private String getRandomWord(ArrayList<String> words) {
        Random random = new Random();
        int randomIndex = random.nextInt(words.size());
        String randomWord = words.get(randomIndex);
        while (randomWord.length() < 3) {
            // Se la parola è troppo corta, genera una nuova parola
            randomWord = getRandomWord(words);
        }
        return randomWord;
    }

    private void startTimer(TextView timeEditText, AppCompatImageButton startButton, long initialTime) {
        String timeString = timeEditText.getText().toString();
        if (!timeString.isEmpty()) {

            long timeInMillis = initialTime > 0 ? initialTime-1000 : 60000;

            startButton.setEnabled(false);
             countDownTimer = new CountDownTimer(timeInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long secondsRemaining = millisUntilFinished / 1000;
                    long minutes = secondsRemaining / 60;
                    long seconds = secondsRemaining % 60;
                    timeRemaining = millisUntilFinished;
                    // Formatta il tempo rimanente in minuti e secondi
                    String formattedTime = String.format("%02d:%02d", minutes, seconds);
                    timeEditText.setText(formattedTime);
                    Log.d("Timer", "Tempo rimanente: " + formattedTime);
                }

                @Override
                public void onFinish() {
                    timeEditText.setText("01:00");
                    startButton.setEnabled(true);
                    isTimerRunning = false;
                    timeRemaining = 0;
                }
            };

            countDownTimer.start();
            isTimerRunning = true;
        }
    }


    public void getCounter(TextView counterTextView, int counter) {
        if (!counterTextView.getText().toString().isEmpty()) {
            counterTextView.setText("Punteggio: " + counter);
        }
    }


}