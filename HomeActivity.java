package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    // Declare the buttons
    private Button offerRideButton;
    private Button findRideButton;
    private Button profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize the buttons by linking them to their corresponding XML elements
        offerRideButton = findViewById(R.id.offer_ride);
        findRideButton = findViewById(R.id.find_ride);
        profileButton = findViewById(R.id.profile);

        // Set a click listener for the Offer Ride button
        offerRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the OfferRideActivity when the button is clicked
                startActivity(new Intent(HomeActivity.this, OfferRideActivity.class));
            }
        });

        // Set a click listener for the Find Ride button
        findRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the FindRideActivity when the button is clicked
                startActivity(new Intent(HomeActivity.this, FindRideActivity.class));
            }
        });

        // Set a click listener for the Profile button
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the ProfileManagementActivity when the button is clicked
                startActivity(new Intent(HomeActivity.this, ProfileManagementActivity.class));
            }
        });
    }
}
