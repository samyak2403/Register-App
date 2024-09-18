/*
 * Created by Samyak Kamble on 9/18/24, 11:47 PM
 *  Copyright (c) 2024 . All rights reserved.
 *  Last modified 9/18/24, 10:02 PM
 */

package com.samyak2403.registerapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    // Firebase Auth instance
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth instance
        auth = FirebaseAuth.getInstance();

        // Set insets for edge-to-edge UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the logout button
        Button logoutBtn = findViewById(R.id.logoutBtn);

        // Set click listener for logout button
        logoutBtn.setOnClickListener(v -> logoutUser());
    }

    // Method to log out the user
    private void logoutUser() {
        auth.signOut(); // Log out from Firebase Authentication
        // Redirect to login screen after logout
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear activity stack
        startActivity(intent);
        finish(); // Close the main activity
    }
}
