package com.company;

import javax.swing.*;

public class GetDetailsCalculateRevenue {
    private JPanel RevenuePanel;
    private JTextField revenueDateField;
    private JButton calculateRevenueButton;
    private JButton cancelButton;
    private static JFrame revenueFrame;

    private GetDetailsCalculateRevenue() {
        calculateRevenueButton.addActionListener(e -> {
            Staff staff = new Staff();
            String dateOrMonth = revenueDateField.getText();//Used to the date entered in the text box
            double revenue = staff.calculateRevenue(dateOrMonth);
            //Displays revenue if its greater than or equal to zero. If the function calculate Revenue returns its
            //default value of -1 it displays the error message.
            if (revenue >= 0) {
                JOptionPane.showMessageDialog(null, "The revenue was found to be £" + revenue);
                revenueFrame.dispose();
            } else
                JOptionPane.showMessageDialog(null, "Please enter the proper dates");
        });
        cancelButton.addActionListener(e -> revenueFrame.dispose());
    }

    static void showWindow() {
        revenueFrame = new JFrame();
        revenueFrame.setContentPane(new GetDetailsCalculateRevenue().RevenuePanel);
        revenueFrame.pack();
        revenueFrame.setVisible(true);
    }

}
