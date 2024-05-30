package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class OfferRideActivity extends AppCompatActivity implements OnMapReadyCallback {

    // Declare UI elements and variables
    private EditText startPointEditText;
    private EditText destinationEditText;
    private EditText numberOfSeatsEditText;
    private MapView mapView;
    private Button submitButton;
    private GoogleMap gMap;
    private DatabaseHelper db;
    private Ride ride;

    // Key to save the state of the MapView
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_ride);

        // Initialize UI elements
        startPointEditText = findViewById(R.id.start_point);
        destinationEditText = findViewById(R.id.destination);
        numberOfSeatsEditText = findViewById(R.id.number_of_seats);
        mapView = findViewById(R.id.map);
        submitButton = findViewById(R.id.submit);

        // Initialize the database helper
        db = new DatabaseHelper(OfferRideActivity.this);

        // Restore mapView state if available
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        // Initialize mapView
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        // Set the submit button click listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubmit();
            }
        });
    }

    // Method to handle submit button click
    private void handleSubmit() {
        // Get values from input fields
        String startPoint = startPointEditText.getText().toString().trim();
        String destination = destinationEditText.getText().toString().trim();
        int numberOfSeats = Integer.parseInt(numberOfSeatsEditText.getText().toString().trim());

        // Create a new Ride object and set its properties
        ride = new Ride();
        ride.setStartPoint(startPoint);
        ride.setDestination(destination);
        ride.setNumberOfSeats(numberOfSeats);

        // Perform validation to check if fields are not empty
        if (startPoint.isEmpty() || destination.isEmpty()) {
            // Show a toast message if fields are empty
            Toast.makeText(OfferRideActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Show a toast message indicating ride details submission
            Toast.makeText(OfferRideActivity.this, "Ride details submitted", Toast.LENGTH_SHORT).show();

            // Insert the ride details into the database
            db.insertRide(startPoint, destination, numberOfSeats);

            // Log the number of rides for debugging purposes
            Log.d("Offer Ride", "Number of Rides:" + db.getAllRides().getCount());
        }
    }

    // Method called when the map is ready to be used
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        // Set default location (e.g., Sydney, Australia)
        LatLng defaultLocation = new LatLng(-34, 151);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10));

        // Enable zoom controls on the map
        gMap.getUiSettings().setZoomControlsEnabled(true);

        // Add a marker at the default location
        gMap.addMarker(new MarkerOptions().position(defaultLocation).title("Default Location"));
    }

    // Lifecycle method to resume the mapView
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    // Lifecycle method to start the mapView
    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    // Lifecycle method to stop the mapView
    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    // Lifecycle method to pause the mapView
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    // Lifecycle method to destroy the mapView
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    // Method called when the device is running low on memory
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    // Method to save the state of the mapView
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the state of the mapView
        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }
}
