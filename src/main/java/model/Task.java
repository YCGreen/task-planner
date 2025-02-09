package model;

import java.time.LocalDate;

public class Task {

    private String name;
    private int len;
    private AssignStatus stat;
    //int id?

    public Task(String name, int len) {
        this.name = name;
        this.len = len;
        stat = AssignStatus.UNBEGUN;
    }

    public Task(String name, int len, LocalDate deadline) {
        //make this default
    }

    public int getLen() {
        return len;
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
