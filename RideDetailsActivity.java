package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RideDetailsActivity extends AppCompatActivity {

    // Declare UI elements
    private TextView rideDetailsTextView;
    private Button requestRideButton;
    private DatabaseHelper dbHelper;
    private String rideDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_details);

        // Initialize UI elements
        rideDetailsTextView = findViewById(R.id.ride_details);
        requestRideButton = findViewById(R.id.request_ride);

        // Initialize the database helper
        dbHelper = new DatabaseHelper(this);

        // Get ride details from the intent
        Intent intent = getIntent();
        rideDetails = intent.getStringExtra("ride_details");

        // Set ride details to the TextView
        rideDetailsTextView.setText(rideDetails);

        // Set click listener for the request ride button
        requestRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRequestRide();
            }
        });
    }

    // Method to handle ride request
    private void handleRequestRide() {
        // Insert ride request into the database
        boolean isInserted = dbHelper.insertRideRequest(rideDetails);
        if (isInserted) {
            Toast.makeText(RideDetailsActivity.this, "Ride requested successfully", Toast.LENGTH_SHORT).show();
            // Optionally, navigate to another screen
            Intent intent = new Intent(RideDetailsActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(RideDetailsActivity.this, "Failed to request ride", Toast.LENGTH_SHORT).show();
        }
    }
}
