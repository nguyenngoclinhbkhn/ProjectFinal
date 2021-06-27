package com.bkhn.doantotnghiep.model;

import com.bkhn.doantotnghiep.R;

public class Light3 implements IDevice {
    private String status;

    public Light3(String status) {
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
        return TypeDevice.LIGHT3;
    }

    @Override
    public int getResourceDevice() {
        if (isDeviceOn()) return R.drawable.on_lamp;
        return R.drawable.off_lamp;
    }

    @Override
    public String getKeyDevice() {
        return "LIGHT3";
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
