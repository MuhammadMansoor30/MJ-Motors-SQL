package com.example.mj_motors;


import javax.xml.stream.events.StartElement;

public class OwnerRecord {

    public int custId;
    public int manId;
    public int newCarId;
    public int oldCarId;
    public String carName;
    public int carCondition;
    public int carModel;
    public String carType;
    public int carImportYear;
    public String carAuction;
    public int carPrice;
    public  int carGrade;



    public OwnerRecord(int custId,
                       int manId,
                       int newCarId,
                       int oldCarId,
                       String carName,
                       int carCondition,
                       int carModel) {

        this.custId = custId;
        this.manId = manId;
        this.newCarId = newCarId;
        this.oldCarId  = oldCarId;
        this.carName  = carName;
        this.carCondition = carCondition;
        this.carModel = carModel;

    }

    public OwnerRecord(int custId,
                       int manId,
                       int newCarId,
                       int oldCarId,
                       String carName,
                       String  carType,
                       int carCondition,
                       int carModel  , int carPrice, int carImportYear , String carAuction , int carGrade) {

        this.custId = custId;
        this.manId = manId;
        this.newCarId = newCarId;
        this.oldCarId  = oldCarId;
        this.carName  = carName;
        this.carType = carType;
        this.carCondition = carCondition;
        this.carModel = carModel;
        this.carPrice = carPrice;
        this.carImportYear = carImportYear;
        this.carAuction = carAuction;
        this.carGrade = carGrade;

    }

    public int getCustId() {
        return custId;
    }

    public int getManId() {
        return manId;
    }

    public int getNewCarId() {
        return newCarId;
    }

    public int getOldCarId() {
        return oldCarId;
    }

    public String getCarName() {
        return carName;
    }



    public int getCarCondition() {
        return carCondition;
    }

    public int getCarModel() {
        return carModel;
    }

    public int getCarImportYear() {
        return carImportYear;
    }

    public String getCarAuction() {
        return carAuction;
    }

    public int getCarGrade() {
        return carGrade;
    }

    public int getCarPrice() {
        return carPrice;
    }
    public String getCarType(){
        return carType;
    }

}
