/*
 * Created by Samyak Kamble on 9/18/24, 10:52 PM
 *  Copyright (c) 2024 . All rights reserved.
 *  Last modified 9/18/24, 10:52 PM
 */

package com.samyak2403.registerapp;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    // UI elements
    private Button forgetBtn;
    private EditText txtEmail;

    // Firebase Auth instance
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Link UI elements with the layout
        txtEmail = findViewById(R.id.forEmail);
        forgetBtn = findViewById(R.id.forgetBtn);

        // Set click listener for the forget password button
        forgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUserData(); // Validate user input when button is clicked
            }
        });
    }

    // Validate user data
    private void validateUserData() {
        String email = txtEmail.getText().toString().trim(); // Get the email input and trim any whitespace

        // Check if the email field is empty
        if (email.isEmpty()) {
            txtEmail.setError("Email is required");
        } else {
            forgetPass(email); // If valid, proceed with the password reset process
        }
    }

    // Send a password reset email
    private void forgetPass(String email) {
        // Use Firebase Auth to send a password reset email
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // If the email was sent successfully
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgetPasswordActivity.this, "Check your Email", Toast.LENGTH_SHORT).show();
                            // Redirect to the login screen after success
                            startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            // Display error message if something went wrong
                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(ForgetPasswordActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
