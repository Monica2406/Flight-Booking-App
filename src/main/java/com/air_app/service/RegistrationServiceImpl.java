package com.air_app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

import com.air_app.Configuration.DBConfiguration;
import com.air_app.security.PasswordEncrypt;

public class RegistrationServiceImpl implements RegistrationService {
    @Override
    public String registration(Scanner sc) {
        System.out.println("Enter the Username :");
        String userName = sc.next();
        System.out.println("Enter the Password :");
        String password = sc.next();

        // Encrypt password using stored SecretKey
        String encryptedPassword = PasswordEncrypt.encrypt(password);
        if (encryptedPassword == null) {
            return "Error encrypting password.";
        }

        Connection con = DBConfiguration.getConnection();
        PreparedStatement ps;
        int rows = -1;
        try {
            ps = con.prepareStatement("INSERT INTO user (username, password) VALUES(?, ?)");
            ps.setString(1, userName);
            ps.setString(2, encryptedPassword);
            rows = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return (rows > 0) ? "User Registered Successfully" : "Something Went Wrong";
    }
}
