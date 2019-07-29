package com.company;

import javax.swing.*;
import java.util.ArrayList;

public class Customer implements Users {
    private JButton searchCarsButton;
    private JPanel customerPanel;
    private JButton logoutButton;
    private static JFrame customerFrame;

    Customer() {//Onclicking the buttons on the customerPanel, the customer can perform his functions
        searchCarsButton.addActionListener(e -> GetDetailsSearchCar.showWindow("customer"));
        logoutButton.addActionListener(e -> {
            customerFrame.dispose();
            UserControl.showWindow();
        });
    }

    static void showWindow() {//THIS FUNCTION IS USED VERY FREQUENTLY(OR SIMILAR FUNCTIONS). IT IS USED TO DISPLAY
        // THE FRAMES OF THE RESPECTIVE CLASSES AS A RESPONSE TO PARTICULAR BUTTONS.
        customerFrame = new JFrame();
        customerFrame.setContentPane(new Customer().customerPanel);
        customerFrame.pack();
        customerFrame.setVisible(true);
    }


    @Override
    public ArrayList<Car> searchCarByDetails(int minimumseats, int maximumseats) {
        return UserFileDataController.searchCarsByDetails(minimumseats, maximumseats, "Customer");
    }

    @Override
    public ArrayList<Car> searchCarByDetails(Object vanSize) {
        return UserFileDataController.searchCarsByDetails(vanSize, "Customer");
    }

    @Override
    public ArrayList<Car> searchCarByDetails(String colour) {
        return UserFileDataController.searchCarsByDetails(colour, "staff");
    }

    @Override
    public ArrayList<Car> searchCarByDetails(String model, String transmission) {
        return UserFileDataController.searchCarsByDetails(model, transmission, "staff");
    }
}
