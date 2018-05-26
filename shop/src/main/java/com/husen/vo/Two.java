package com.husen.vo;

import java.io.Serializable;

/**
 * @author 11785
 */
public class Two implements Serializable {
    private String registNumber;

    private String blPicPath;

    public String getRegistNumber() {
        return registNumber;
    }

    public void setRegistNumber(String registNumber) {
        this.registNumber = registNumber;
    }

    public String getBlPicPath() {
        return blPicPath;
    }

    public void setBlPicPath(String blPicPath) {
        this.blPicPath = blPicPath;
    }

    @Override
    public String toString() {
        return "Two{" +
                "registNumber='" + registNumber + '\'' +
                ", blPicPath='" + blPicPath + '\'' +
                '}';
    }
}
