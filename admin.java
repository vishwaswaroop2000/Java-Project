package com.company;

import javax.swing.*;

public class admin extends Staff implements Users {
    private JButton createUserButton;
    private JButton addCarButton;
    private JButton addCarsButton;
    private JButton sellCarButton;
    private JButton printCarsButton;
    private JButton calculateRevenueButton;
    private JButton searchCarsButton;
    private JPanel adminPanel;
    private JButton logoutButton;
    private static JFrame adminFrame;

    public admin() {//Onclicking the buttons of adminFrame, the admin can perform his functions.
        createUserButton.addActionListener(e -> GetDetailsCreateCredentials.showDisplay());
        addCarsButton.addActionListener(e -> {
            boolean carsAddedToCarsDatabase = this.addCars();
            if (carsAddedToCarsDatabase)
                JOptionPane.showMessageDialog(null, "Cars added to cars-database.txt");
            else
                JOptionPane.showMessageDialog(null, "Error adding cars to car-database");
        });
        addCarButton.addActionListener(e -> GetDetailsAddCar.showWindow());
        sellCarButton.addActionListener(e -> GetDetailsSellCar.showWindow());
        searchCarsButton.addActionListener(e -> GetDetailsSearchCar.showWindow("staff"));
        calculateRevenueButton.addActionListener(e -> GetDetailsCalculateRevenue.showWindow());
        printCarsButton.addActionListener(e -> {
            boolean carsDisplayed = this.printCars();
            if (carsDisplayed)
                JOptionPane.showMessageDialog(null, "Cars Outputted to cars-output.txt");
            else
                JOptionPane.showMessageDialog(null, "Error in outputting cars");
        });
        logoutButton.addActionListener(e -> {
            adminFrame.dispose();
            UserControl.showWindow();
        });
    }

    static void showWindow() {
        adminFrame = new JFrame();
        adminFrame.setContentPane(new admin().adminPanel);
        adminFrame.pack();
        adminFrame.setVisible(true);
    }

    /*Since the admin  extends to the staff, Only the function that is unique to the admin is present here(i.e.,
    *  createCredentials function
    */
    boolean createCredentials(String username, String password, String role) {
        return UserControl.createCredentials(username, password, role);
    }

}
