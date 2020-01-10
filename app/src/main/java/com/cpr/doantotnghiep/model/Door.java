package com.cpr.doantotnghiep.model;

public class Door {

    private int ID;
    private int state;

    public Door(int ID, int state) {
        this.ID = ID;
        this.state = state;
    }

    public Door() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
