package com.husen.vo;

import java.io.Serializable;

public class One implements Serializable {
    private String storesName;
    private String address;
    private String sportStr;

    public String getStoresName() {
        return storesName;
    }

    public void setStoresName(String storesName) {
        this.storesName = storesName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSportStr() {
        return sportStr;
    }

    public void setSportStr(String sportStr) {
        this.sportStr = sportStr;
    }

    @Override
    public String toString() {
        return "One{" +
                "storesName='" + storesName + '\'' +
                ", address='" + address + '\'' +
                ", sportStr='" + sportStr + '\'' +
                '}';
    }
}
