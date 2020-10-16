package com.example.demo.dto;

public class CONO2Mean {
    int id;
    String Date;
    String CO_mean;
    String NO2_mean;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getCO_mean() {
        return CO_mean;
    }

    public void setCO_mean(String CO_mean) {
        this.CO_mean = CO_mean;
    }

    public String getNO2_mean() {
        return NO2_mean;
    }

    public void setNO2_mean(String NO2_mean) {
        this.NO2_mean = NO2_mean;
    }

    @Override
    public String toString() {
        return "CONO2Mean{" +
                "id=" + id +
                ", Date='" + Date + '\'' +
                ", CO_mean='" + CO_mean + '\'' +
                ", NO2_mean='" + NO2_mean + '\'' +
                '}';
    }
}
