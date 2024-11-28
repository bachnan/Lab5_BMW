package com.example.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {
    private EditText regEmail, regUsername, regPassword;
    private Button btnRegister;
    private  SQLiteConnector db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        regEmail = findViewById(R.id.mailEditText);
        regUsername = findViewById(R.id.usernameEditText);
        regPassword = findViewById(R.id.passwordEditText);
        btnRegister = findViewById(R.id.resgisterButton);

        db = new SQLiteConnector(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = regEmail.getText().toString().trim();
                String username = regUsername.getText().toString().trim();
                String password = regPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "All files are required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(db.checkUser(username)) {
                    Toast.makeText(RegisterActivity.this, "Username already exists!", Toast.LENGTH_SHORT).show();
                    return;
                }
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setName(username);
                try {
                    newUser.setPassword(password);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                db.addUser(newUser);
                Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
