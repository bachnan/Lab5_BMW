package com.example.sampleapp;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String passwordHash;
    private String salt;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    private void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    private String getPasswordHash() { return passwordHash; }
    private String getSalt() { return salt; }
    private void setSalt(String salt) { this.salt = salt; }

    public void setPassword(String password) throws NoSuchAlgorithmException {
        this.salt = generateSalt();
        this.passwordHash = hashPassword(password, salt); 
    }

    public boolean checkPassword(String password) throws NoSuchAlgorithmException {
        String hash = hashPassword(password, salt);
        return hash.equals(passwordHash);
    }

    private String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes());
        byte[] hashedBytes = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }

    private String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] saltBytes = new byte[16];
        sr.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    public String getPassword() {
        return password;
    }
}
