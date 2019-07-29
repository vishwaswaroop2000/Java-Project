package com.company;

import javax.swing.*;

public class GetDetailsSellCar {
    private JButton sellCarButton;
    private JTextField sellCarField;
    private JPanel sellCarPanel;
    private JButton cancelButton;
    private static JFrame sellCarFrame;

    private GetDetailsSellCar() {
        /*This class is used to get the numberPlate of the car to be Sold.*/
        sellCarButton.addActionListener(e -> {
            String numberPlate = sellCarField.getText();
            Staff staff = new Staff();
            String message = staff.sellCar(numberPlate);
            JOptionPane.showMessageDialog(null, message);
            if (message.equals("Car Sold!"))
                sellCarFrame.dispose();
        });
        cancelButton.addActionListener(e -> sellCarFrame.dispose());
    }


    static void showWindow() {
        sellCarFrame = new JFrame();
        sellCarFrame.setContentPane(new GetDetailsSellCar().sellCarPanel);
        sellCarFrame.pack();
        sellCarFrame.setVisible(true);
    }

}
