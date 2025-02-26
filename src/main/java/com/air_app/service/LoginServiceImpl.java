package com.air_app.service;

import com.air_app.Configuration.DBConfiguration;
import com.air_app.payLoad.LoginResponse;
import com.air_app.security.PasswordEncrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginServiceImpl implements LoginService {
    private final Connection con = DBConfiguration.getConnection();

    @Override
    public LoginResponse login(Scanner sc) {
        LoginResponse loginResponse = new LoginResponse();

        if (con == null) {
            System.out.println("Database connection failed!");
            loginResponse.setUsername(null);
            loginResponse.setLoggedIn(false);
            return loginResponse;
        }

        System.out.println("Enter the username:");
        String username = sc.next();

        System.out.println("Enter your password:");
        String enteredPassword = sc.next();

        String storedPassword = null;

        // Using try-with-resources to prevent memory leaks
        try (PreparedStatement ps = con.prepareStatement("SELECT password FROM user WHERE username = ?")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    storedPassword = rs.getString("password");
                } else {
                    System.out.println("User not found. Please register first.");
                    loginResponse.setUsername(null);
                    loginResponse.setLoggedIn(false);
                    return loginResponse;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user data: " + e.getMessage());
            e.printStackTrace();
            loginResponse.setUsername(null);
            loginResponse.setLoggedIn(false);
            return loginResponse;
        }

        // Password verification
        if (storedPassword != null) {
            try {
                String decryptedPassword = PasswordEncrypt.decrypt(storedPassword);
                if (decryptedPassword == null) {
                    System.out.println("Decryption failed! SecretKey might be missing.");
                    loginResponse.setUsername(null);
                    loginResponse.setLoggedIn(false);
                    return loginResponse;
                }

                if (enteredPassword.equals(decryptedPassword)) {
                    loginResponse.setUsername(username);
                    loginResponse.setLoggedIn(true);
                    System.out.println("Login successful!");
                } else {
                    System.out.println("Incorrect password.");
                    loginResponse.setUsername(null);
                    loginResponse.setLoggedIn(false);
                }
            } catch (Exception e) {
                System.err.println("Error decrypting password: " + e.getMessage());
                e.printStackTrace();
                loginResponse.setUsername(null);
                loginResponse.setLoggedIn(false);
            }
        } else {
            System.out.println("Error: Retrieved password is null.");
            loginResponse.setUsername(null);
            loginResponse.setLoggedIn(false);
        }

        return loginResponse;
    }
}
