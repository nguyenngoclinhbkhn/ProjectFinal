package com.bkhn.doantotnghiep.model;

public interface IDevice {
    public TypeDevice getTypeDevice();
    public int getResourceDevice();
    public String getKeyDevice();

    public Boolean isDeviceOn();
    void setStatusDevice();
    String getStatusDevice();
}
