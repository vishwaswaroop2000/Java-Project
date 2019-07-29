package com.company;

import java.util.ArrayList;

public interface Users {//this interface is implemented by the staff,admin and the customer
    ArrayList<Car> searchCarByDetails(int minimumSeats, int maximumSeats);

    ArrayList<Car> searchCarByDetails(Object vanSize);

    ArrayList<Car> searchCarByDetails(String colour);

    ArrayList<Car> searchCarByDetails(String model, String transmission);
}
