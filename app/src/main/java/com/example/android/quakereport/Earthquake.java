package com.example.android.quakereport;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by benjaminfras on 10/8/17.
 */

public class Earthquake {
    private static final String LOG_TAG = "Earthquake";

    private double magnitude;
    private String location;
    private Date date;

    public Earthquake(double magnitude, String location, Date date) {
        this.magnitude = magnitude;
        this.location = location;
        this.date = date;
    }

    public Earthquake(double magnitude, String location, String date) {
        this.magnitude = magnitude;
        this.location = location;

        try {
            this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "Cannot parse provided date: " + e);
        }
    }

    public Earthquake(double magnitude, String location, long timestamp) {
        this.magnitude = magnitude;
        this.location = location;

        try {
            this.date = new Date(timestamp);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Date conversion from timestamp failed: " + e);
        }
    }

    public double getMagnitude() {
        return this.magnitude;
    }

    public String getLocation() {
        return this.location;
    }

    public Date getDate() {
        return this.date;
    }
}
