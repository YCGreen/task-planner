package model;

import java.time.LocalDate;

public class Task {

    private String name;
    private double len;
    private double lenComplete;
    private AssignStatus stat;
    //int id?

    public Task(String name, double len) {
        this.name = name;
        this.len = len;
        stat = AssignStatus.UNBEGUN;
        lenComplete = 0;
    }

    public Task(String name, int len, LocalDate deadline) {
        //make this default
    }

    public void setLenComplete(double lenComplete) {
        this.lenComplete = lenComplete;
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
