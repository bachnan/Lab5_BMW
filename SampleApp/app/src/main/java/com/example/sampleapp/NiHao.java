package com.example.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NiHao extends AppCompatActivity {
    private TextView sappContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sample_app);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setContentView(R.layout.sample_app);
        sappContent = findViewById(R.id.Sapp_content);
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        if (username != null && !username.isEmpty()) {
            sappContent.setText("Ni Hao  " + username);
        } else {
            sappContent.setText("Ni Hao user");
        }
    }
}