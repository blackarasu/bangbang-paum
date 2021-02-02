package com.paum.bangbang;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class HelpActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                Locale locale = new Locale("pl", "PL");
                int ttsLang = textToSpeech.setLanguage(locale);
                if (ttsLang == TextToSpeech.LANG_MISSING_DATA || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "The language is not supported.");
                }
            }
            String text = getString(R.string.helpTextToSpeech);
            int speechStatus = textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            if (speechStatus == TextToSpeech.ERROR) {
                Log.e("TTS", "Error in converting Text to Speech.");
            }
        });

        View view = findViewById(android.R.id.content);
        view.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                //Intent intent = new Intent(HelpActivity.this, MainActivity.class);
                //startActivity(intent);
                if(this.textToSpeech != null)
                    this.textToSpeech.shutdown();
                finish();
            }
            return true;
        });
    }
}