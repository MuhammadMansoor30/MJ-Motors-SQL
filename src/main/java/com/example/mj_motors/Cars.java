package com.example.mj_motors;

public class Cars {


    public int carId;
    public String carName;
    public int carCondition;
    public int carModel;
    public int carPrice;
    public String carType;
    public int carImportYear;
    public String carAuction;
    public int carGrade;
    public int custId;
    public int manId;

    public  Cars(){

    }


    public Cars(int carId, int carCondition, String carName, int carModel, int carPrice, String carType){
        this.carId = carId;
        this.carCondition = carCondition;
        this.carName = carName;
        this.carModel = carModel;
        this.carPrice = carPrice;
        this.carType = carType;
    }

    public Cars(int carId, int carCondition, String carName, int carModel, int carPrice, String carType, int custId, int manId){
        this.carId = carId;
        this.carCondition = carCondition;
        this.carName = carName;
        this.carModel = carModel;
        this.carPrice = carPrice;
        this.carType = carType;
        this.custId = custId;
        this.manId = manId;
    }

    public Cars(int carId, int carCondition, String carName, int carModel, int carPrice, String carType , int yearOfImport , String auctionSheet , int carGrade){
        this.carId = carId;
        this.carCondition = carCondition;
        this.carName = carName;
        this.carModel = carModel;
        this.carPrice = carPrice;
        this.carType = carType;
        this.carImportYear = yearOfImport;
        this.carAuction = auctionSheet;
        this.carGrade = carGrade;
    }

    public Cars(int carId, int carCondition, String carName, int carModel, int carPrice, String carType , int yearOfImport , String auctionSheet , int carGrade,
                int custId, int manId){
        this.carId = carId;
        this.carCondition = carCondition;
        this.carName = carName;
        this.carModel = carModel;
        this.carPrice = carPrice;
        this.carType = carType;
        this.carImportYear = yearOfImport;
        this.carAuction = auctionSheet;
        this.carGrade = carGrade;
        this.custId = custId;
        this.manId = manId;
    }

    public int getCarModel() {
        return carModel;
    }


    public int getCarGrade() {
        return carGrade;
    }

    public String getCarName() {
        return carName;
    }

    public int getCarImportYear() {
        return carImportYear;
    }

    public String getCarAuction() {
        return carAuction;
    }

    public String getCarType() {
        return carType;
    }

    public int getCarCondition() {
        return carCondition;
    }

    public int getCarId() {
        return carId;
    }

    public int getCarPrice() {
        return carPrice;
    }

    public int getCustId() {
        return custId;
    }

    public int getManId() {
        return manId;
    }
}
