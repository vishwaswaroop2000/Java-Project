package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetDetailsCreateCredentials {
    private JTextField usernameField;
    private JTextField passwordField;
    private JComboBox roleComboBox;
    private JPanel credentialsPanel;
    private JButton createCredentialsButton;
    private JButton cancelButton;
    private static JFrame credentialsFrame;

    private GetDetailsCreateCredentials() {
        createCredentialsButton.addActionListener(e -> {/* This class is mainly is used to get Details to create
        crendentials.*/
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = (String) roleComboBox.getSelectedItem();
            admin Admin = new admin();
            boolean credentialsAreCreated = Admin.createCredentials(username, password, role);
            if (credentialsAreCreated) {
                JOptionPane.showMessageDialog(null, "Role Set!");
                credentialsFrame.dispose();
            } else
                JOptionPane.showMessageDialog(null, "Please Check Credentials! \n" +
                        "Hint: Given username may already exist, " +
                        "or Password may contain character that arent allowed are: " +
                        "+   ,  -  [  ]  ~  \\");
        });
        cancelButton.addActionListener(e -> credentialsFrame.dispose());
    }

    static void showDisplay() {
        credentialsFrame = new JFrame();
        credentialsFrame.setContentPane(new GetDetailsCreateCredentials().credentialsPanel);
        credentialsFrame.pack();
        credentialsFrame.setVisible(true);
    }

}
