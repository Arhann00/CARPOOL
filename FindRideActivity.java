package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FindRideActivity extends AppCompatActivity {

    // UI components
    private EditText searchDestinationEditText;
    private ListView rideListView;
    private ArrayList<String> rides;
    private ArrayAdapter<String> adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ride);

        // Initialize the DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Link UI components to their XML layout elements
        searchDestinationEditText = findViewById(R.id.search_destination);
        rideListView = findViewById(R.id.ride_list);
        Button searchButton = findViewById(R.id.search_button);

        // Initialize the ArrayList to hold ride details
        rides = new ArrayList<>();

        // Initialize the ArrayAdapter with a custom layout for the list items
        adapter = new ArrayAdapter<>(this, R.layout.ride_list_item, R.id.driver_name, rides);
        rideListView.setAdapter(adapter);

        // Set up the click listener for the search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchRides();
            }
        });

        // Set up the item click listener for the ListView
        rideListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                requestRide(position);
            }
        });
    }

    // Method to search for rides by destination
    private void searchRides() {
        // Get the destination entered by the user
        String destination = searchDestinationEditText.getText().toString().trim();

        // Check if the destination field is empty
        if (destination.isEmpty()) {
            Toast.makeText(FindRideActivity.this, "Please enter a destination to search", Toast.LENGTH_SHORT).show();
            return;
        }

        // Query the database for rides with the specified destination
        Cursor cursor = dbHelper.getRidesByDestination(destination);
        rides.clear(); // Clear the current list of rides

        // Check if the cursor has any results
        if (cursor.moveToFirst()) {
            do {
                // Create a string with the ride details
                String ride = "Ride from " + cursor.getString(cursor.getColumnIndex("start_point")) +
                        " to " + cursor.getString(cursor.getColumnIndex("destination")) +
                        " with " + cursor.getInt(cursor.getColumnIndex("number_of_seats")) + " seats available";
                // Add the ride details to the list
                rides.add(ride);
            } while (cursor.moveToNext());
        } else {
            // Show a message if no rides are found
            Toast.makeText(FindRideActivity.this, "No rides found for this destination", Toast.LENGTH_SHORT).show();
        }

        cursor.close(); // Close the cursor to free up resources
        adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

   /* // Method to handle ride request
    private void requestRide(int position) {
        // Get the details of the selected ride
        String rideDetails = rides.get(position);

        // Insert the ride request into the database
        if (dbHelper.insertRideRequest(rideDetails)) {
            Toast.makeText(FindRideActivity.this, "Request sent: " + rideDetails, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(FindRideActivity.this, "Failed to send request", Toast.LENGTH_SHORT).show();
        }
*/
        private void requestRide(int position) {
            String rideDetails = rides.get(position);
            Intent intent = new Intent(FindRideActivity.this, RideDetailsActivity.class);
            intent.putExtra("ride_details", rideDetails);
            startActivity(intent);
        }

    }


