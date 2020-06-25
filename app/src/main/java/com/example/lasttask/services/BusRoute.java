package com.example.lasttask.services;

public class BusRoute {
    private int routeNumber;
    private String name;
    private String stopA;
    private String stopB;

    public int getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(int routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStopA() {
        return stopA;
    }

    public void setStopA(String stopA) {
        this.stopA = stopA;
    }

    public String getStopB() {
        return stopB;
    }

    public void setStopB(String stopB) {
        this.stopB = stopB;
    }
}
