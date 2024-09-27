package com.example.transportfirebase;

public class VehiclesData {
    private int vehno;
    private String vehname;
    private String drivercnic;
    private String Imageurl;

    public VehiclesData(int vehno, String vehname, String drivercnic,String Imageurl) {
        this.vehno=vehno;
        this.vehname=vehname;
        this.drivercnic=drivercnic;
         this.Imageurl=Imageurl;

    }
    public VehiclesData(){

    }

    public int getVehno() {
        return vehno;
    }

    public void setVehno(int vehno) {
        this.vehno = vehno;
    }

    public String getVehname() {
        return vehname;
    }

    public void setVehname(String vehname) {
        this.vehname = vehname;
    }

    public String getDrivercnic() {
        return drivercnic;
    }

    public void setDrivercnic(String drivercnic) {
        this.drivercnic = drivercnic;
    }

    public String getImageurl() {
        return Imageurl;
    }

    public void setImageurl(String imageurl) {
        Imageurl = imageurl;
    }
}
