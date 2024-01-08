package com.example.generatoreparole;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView wordTextView = findViewById(R.id.parolaGenerata);

        ArrayList<String> words;
        // Carica le parole dal file di testo
        words = loadWords();

        Button buttonGenera = findViewById(R.id.buttonGenera);

        // Genera una parola casuale al click
        buttonGenera.setOnClickListener(v -> {
            String randomWord = getRandomWord(words);
            while (randomWord.length() < 3) {
                // Se la parola è troppo corta, genera una nuova parola
                randomWord = getRandomWord(words);
            }
            wordTextView.setText(randomWord);
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
        return words.get(randomIndex);
    }


}