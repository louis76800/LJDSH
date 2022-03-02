package com.ljdsh.beans;
public class Position {
    //création des variables
    private String longitude;
    private String lattitude;

    /**
     * @return getter pour longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return getter pour lattitude
     */
    public String getLattitude() {
        return lattitude;
    }

    /**
     * @param lattitude  setter lattitude
     */
    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }
}