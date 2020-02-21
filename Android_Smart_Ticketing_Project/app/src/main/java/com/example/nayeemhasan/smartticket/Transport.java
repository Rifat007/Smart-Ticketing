package com.example.nayeemhasan.smartticket;

/**
 * Created by Nayeem Hasan on 03-Jun-17.
 */

public class Transport {

    int id;
    private String name;
    private float ratings;
    private int fare;

    public Transport(String name, float ratings) {
        this.name = name;
        this.ratings = ratings;
    }

    public Transport(int id, String name, float ratings,int fare) {
        this.id = id;
        this.name = name;
        this.ratings = ratings;
        this.fare = fare;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRatings() {
        return ratings;
    }

    public void setRatings(float ratings) {
        this.ratings = ratings;
    }

    public int getFare() {
        return fare;
    }
}
