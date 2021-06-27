package com.bkhn.doantotnghiep.model;

import com.bkhn.doantotnghiep.R;

public class Fan implements IDevice {
    private String status;

    public Fan(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public TypeDevice getTypeDevice() {
        return TypeDevice.FANS;
    }

    @Override
    public int getResourceDevice() {
        return R.drawable.fan_gif;
    }

    @Override
    public String getKeyDevice() {
        return "FAN";
    }

    @Override
    public Boolean isDeviceOn() {
        if (status.equals("1")) return true;
        return false;
    }

    @Override
    public void setStatusDevice() {
        if (status.equals("0")) {
            status = "1";
        } else {
            status = "0";
        }
    }

    @Override
    public String getStatusDevice() {
        return status;
    }
}
