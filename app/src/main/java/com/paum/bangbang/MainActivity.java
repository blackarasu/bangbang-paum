package com.paum.bangbang;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private boolean isTTSInitialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = findViewById(android.R.id.content);
        view.setOnTouchListener(new OnGestureListener(MainActivity.this) {
            public void onSwipeLeft() {
                Intent intent = new Intent(MainActivity.this, GloryActivity.class);
                startActivity(intent);
            }

            public void onSwipeRight() {
                Intent intent = new Intent(MainActivity.this, GloryActivity.class);
                startActivity(intent);
            }

            public void onSwipeTop() {
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
            }

            public void onSwipeBottom() {
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
            }

            public void onSingleTap() {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }

            public void onZoomOut() {
                finish();
            }
        });
    }

    @Override
    protected void onResume () {
        super.onResume();

        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                Locale locale = new Locale("pl", "PL");
                int ttsLang = textToSpeech.setLanguage(locale);
                if (ttsLang == TextToSpeech.LANG_MISSING_DATA || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "The language is not supported.");
                }
            }

            String text = getString(R.string.menuTextToSpeech);
            int speechStatus = textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);

            if (speechStatus == TextToSpeech.ERROR) {
                Log.e("TTS", "Error in converting Text to Speech.");
            }

            isTTSInitialized = true;
        });
    }

    @Override
    protected void onDestroy (){
        super.onDestroy();
        if(textToSpeech != null){
            textToSpeech.shutdown();
        }
    }

    @Override
    protected void onPause (){
        super.onPause();
        if(textToSpeech != null){
            textToSpeech.shutdown();
        }
    }
}