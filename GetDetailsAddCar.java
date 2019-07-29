package com.company;

import javax.swing.*;

public class GetDetailsAddCar {
    private JTextField numberPlateField;
    private JTextField modelField;
    private JTextField colourField;
    private JTextField mileageField;
    private JTextField accidentHistoryField;
    private JTextField priceField;
    protected JTextField arrivalDateField;
    private JButton submitButton;
    protected JPanel addCarPanel;
    protected JTextField sellingDateField;
    private JComboBox vanTypeBox;
    private JComboBox transmissionTypeBox;
    private JComboBox typeComboBox;
    private JLabel Type;
    private JButton cancelButton;
    private static JFrame addCarFrame;

    private GetDetailsAddCar() {
        submitButton.addActionListener(e -> {
            Staff staff = new Staff();
            Car car = new Car(numberPlateField.getText(), modelField.getText(),
                    String.valueOf(typeComboBox.getSelectedItem()), String.valueOf(vanTypeBox.getSelectedItem()),
                    colourField.getText(), mileageField.getText(), accidentHistoryField.getText(),
                    String.valueOf(transmissionTypeBox.getSelectedItem()), priceField.getText(),
                    arrivalDateField.getText(), sellingDateField.getText());
            String status = staff.addCar(car);//Staff object is created to add Car.
            switch (status) {
                /*The String varible status is gets the appropritate message as a response to adding a car, and
                display the message*/
                case "DONE!":
                    JOptionPane.showMessageDialog(null, "Car Added!");
                    addCarFrame.dispose();
                    break;
                case "Date":
                    JOptionPane.showMessageDialog(null, "Please enter the proper date" +
                            "\n The format is yyyy-mm-dd");
                    break;
                case "Numberplate":
                    JOptionPane.showMessageDialog(null, "Number Plate already exists");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Please enter the correct values" +
                            " for Mileage,Price");
                    break;
            }
        });
        cancelButton.addActionListener(e -> addCarFrame.dispose());
    }


    static void showWindow() {
        addCarFrame = new JFrame();
        addCarFrame.setContentPane(new GetDetailsAddCar().addCarPanel);
        addCarFrame.pack();
        addCarFrame.setVisible(true);
    }
}
