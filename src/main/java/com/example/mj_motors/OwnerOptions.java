package com.example.mj_motors;

public class OwnerOptions {

    public int carId;
    public String carName;
    public int carPrice;
    public int manId;
    public String manName;
    public int manPhNo;
    public int custId;

    public OwnerOptions(int custId){
        this.custId = custId;
    }

    public OwnerOptions(int carId, String carName){
        this.carId = carId;
        this.carName = carName;
    }
    public OwnerOptions(String carName , int carPrice){
        this.carName = carName;
        this.carPrice = carPrice;
    }

    public OwnerOptions(int manId , String manName , int manPhNo  ){
        this.manId = manId;
        this.manName = manName;
        this.manPhNo = manPhNo;
    }

    public int getCarId() {
        return carId;
    }

    public String getCarName() {
        return carName;
    }

    public int getCarPrice() {
        return carPrice;
    }

    public int getManId() {
        return manId;
    }

    public String getManName() {
        return manName;
    }

    public int getManPhNo() {
        return manPhNo;
    }

    public int getCustId() {
        return custId;
    }
}
