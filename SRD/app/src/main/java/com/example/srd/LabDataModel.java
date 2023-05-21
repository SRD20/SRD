package com.example.srd;

public class LabDataModel {
    String labId,empId,time,date,temp,humidity,name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public LabDataModel(String labId, String empId, String name,String time, String date, String temp, String humidity) {
        this.labId = labId;
        this.empId = empId;
        this.name = name;
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

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
