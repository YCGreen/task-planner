package model;

import java.time.LocalDate;

public class Task {

    private String name;
    private double len;
    private double lenComplete;
    private AssignStatus stat;
    private boolean interm;
    //int id?

    public Task(String name, double len, boolean interm) {
        this.name = name;
        this.len = len;
        stat = AssignStatus.UNBEGUN;
        lenComplete = 0;
        this.interm = interm;
    }

    public Task(String name, int len, LocalDate deadline) {
        //make this default
    }

    public void setMode(boolean interm) {
        this.interm = interm;
    }

    public boolean getMode() {
        return interm;
    }

    public void setLenComplete(double lenComplete) {
        this.lenComplete = lenComplete;
        if(lenComplete == len) {
            stat = AssignStatus.COMPLETE;
        }
    }

    public double getLen() {
        return len - lenComplete;
    }

    public void begin() {
        stat = AssignStatus.BEGUN;
    }

    public void complete() {
        stat = AssignStatus.COMPLETE;
    }

    public AssignStatus getStatus() {
        return stat;
    }

    public String toString() {
        return name + ", length: " + len;
    }
}
