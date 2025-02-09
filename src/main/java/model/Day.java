package model;

import java.util.ArrayList;
import java.util.List;

public class Day {

    private int availHours;
    private List<Task> tasks;

    public Day(int availHours) {
        this.availHours = availHours;
        tasks = new ArrayList<>();
    }

    public int getAvailHours() {
        return availHours;
    }

}
