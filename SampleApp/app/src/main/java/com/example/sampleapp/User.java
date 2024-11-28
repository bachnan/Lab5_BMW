package com.example.sampleapp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private int id;
    private String name;
    private String email;
    private String passwordHash;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Thay đổi phương thức getPassword để trả về passwordHash
    public String getPassword() { return passwordHash; }

    // Phương thức để đặt mật khẩu (mã hóa mật khẩu)
    public void setPassword(String password) {
        this.passwordHash = hashPassword(password);
    }

    // Phương thức mã hóa mật khẩu bằng MD5
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
