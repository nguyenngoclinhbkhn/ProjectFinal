package com.cpr.doantotnghiep.model;


// rem cua
public class Curtains {
    private String idCurtains;
    private int automatic;
    private int state;
    private int time;
    private int stop;

    public int getStop() {
        return stop;
    }

    public void setStop(int stop) {
        this.stop = stop;
    }

    public Curtains(){

    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getIdCurtains() {
        return idCurtains;
    }

    public void setIdCurtains(String idCurtains) {
        this.idCurtains = idCurtains;
    }

    public int getAutomatic() {
        return automatic;
    }

    public void setAutomatic(int automatic) {
        this.automatic = automatic;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Curtains(String idCurtains, int automatic, int state) {
        this.idCurtains = idCurtains;
        this.automatic = automatic;
        this.state = state;
    }
}
