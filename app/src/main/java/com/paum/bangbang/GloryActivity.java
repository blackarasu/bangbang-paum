package com.paum.bangbang;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class GloryActivity extends AppCompatActivity {
    private DbScores dbScores;
    private TextToSpeech textToSpeech;
    private boolean isTTSInitialized = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glory);
        View view = findViewById(android.R.id.content);
        view.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                //@SuppressLint("ClickableViewAccessibility") Intent intent = new Intent(GloryActivity.this, MainActivity.class);
                //startActivity(intent);
                finish();
            }
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbScores = new DbScores(getApplicationContext());
        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                Locale locale = new Locale("pl", "PL");
                int ttsLang = textToSpeech.setLanguage(locale);
                if (ttsLang == TextToSpeech.LANG_MISSING_DATA || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "The language is not supported.");
                }
            }
            String text = dbScores.getNBestPlayers(3);
            if (text.isEmpty()) {
                text = getString(R.string.noScoreToText);
            }
            int speechStatus = textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            if (speechStatus == TextToSpeech.ERROR) {
                Log.e("TTS", "Error in converting Text to Speech.");
            }

            isTTSInitialized = true;
        });
    }

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.shutdown();
        }
        if (dbScores != null) {
            try {
                //noinspection FinalizeCalledExplicitly
                dbScores.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (textToSpeech != null) {
            textToSpeech.shutdown();
        }
        if (dbScores != null) {
            try {
                //noinspection FinalizeCalledExplicitly
                dbScores.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}