package com.example.demo.dto;

import java.util.Date;

public class AirQuality {
//    Date date;
//    Date time;
    int id;
    String date;
    String time;
    float CO_GT;
    int PT08_S1_CO;
    int NMHC_GT;
    float C6H6_GT;
    int PT08_S2_NMHC;
    int NOx_GT;
    int PT08_S3_NOx;
    int NO2_GT;
    int PT08_S4_NO2;
    int PT08_S5_O3;
    float T;
    float RH;
    double AH;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public Time getTime() {
//        return time;
//    }
//
//    public void setTime(Time time) {
//        this.time = time;
//    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getCO_GT() {
        return CO_GT;
    }

    public void setCO_GT(float CO_GT) {
        this.CO_GT = CO_GT;
    }

    public int getPT08_S1_CO() {
        return PT08_S1_CO;
    }

    public void setPT08_S1_CO(int PT08_S1_CO) {
        this.PT08_S1_CO = PT08_S1_CO;
    }

    public int getNMHC_GT() {
        return NMHC_GT;
    }

    public void setNMHC_GT(int NMHC_GT) {
        this.NMHC_GT = NMHC_GT;
    }

    public float getC6H6_GT() {
        return C6H6_GT;
    }

    public void setC6H6_GT(float c6H6_GT) {
        C6H6_GT = c6H6_GT;
    }

    public int getPT08_S2_NMHC() {
        return PT08_S2_NMHC;
    }

    public void setPT08_S2_NMHC(int PT08_S2_NMHC) {
        this.PT08_S2_NMHC = PT08_S2_NMHC;
    }

    public int getNOx_GT() {
        return NOx_GT;
    }

    public void setNOx_GT(int NOx_GT) {
        this.NOx_GT = NOx_GT;
    }

    public int getPT08_S3_NOx() {
        return PT08_S3_NOx;
    }

    public void setPT08_S3_NOx(int PT08_S3_NOx) {
        this.PT08_S3_NOx = PT08_S3_NOx;
    }

    public int getNO2_GT() {
        return NO2_GT;
    }

    public void setNO2_GT(int NO2_GT) {
        this.NO2_GT = NO2_GT;
    }

    public int getPT08_S4_NO2() {
        return PT08_S4_NO2;
    }

    public void setPT08_S4_NO2(int PT08_S4_NO2) {
        this.PT08_S4_NO2 = PT08_S4_NO2;
    }

    public int getPT08_S5_O3() {
        return PT08_S5_O3;
    }

    public void setPT08_S5_O3(int PT08_S5_O3) {
        this.PT08_S5_O3 = PT08_S5_O3;
    }

    public float getT() {
        return T;
    }

    public void setT(float t) {
        T = t;
    }

    public float getRH() {
        return RH;
    }

    public void setRH(float RH) {
        this.RH = RH;
    }

    public double getAH() {
        return AH;
    }

    public void setAH(double AH) {
        this.AH = AH;
    }

    @Override
    public String toString() {
        return "airQuality{" +
                "date=" + date +
                ", time=" + time +
                ", CO_GT=" + CO_GT +
                ", PT08_S1_CO=" + PT08_S1_CO +
                ", NMHC_GT=" + NMHC_GT +
                ", C6H6_GT=" + C6H6_GT +
                ", PT08_S2_NMHC=" + PT08_S2_NMHC +
                ", NOx_GT=" + NOx_GT +
                ", PT08_S3_NOx=" + PT08_S3_NOx +
                ", NO2_GT=" + NO2_GT +
                ", PT08_S4_NO2=" + PT08_S4_NO2 +
                ", PT08_S5_O3=" + PT08_S5_O3 +
                ", T=" + T +
                ", RH=" + RH +
                ", AH=" + AH +
                '}';
    }
}
