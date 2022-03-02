package com.ljdsh.beans;

public class Super {
    //création des variables
    private String pseudo;
    private String ville;
    private String telephone;
    private String longitude;
    private String lattitude;
    // getter pour longitude
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
     * @param lattitude
     */
    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    /**
     * @return getter pour pseudo
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * @param pseudo
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * @return getter pour ville
     */
    public String getVille() {
        return ville;
    }

    /**
     * @param ville
     */
    public void setVille(String ville) {
        this.ville = ville;
    }

    /**
     * @return getter pour telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
