package com.example.sampleapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        apiService API_Service = RetrofitClient.getInstance().create(apiService.class);
        Call<List<User>> call = API_Service.getUsers();

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            String inputUsername = usernameEditText.getText().toString().trim();
            String inputPassword = passwordEditText.getText().toString().trim();

            if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            call.enqueue(new Callback<List<User>>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<User> users = response.body();
                        boolean isValidUser = false;

                        for (User user : users) {
                            try {
                                if (user.getName().equals(inputUsername) && user.checkPassword(inputPassword)) {
                                    isValidUser = true;
                                    Intent intent = new Intent(MainActivity.this, SampleApp.class);
                                    intent.putExtra("username", inputUsername);
                                    startActivity(intent);
                                    finish();
                                    break;
                                }
                            } catch (NoSuchAlgorithmException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        if (!isValidUser) {
                            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to fetch users", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error connecting to server", Toast.LENGTH_SHORT).show();
                }
            });
        });

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}