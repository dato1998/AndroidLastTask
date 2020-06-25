package com.example.lasttask.services;

import com.google.gson.annotations.SerializedName;

public class RouteStops {
    @SerializedName("Forward")
    private boolean forward;
    @SerializedName("HasBoard")
    private boolean hasBoard;
    @SerializedName("Virtual")
    private boolean virtual;
    @SerializedName("Lat")
    private double lat;
    @SerializedName("Lon")
    private double lon;
    @SerializedName("Sequence")
    private int sequence;
    @SerializedName("StopId")
    private int stopId;
    @SerializedName("Name")
    private String name;
    @SerializedName("Type")
    private String type;

    public boolean isForward() {
        return forward;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }

    public boolean isHasBoard() {
        return hasBoard;
    }

    public void setHasBoard(boolean hasBoard) {
        this.hasBoard = hasBoard;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
