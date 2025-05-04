package model;

import java.time.LocalDate;

public class Task {

    private String name;
    private double len;
    private double lenComplete;
    private boolean interm;
    private double lenPerDay;
    //int id?

    public Task(String name, double len, boolean interm) {
        this.name = name;
        this.len = len;
        lenComplete = 0;
        this.interm = interm;
        double lenPerDay = 0;
    }

    public Task(String name, int len, LocalDate deadline) {
        //make this default
    }

    public void setMode(boolean interm) {
        this.interm = interm;
    }

    public void setLenPerDay(double lenPerDay) {
        this.lenPerDay = lenPerDay;
    }

    public boolean getMode() {
        return interm;
    }

    public void setLenComplete(double lenComplete) {
        this.lenComplete = lenComplete;
        if (lenComplete == len) {
            //
        }
    }

    public double getLenComplete() {
        return lenComplete;
    }

    public double getLenRemaining() {
        return len - lenComplete;
    }

    public void complete() {
        lenComplete = len;
    }

    public boolean isComplete() {
        return lenComplete == len;
    }

    public double getLen() {
        return len - lenComplete;
    }

    public String toString() {
        return name;
    }
}

