package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class GetDetailsSearchCar {
    private JTextField modelField;
    private JComboBox transmissionTypeBox;
    private JComboBox minSeatsBox;
    private JComboBox maxSeatsBox;
    private JTextField colourField;
    private JButton searchCarButton;
    private JRadioButton category1Button;
    private JRadioButton category2Button;
    private JRadioButton category3Button;
    private JRadioButton category4Button;
    private JPanel searchCarPanel;
    private JComboBox vanSizeBox;
    private JTable CarTable;
    private JScrollPane carTableScrollPane;
    private JButton cancelButton;
    private static JFrame searchCarFrame;

    private GetDetailsSearchCar(String user) {
        searchCarButton.addActionListener(e -> {/* This class is used to search car.
        It gets the ATTRIBUTES of the car and performs the search. And display the result in a table.
        */
            if (user.equals("customer")) {
                Customer customer = new Customer();
                ArrayList<Car> result = new ArrayList();
                if (modelField.isEnabled()) {
                    String transmissionType = (String) transmissionTypeBox.getSelectedItem();
                    result = customer.searchCarByDetails(modelField.getText(), transmissionType);
                }
                if (colourField.isEnabled()) {
                    result = customer.searchCarByDetails(colourField.getText());
                }
                if (vanSizeBox.isEnabled()) {
                    result = customer.searchCarByDetails(vanSizeBox.getSelectedItem());
                }
                if (minSeatsBox.isEnabled()) {
                    int minimumSeats = Integer.valueOf(String.valueOf(minSeatsBox.getSelectedItem()));
                    int maximumSeats = Integer.valueOf(String.valueOf(maxSeatsBox.getSelectedItem()));
                    result = customer.searchCarByDetails(minimumSeats, maximumSeats);
                }
                if (!result.isEmpty()) {
                    String[] heading = {"Number Plate", "Model", "Type", "Size of Van", "Colour", "mileage",
                            "Tranmission Type", "Price", "Arrival Date"};
                    DefaultTableModel model = new DefaultTableModel(heading, 0);
                    //model.setColumnIdentifiers(heading);
                    for (Car car : result) {
                        model.addRow(new Object[]{
                                car.numberPlate, car.model, car.type, car.vanSize, car.colour, car.mileage,
                                car.transmissionType, car.price, car.arrivalDate});
                    }
                    CarTable.setModel(model);
                } else {
                    JOptionPane.showMessageDialog(null, "No car Found with given Data");
                }
            } else {
                Staff staff = new Staff();
                ArrayList<Car> result = new ArrayList();
                if (modelField.isEnabled()) {
                    String transmissionType = (String) transmissionTypeBox.getSelectedItem();
                    result = staff.searchCarByDetails(modelField.getText(), transmissionType);
                }
                if (colourField.isEnabled()) {
                    result = staff.searchCarByDetails(colourField.getText());
                }
                if (vanSizeBox.isEnabled()) {
                    result = staff.searchCarByDetails(vanSizeBox.getSelectedItem());
                }
                if (minSeatsBox.isEnabled()) {
                    int minimumSeats = Integer.valueOf(String.valueOf(minSeatsBox.getSelectedItem()));
                    int maximumSeats = Integer.valueOf(String.valueOf(maxSeatsBox.getSelectedItem()));
                    result = staff.searchCarByDetails(minimumSeats, maximumSeats);
                }
                if (!result.isEmpty()) {
                    String[] heading = {"Number Plate", "Model", "Type", "Size of Van", "Colour", "mileage", "Accident History",
                            "Tranmission Type", "Price", "Arrival Date"};
                    DefaultTableModel model = new DefaultTableModel(heading, 0);
                    //model.setColumnIdentifiers(heading);
                    for (Car car : result) {
                        model.addRow(new Object[]{
                                car.numberPlate, car.model, car.type, car.vanSize, car.colour, car.mileage,
                                car.accidentHistory, car.transmissionType, car.price, car.arrivalDate});
                    }
                    CarTable.setModel(model);
                } else {
                    JOptionPane.showMessageDialog(null, "No car Found with given Data");
                }
            }

        });

        category1Button.addActionListener(e -> {
            modelField.setEnabled(true);
            transmissionTypeBox.setEnabled(true);
            minSeatsBox.setEnabled(false);
            maxSeatsBox.setEnabled(false);
            colourField.setEnabled(false);
            vanSizeBox.setEnabled(false);
        });

        category2Button.addActionListener(e -> {
            modelField.setEnabled(false);
            transmissionTypeBox.setEnabled(false);
            minSeatsBox.setEnabled(true);
            maxSeatsBox.setEnabled(true);
            colourField.setEnabled(false);
            vanSizeBox.setEnabled(false);
        });

        category3Button.addActionListener(e -> {
            modelField.setEnabled(false);
            transmissionTypeBox.setEnabled(false);
            minSeatsBox.setEnabled(false);
            maxSeatsBox.setEnabled(false);
            colourField.setEnabled(true);
            vanSizeBox.setEnabled(false);
        });

        category4Button.addActionListener(e -> {
            modelField.setEnabled(false);
            transmissionTypeBox.setEnabled(false);
            minSeatsBox.setEnabled(false);
            maxSeatsBox.setEnabled(false);
            colourField.setEnabled(false);
            vanSizeBox.setEnabled(true);
        });
        cancelButton.addActionListener(e -> searchCarFrame.dispose());
    }


    static void showWindow(String user) {
        searchCarFrame = new JFrame();
        searchCarFrame.setContentPane(new GetDetailsSearchCar(user).searchCarPanel);
        searchCarFrame.pack();
        searchCarFrame.setVisible(true);
    }
}
