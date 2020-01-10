package com.cpr.doantotnghiep.model;


// model cho nhiet do , do am
public class Air {
    private String idAir;
    private int temperature;
    private int humidity;

    public Air(){

    }
    public String getIdAir() {
        return idAir;
    }

    public void setIdAir(String idAir) {
        this.idAir = idAir;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public Air(String idAir, int temperature, int humidity) {
        this.idAir = idAir;
        this.temperature = temperature;
        this.humidity = humidity;
    }
}
