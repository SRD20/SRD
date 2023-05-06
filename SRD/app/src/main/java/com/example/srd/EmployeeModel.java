package com.example.srd;

public class EmployeeModel
{
    String empId;
    String name;
    String phoneNumber;
    String password;
    String typeOfData;


    public EmployeeModel(String empId,String name,String phoneNumber,String password,String typeOfData){
        this.empId=empId;
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.password=password;
        this.typeOfData=typeOfData;
    }


    public String getEmpId() {
        return empId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTypeOfData() {
        return typeOfData;
    }

    public void setTypeOfData(String typeOfData) {
        this.typeOfData = typeOfData;
    }
}
