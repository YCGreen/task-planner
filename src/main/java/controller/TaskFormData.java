package controller;

public class TaskFormData {
    private String name;
    private Double length;
    private String date;
    private boolean interm;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setInterm(String interm) {
        this.interm = Boolean.parseBoolean(interm);
    }


    public boolean isInterm() {
        return interm;
    }

    public boolean isValid() {
        return name != null && length != null && date != null;
    }

}
