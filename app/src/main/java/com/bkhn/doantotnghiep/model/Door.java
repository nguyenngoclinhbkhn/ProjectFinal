package com.bkhn.doantotnghiep.model;

import com.bkhn.doantotnghiep.R;

public class Door implements IDevice {
    private String status;

    public Door(String status) {
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
        return TypeDevice.DOOR;
    }

    @Override
    public int getResourceDevice() {
        if (isDeviceOn()) return R.drawable.open_door;
        return R.drawable.closed_door;
    }

    @Override
    public String getKeyDevice() {
        return "DOOR";
    }

    @Override
    public Boolean isDeviceOn() {
        if (status.toLowerCase().equals("unlock")) return true;
        return false;
    }

    @Override
    public void setStatusDevice() {
        if (status.toLowerCase().equals("unlock")) {
            status = "Lock";
        } else {
            status = "Unlock";
        }
    }

    @Override
    public String getStatusDevice() {
        return status;
    }
}
