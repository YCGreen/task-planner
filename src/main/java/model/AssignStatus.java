package model;

public enum AssignStatus {
    UNBEGUN, //has not yet been assigned
    BEGUN, //has been assigned partially (exceeds capacity of assigned day)
    COMPLETE; //has been fully assigned (to one day or more)
}
