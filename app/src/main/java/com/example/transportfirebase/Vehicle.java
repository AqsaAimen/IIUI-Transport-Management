package com.example.transportfirebase;

public class Vehicle {
    public String vehno,vehimg,vehdriver;
    public Vehicle(){

    }

    public String getVehno() {
        return vehno;
    }

    public void setVehno(String vehno) {
        this.vehno = vehno;
    }

    public String getVehimg() {
        return vehimg;
    }

    public void setVehimg(String vehimg) {
        this.vehimg = vehimg;
    }

    public String getVehdriver() {
        return vehdriver;
    }

    public void setVehdriver(String vehdriver) {
        this.vehdriver = vehdriver;
    }
    public Vehicle(String vehno,String vehimg,String vehdriver){
        this.vehno=vehno;
        this.vehimg=vehimg;
        this.vehdriver=vehdriver;
    }
}
