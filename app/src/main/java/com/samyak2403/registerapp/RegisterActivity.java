/*
 * Created by Samyak Kamble on 9/18/24, 10:45 PM
 *  Copyright (c) 2024 . All rights reserved.
 *  Last modified 9/18/24, 10:22 PM
 */

package com.samyak2403.registerapp;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;

import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    // UI elements
    private EditText regName, regEmail, regPassword;
    private Button register;
    private TextView openLog;

    // Variables for storing user inputs
    private String name, email, pass;

    // Firebase references
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth and Database reference
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        // Link UI elements with the layout
        regName = findViewById(R.id.regName);
        regEmail = findViewById(R.id.regEmail);
        regPassword = findViewById(R.id.regPass);
        register = findViewById(R.id.register);
        openLog = findViewById(R.id.openLog);

        // Register button click listener
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData(); // Validate user input before registering
            }
        });

        // Open Login activity on button click
        openLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });
    }

    // Open the login activity
    private void openLogin() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if the user is already logged in
        if (auth.getCurrentUser() != null) {
            openMainActivity(); // Open main activity if the user is logged in
        }
    }

    // Open the main activity
    private void openMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    // Validate user input data
    private void validateData() {
        name = regName.getText().toString();
        email = regEmail.getText().toString();
        pass = regPassword.getText().toString();

        // Check if name field is empty
        if (name.isEmpty()) {
            regName.setError("Required Name");
            regName.requestFocus();
        }
        // Check if email field is empty
        else if (email.isEmpty()) {
            regEmail.setError("Required Email");
            regEmail.requestFocus();
        }
        // Check if password field is empty
        else if (pass.isEmpty()) {
            regPassword.setError("Required Password");
        }
        // If everything is filled, proceed to create the user
        else {
            createUser();
        }
    }

    // Create a new user with Firebase Authentication
    private void createUser() {
        // Firebase method to create user with email and password
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            uploadUserData(); // Upload user data to the Firebase database
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Upload user data to the Firebase Realtime Database
    private void uploadUserData() {
        databaseReference = reference.child("users");
        String key = databaseReference.push().getKey(); // Generate unique key for each user

        // Store user data in a HashMap
        HashMap<String, String> user = new HashMap<>();
        user.put("key", key);
        user.put("name", name);
        user.put("email", email);
        user.put("status", "no"); // Default status

        // Push user data to the Firebase Database under 'users'
        databaseReference.child(key).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "User created", Toast.LENGTH_SHORT).show();
                            openMainActivity(); // Open the main activity after successful registration
                        } else {
                            Toast.makeText(RegisterActivity.this, (CharSequence) task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
