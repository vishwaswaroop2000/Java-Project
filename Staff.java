package com.company;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;


public class Staff implements Users {
    private JButton addCarsButton;
    private JButton addCarButton;
    private JButton sellCarButton;
    private JButton calculateRevenueButton;
    private JButton searchCarsButton;
    private JButton printCarsButton;
    private JButton logoutButton;
    private JPanel staffPanel;
    private static JFrame staffFrame;

    Staff() {
        addCarsButton.addActionListener(e -> {
            boolean carsWrittenToCarDatabase = addCars();
            if (carsWrittenToCarDatabase)
                JOptionPane.showMessageDialog(null, "Cars added to cars-database.txt");
            else
                JOptionPane.showMessageDialog(null, "Error in adding cars to cars-database");
        });
        addCarButton.addActionListener(e -> GetDetailsAddCar.showWindow());
        sellCarButton.addActionListener(e -> GetDetailsSellCar.showWindow());
        searchCarsButton.addActionListener(e -> GetDetailsSearchCar.showWindow("staff"));
        calculateRevenueButton.addActionListener(e -> GetDetailsCalculateRevenue.showWindow());
        printCarsButton.addActionListener(e -> {
            if (printCars())
                JOptionPane.showMessageDialog(null, "Cars printed onto cars-output");
            else
                JOptionPane.showMessageDialog(null, "Problem printing cars");
        });
        logoutButton.addActionListener(e -> {
            staffFrame.dispose();
            UserControl.showWindow();
        });
    }

    static void showWindow() {
        staffFrame = new JFrame();
        staffFrame.setContentPane(new Staff().staffPanel);
        staffFrame.pack();
        staffFrame.setVisible(true);
    }

    /* The staff can addCars , add Car, sell Car, print Cars , Search cars and calculate the revenue. Since the admin
    extends to the staff ,the admin INHERITS ALL THESE FUNCTIONS.
     */
    boolean addCars() {
        return UserFileDataController.writeCarsData(UserFileDataController.readCarsData(), "append");
    }

    String addCar(Car car) {
        try {
            String status = UserFileDataController.setCarDetailsAndWrite(car.numberPlate, car.model, car.type, car.vanSize,
                    car.colour, car.mileage, car.accidentHistory,
                    car.transmissionType, car.price, car.arrivalDate, car.sellingDate);
            return status;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    String sellCar(String numberPlate) {
        return UserFileDataController.sellCar(numberPlate);
    }

    public ArrayList<Car> searchCarByDetails(int minmumseats, int maximumseats) {
        return UserFileDataController.searchCarsByDetails(minmumseats, maximumseats, "staff");
    }

    public ArrayList<Car> searchCarByDetails(Object sizeOfVan) {
        return UserFileDataController.searchCarsByDetails(sizeOfVan, "staff");
    }

    public ArrayList<Car> searchCarByDetails(String colour) {
        return UserFileDataController.searchCarsByDetails(colour, "staff");
    }

    public ArrayList<Car> searchCarByDetails(String model, String transmissionType) {
        return UserFileDataController.searchCarsByDetails(model, transmissionType, "staff");
    }


    boolean printCars() {
        return UserFileDataController.printCars();
    }

    double calculateRevenue(String dateOrMonth) {
        return UserFileDataController.calculateTheRevenue(dateOrMonth);
    }


}
