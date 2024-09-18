/*
 * Created by Samyak Kamble on 9/18/24, 10:57 PM
 *  Copyright (c) 2024 . All rights reserved.
 *  Last modified 9/18/24, 10:57 PM
 */

package com.samyak2403.registerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    // UI components
    private TextView openReg, openForgetPass;
    private EditText logEmail, logPassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Linking UI components
        openReg = findViewById(R.id.openReg); // Link to open registration screen
        logEmail = findViewById(R.id.logEmail); // Input field for email
        logPassword = findViewById(R.id.logPass); // Input field for password
        Button loginBtn = findViewById(R.id.loginBtn); // Login button
        openForgetPass = findViewById(R.id.openForgetPass); // Link to open forget password screen

        // Initialize Firebase Auth instance
        auth = FirebaseAuth.getInstance();

        // Set click listener to open registration activity
        openReg.setOnClickListener(v -> openRegisterActivity());

        // Set click listener for login button to validate user data
        loginBtn.setOnClickListener(v -> validateUserData());

        // Set click listener to open forget password activity
        openForgetPass.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class)));

        // Check if user is already logged in
        if (auth.getCurrentUser() != null) {
            openMainActivity(); // If user is logged in, open the main activity
        }
    }

    // Method to validate email and password input fields
    private void validateUserData() {
        String email = logEmail.getText().toString().trim(); // Get user email input
        String password = logPassword.getText().toString().trim(); // Get user password input

        // Check if email or password fields are empty
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please provide all fields", Toast.LENGTH_SHORT).show();
        } else {
            // If fields are valid, attempt to login the user
            loginUser(email, password);
        }
    }

    // Method to authenticate and log in the user
    private void loginUser(String email, String password) {
        // Use Firebase Authentication to log in the user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            if (user.isEmailVerified()) {
                                // If email is verified, open the main activity
                                openMainActivity();
                            } else {
                                // If email is not verified, show a message and send a verification email
                                Toast.makeText(LoginActivity.this, "Email not verified. Please verify your email.", Toast.LENGTH_SHORT).show();
                                sendVerificationEmail(user);
                            }
                        }
                    } else {
                        // If login fails, show an error message
                        Toast.makeText(LoginActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // Show an error message if login fails
                    Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Method to send a verification email
    private void sendVerificationEmail(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Verification email sent. Please check your inbox.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Method to open the main activity
    private void openMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish(); // Close the login activity once main activity is opened
    }

    // Method to open the registration activity
    private void openRegisterActivity() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        finish(); // Close the login activity once registration activity is opened
    }
}
