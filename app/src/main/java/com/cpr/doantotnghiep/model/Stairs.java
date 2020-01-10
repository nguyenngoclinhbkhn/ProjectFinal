package com.cpr.doantotnghiep.model;


// dieu khien den bat tat o cau thang
// cau thang co 2 den , ben tren va ben duoi
public class Stairs {
    private String id;
    private int automatic;
    private int state1;



    public Stairs(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAutomatic() {
        return automatic;
    }

    public void setAutomatic(int automatic) {
        this.automatic = automatic;
    }

    public int getState1() {
        return state1;
    }

    public void setState1(int state1) {
        this.state1 = state1;
    }


    public Stairs(String id, int automatic, int state1, int state2) {
        this.id = id;
        this.automatic = automatic;
        this.state1 = state1;
    }
}
