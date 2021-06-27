package com.bkhn.doantotnghiep.model;

import com.bkhn.doantotnghiep.R;

import java.util.ArrayList;

public class DeviceInHome {
    private String CURTAIN;
    private String DOOR;
    private String FAN;
    private int GAS;
    private float HUMIDITY;
    private String LIGHT1;
    private String LIGHT2;
    private String LIGHT3;
    private double TEMPERATURE;

    public DeviceInHome(String CURTAIN, String DOOR, String FAN, int GAS, float HUMIDITY, String LIGHT1, String LIGHT2, String LIGHT3, double TEMPERATURE) {
        this.CURTAIN = CURTAIN;
        this.DOOR = DOOR;
        this.FAN = FAN;
        this.GAS = GAS;
        this.HUMIDITY = HUMIDITY;
        this.LIGHT1 = LIGHT1;
        this.LIGHT2 = LIGHT2;
        this.LIGHT3 = LIGHT3;
        this.TEMPERATURE = TEMPERATURE;
    }

    public DeviceInHome(){

    }

    public int getResourceLed1() {
        if (LIGHT1.equals("0")) return R.drawable.on_lamp;
        return R.drawable.off_lamp;
    }

    public int getResourceLed2() {
        if (LIGHT2.equals("0")) return R.drawable.on_lamp;
        return R.drawable.off_lamp;
    }

    public int getResourceLed3() {
        if (LIGHT3.equals("0")) return R.drawable.on_lamp;
        return R.drawable.off_lamp;
    }

    public String getCURTAIN() {
        return CURTAIN;
    }

    public void setCURTAIN(String CURTAIN) {
        this.CURTAIN = CURTAIN;
    }

    public String getDOOR() {
        return DOOR;
    }

    public void setDOOR(String DOOR) {
        this.DOOR = DOOR;
    }

    public String getFAN() {
        return FAN;
    }

    public void setFAN(String FAN) {
        this.FAN = FAN;
    }

    public int getGAS() {
        return GAS;
    }

    public void setGAS(int GAS) {
        this.GAS = GAS;
    }

    public float getHUMIDITY() {
        return HUMIDITY;
    }

    public void setHUMIDITY(float HUMIDITY) {
        this.HUMIDITY = HUMIDITY;
    }

    public String getLIGHT1() {
        return LIGHT1;
    }

    public void setLIGHT1(String LIGHT1) {
        this.LIGHT1 = LIGHT1;
    }

    public String getLIGHT2() {
        return LIGHT2;
    }

    public void setLIGHT2(String LIGHT2) {
        this.LIGHT2 = LIGHT2;
    }

    public String getLIGHT3() {
        return LIGHT3;
    }

    public void setLIGHT3(String LIGHT3) {
        this.LIGHT3 = LIGHT3;
    }

    public double getTEMPERATURE() {
        return TEMPERATURE;
    }

    public void setTEMPERATURE(double TEMPERATURE) {
        this.TEMPERATURE = TEMPERATURE;
    }

    public static ArrayList<IDevice> getListDevice(DeviceInHome device) {
        ArrayList<IDevice> list = new ArrayList<>();
        list.add(new Fan(device.getFAN()));
        list.add(new Light1(device.getLIGHT1()));
        list.add(new Light2(device.getLIGHT2()));
        list.add(new Light3(device.getLIGHT3()));
        list.add(new Door(device.getDOOR()));
        list.add(new Curtain(device.getCURTAIN()));
        return list;
    }
}
