package com.company;

public class Car {
    String numberPlate;
    String model;
    String type;
    String vanSize;
    String colour;
    String mileage;
    String accidentHistory;
    String transmissionType;
    String price;
    String arrivalDate;
    String sellingDate;

    //Written below is the car constructor used to initialize the attributes of the car, to a car object
    public Car(String numberPlate, String model, String type, String vanSize, String colour, String mileage,
               String accidentHistory, String transmissionType, String price, String arrivalDate, String sellingDate) {
        this.numberPlate = numberPlate;
        this.model = model;
        this.vanSize = vanSize;
        this.type = type;
        this.colour = colour;
        this.mileage = mileage;
        this.accidentHistory = accidentHistory;
        this.transmissionType = transmissionType;
        this.price = price;
        this.arrivalDate = arrivalDate;
        this.sellingDate = sellingDate;
    }
}
