package com.example.srd;

public class LabDataModel {
    String labId,empId,time,date;
    Float temp,humidity;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LabDataModel(String labId, String empId, String time, String date, Float temp, Float humidity) {
        this.labId = labId;
        this.empId = empId;
        this.time = time;
        this.date = date;
        this.temp = temp;
        this.humidity = humidity;
    }
    public LabDataModel(){
        super();
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }
}
