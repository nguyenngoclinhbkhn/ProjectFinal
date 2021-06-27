package com.bkhn.doantotnghiep.model;

import com.bkhn.doantotnghiep.R;

public class Curtain implements IDevice {
    private String status;

    public Curtain(String status) {
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
        return TypeDevice.CURTAIN;
    }

    @Override
    public int getResourceDevice() {
        if (isDeviceOn()) return R.drawable.curtain_open;
        return R.drawable.curtain_close;
    }

    @Override
    public String getKeyDevice() {
        return "CURTAIN";
    }

    @Override
    public Boolean isDeviceOn() {
        if (status.equals("1")) return true;
        return false;
    }

    @Override
    public void setStatusDevice() {
        if (status.equals("1")) {
            status = "2";
        } else {
            status = "1";
        }
    }

    @Override
    public String getStatusDevice() {
        return status;
    }
}
