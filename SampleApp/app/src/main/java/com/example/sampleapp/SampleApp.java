package com.example.sampleapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SampleApp extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_app);

        TextView welcomeTextView = findViewById(R.id.textView);
        String username = getIntent().getStringExtra("username");

        if (username != null) {
            welcomeTextView.setText("HELLO " + username + ", WELCOME TO SAMPLE APPLICATION");
        }
    }
}
