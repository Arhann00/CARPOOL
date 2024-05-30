package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileManagementActivity extends AppCompatActivity {

    // Declare UI elements and variables
    private EditText nameEditText;
    private EditText contactDetailsEditText;
    private EditText carMakeEditText;
    private EditText carModelEditText;
    private EditText numberOfSeatsEditText;
    private Button saveChangesButton;
    private DatabaseHelper dbHelper;
    private String loggedInUser = "testUser"; // Example logged-in user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);

        // Initialize the database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize UI elements
        nameEditText = findViewById(R.id.name);
        contactDetailsEditText = findViewById(R.id.contact_details);
        carMakeEditText = findViewById(R.id.car_make);
        carModelEditText = findViewById(R.id.car_model);
        numberOfSeatsEditText = findViewById(R.id.number_of_seats);
        saveChangesButton = findViewById(R.id.save_changes);

        // Load the user profile data
        loadUserProfile();

        // Set the save changes button click listener
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSaveChanges();
            }
        });
    }

    // Method to load user profile data from the database
    private void loadUserProfile() {
        // Query the database for user details
        Cursor cursor = dbHelper.getUser(loggedInUser);
        if (cursor.moveToFirst()) {
            // Populate the input fields with the user details
            nameEditText.setText(cursor.getString(cursor.getColumnIndex("name")));
            contactDetailsEditText.setText(cursor.getString(cursor.getColumnIndex("contact_details")));
            carMakeEditText.setText(cursor.getString(cursor.getColumnIndex("car_make")));
            carModelEditText.setText(cursor.getString(cursor.getColumnIndex("car_model")));
            numberOfSeatsEditText.setText(cursor.getString(cursor.getColumnIndex("number_of_seats")));
        }
        cursor.close();
    }

    // Method to handle save changes button click
    private void handleSaveChanges() {
        // Get values from input fields
        String name = nameEditText.getText().toString().trim();
        String contactDetails = contactDetailsEditText.getText().toString().trim();
        String carMake = carMakeEditText.getText().toString().trim();
        String carModel = carModelEditText.getText().toString().trim();
        String numberOfSeats = numberOfSeatsEditText.getText().toString().trim();

        // Perform validation to check if fields are not empty
        if (name.isEmpty() || contactDetails.isEmpty() || carMake.isEmpty() || carModel.isEmpty() || numberOfSeats.isEmpty()) {
            // Show a toast message if fields are empty
            Toast.makeText(ProfileManagementActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Update the user details in the database
            boolean isUpdated = dbHelper.updateUser(loggedInUser, name, contactDetails, carMake, carModel, Integer.parseInt(numberOfSeats));
            if (isUpdated) {
                // Show a toast message indicating successful update
                Toast.makeText(ProfileManagementActivity.this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
            } else {
                // Show a toast message indicating update failure
                Toast.makeText(ProfileManagementActivity.this, "Failed to save changes", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
