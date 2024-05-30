package com.example.myapplication;

import java.io.Serializable;

public class Ride implements Serializable {
    private int id;
    private String startPoint;
    private String destination;
    private int numberOfSeats;

    public Ride() {
        // Default constructor
    }

    public Ride(int id, String startPoint, String destination, int numberOfSeats) {
        this.id = id;
        this.startPoint = startPoint;
        this.destination = destination;
        this.numberOfSeats = numberOfSeats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}
