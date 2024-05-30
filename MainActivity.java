package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

import com.example.myapplication.HomeActivity;
import com.example.myapplication.OfferRideActivity;
import com.example.myapplication.FindRideActivity;
import com.example.myapplication.ProfileManagementActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // Declare UI elements and navigation components
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Button offerRideButton;
    private Button findRideButton;
    private Button profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);

        // Initialize buttons
        offerRideButton = findViewById(R.id.offer_ride);
        findRideButton = findViewById(R.id.find_ride);
        profileButton = findViewById(R.id.profile);

        // Set click listeners for buttons to navigate to respective activities
        offerRideButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, OfferRideActivity.class)));
        findRideButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, FindRideActivity.class)));
        profileButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProfileManagementActivity.class)));

        // Initialize the NavigationView and set item selected listener
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            // Handle navigation item clicks
            if (id == R.id.nav_home) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            } else if (id == R.id.nav_offer_ride) {
                startActivity(new Intent(MainActivity.this, OfferRideActivity.class));
            } else if (id == R.id.nav_find_ride) {
                startActivity(new Intent(MainActivity.this, FindRideActivity.class));
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(MainActivity.this, ProfileManagementActivity.class));
            } else if (id == R.id.nav_logout) {
                // Handle logout logic
            } else {
                return false;
            }

            drawerLayout.closeDrawers(); // Close the drawer after item is selected
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks
        if (toggle != null && toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
