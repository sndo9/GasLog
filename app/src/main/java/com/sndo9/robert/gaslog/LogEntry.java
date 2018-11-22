package com.sndo9.robert.gaslog;

import org.json.JSONException;
import org.json.JSONObject;

public class LogEntry {

    private int mileage;
    private double gallons;
    private double totalPrice;
    private double pricePerGallon;
    private String date;
    private boolean successful;

    LogEntry(JSONObject entry) {
        try{
            mileage = (int) entry.get("Mileage");
            gallons = (double) entry.get("Gallons");
            totalPrice = (double) entry.get("Total Price");
            pricePerGallon = (double) entry.get("Price Per Gallon");
            date = entry.get("Date").toString();
            successful = true;
        } catch(JSONException e) {
            e.printStackTrace();
            successful = false;
        }
    }

    public int getMileage() {
        return mileage;
    }

    public double getGallons() {
        return gallons;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getPricePerGallon() {
        return pricePerGallon;
    }
    public String getDate() {
        return date;
    }
    public boolean wasSuccessful() {
        return successful;
    }

    public String toString() {
        return String.valueOf(mileage);
    }
}
