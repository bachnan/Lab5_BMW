package com.example.sampleapp;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class User {
    private int id;
    private String name;
    private String email;
    private String salt;
    private String passwordHash;
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getSalt() { return salt; }
    public void setSalt(String salt) { this.salt = salt; }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setPassword(String password) throws NoSuchAlgorithmException {
        this.salt = generateSalt();
        this.passwordHash = hashPassword(password, salt);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean checkPassword(String password) throws NoSuchAlgorithmException {
        String hash = hashPassword(password, salt);
        return hash.equals(passwordHash);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes());
        byte[] hashedBytes = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] saltBytes = new byte[16];
        sr.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }
}
