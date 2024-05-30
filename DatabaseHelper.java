package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "RideSharing.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DatabaseHelper";

    // User table columns
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CONTACT_DETAILS = "contact_details";
    private static final String COLUMN_CAR_MAKE = "car_make";
    private static final String COLUMN_CAR_MODEL = "car_model";
    private static final String COLUMN_NUMBER_OF_SEATS = "number_of_seats";

    // Ride table columns
    private static final String TABLE_RIDES = "rides";
    private static final String COLUMN_RIDE_ID = "id";
    private static final String COLUMN_START_POINT = "start_point";
    private static final String COLUMN_DESTINATION = "destination";
    private static final String COLUMN_NUMBER_OF_SEATS_RIDE = "number_of_seats";

    // Ride request table columns
    private static final String TABLE_RIDE_REQUESTS = "ride_requests";
    private static final String COLUMN_REQUEST_ID = "request_id";
    private static final String COLUMN_REQUEST_DETAILS = "request_details";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // This method is called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL command to create the users table
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_CONTACT_DETAILS + " TEXT, " +
                COLUMN_CAR_MAKE + " TEXT, " +
                COLUMN_CAR_MODEL + " TEXT, " +
                COLUMN_NUMBER_OF_SEATS + " INTEGER)";

        // SQL command to create the rides table
        String createRidesTable = "CREATE TABLE " + TABLE_RIDES + " (" +
                COLUMN_RIDE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_START_POINT + " TEXT, " +
                COLUMN_DESTINATION + " TEXT, " +
                COLUMN_NUMBER_OF_SEATS_RIDE + " INTEGER)";

        // SQL command to create the ride requests table
        String createRideRequestsTable = "CREATE TABLE " + TABLE_RIDE_REQUESTS + " (" +
                COLUMN_REQUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_REQUEST_DETAILS + " TEXT)";

        // Execute the SQL commands
        db.execSQL(createUsersTable);
        db.execSQL(createRidesTable);
        db.execSQL(createRideRequestsTable);
    }

    // This method is called when the database needs to be upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RIDES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RIDE_REQUESTS);
        // Create the tables again with the new structure
        onCreate(db);
    }

    // Method to insert a new user into the database
    public boolean insertUser(String username, String password) {
        // Get a writable database
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new set of values to insert
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);

        // Insert the new row, returning the primary key value of the new row
        long result = db.insert(TABLE_USERS, null, contentValues);
        // If result is -1, insertion failed
        return result != -1;
    }

    // Method to check if a user exists with the given username and password
    public boolean checkUser(String username, String password) {
        // Get a readable database
        SQLiteDatabase db = this.getReadableDatabase();
        // Query the database for a user with the given username and password
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{username, password});
        // Check if any results were returned
        boolean exists = (cursor.getCount() > 0);
        // Close the cursor to free up resources
        cursor.close();
        return exists;
    }

    // Method to update a user's information
    public boolean updateUser(String username, String name, String contactDetails, String carMake, String carModel, int numberOfSeats) {
        // Get a writable database
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new set of values to update
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_CONTACT_DETAILS, contactDetails);
        contentValues.put(COLUMN_CAR_MAKE, carMake);
        contentValues.put(COLUMN_CAR_MODEL, carModel);
        contentValues.put(COLUMN_NUMBER_OF_SEATS, numberOfSeats);

        // Update the row, returning the number of rows affected
        int result = db.update(TABLE_USERS, contentValues, COLUMN_USERNAME + " = ?", new String[]{username});
        return result > 0;
    }

    // Method to get a user's information
    public Cursor getUser(String username) {
        // Get a readable database
        SQLiteDatabase db = this.getReadableDatabase();
        // Query the database for a user with the given username
        return db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=?", new String[]{username});
    }

    // Method to insert a new ride into the database
    public boolean insertRide(String startPoint, String destination, int numberOfSeats) {
        // Get a writable database
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new set of values to insert
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_START_POINT, startPoint);
        contentValues.put(COLUMN_DESTINATION, destination);
        contentValues.put(COLUMN_NUMBER_OF_SEATS_RIDE, numberOfSeats);

        // Insert the new row, returning the primary key value of the new row
        long result = db.insert(TABLE_RIDES, null, contentValues);
        // If result is -1, insertion failed
        return result != -1;
    }

    // Method to get all rides from the database
    public Cursor getAllRides() {
        // Get a readable database
        SQLiteDatabase db = this.getReadableDatabase();
        // Query the database for all rides
        return db.rawQuery("SELECT * FROM " + TABLE_RIDES, null);
    }

    // Method to update a ride's information
    public boolean updateRide(int id, String startPoint, String destination, int numberOfSeats) {
        // Get a writable database
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new set of values to update
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_START_POINT, startPoint);
        contentValues.put(COLUMN_DESTINATION, destination);
        contentValues.put(COLUMN_NUMBER_OF_SEATS_RIDE, numberOfSeats);

        // Update the row, returning the number of rows affected
        int result = db.update(TABLE_RIDES, contentValues, COLUMN_RIDE_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Method to delete a ride from the database
    public boolean deleteRide(int id) {
        // Get a writable database
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete the row, returning the number of rows affected
        int result = db.delete(TABLE_RIDES, COLUMN_RIDE_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Method to insert a new ride request into the database
    public boolean insertRideRequest(String rideDetails) {
        // Get a writable database
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new set of values to insert
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_REQUEST_DETAILS, rideDetails);

        try {
            // Insert the new row, returning the primary key value of the new row
            long result = db.insertOrThrow(TABLE_RIDE_REQUESTS, null, contentValues);
            return result != -1;
        } catch (SQLException e) {
            // Log the error if insertion fails
            Log.e(TAG, "Error inserting ride request", e);
            return false;
        }
    }

    // Method to get rides by destination from the database
    public Cursor getRidesByDestination(String destination) {
        // Get a readable database
        SQLiteDatabase db = this.getReadableDatabase();
        // Query the database for rides with the given destination
        return db.rawQuery("SELECT * FROM " + TABLE_RIDES + " WHERE " + COLUMN_DESTINATION + "=?", new String[]{destination});
    }
}
