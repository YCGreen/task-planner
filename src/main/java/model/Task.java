package model;

import java.time.LocalDate;

public class Task {

    private String name;
    private int len;
    private int lenComplete;
    private AssignStatus stat;
    //int id?

    public Task(String name, int len) {
        this.name = name;
        this.len = len;
        stat = AssignStatus.UNBEGUN;
        lenComplete = 0;
    }

    public Task(String name, int len, LocalDate deadline) {
        //make this default
    }

    public void setLenComplete(int lenComplete) {
        this.lenComplete = lenComplete;
    }

    public int getLen() {
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
