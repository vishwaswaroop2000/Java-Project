/* Cardealer Java Project
Created by Vishwaswaroop P Bennur
Dated 12/05/2019
 */

package com.company;

import javax.swing.*;
import java.io.*;

public class UserControl {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton nextButton;
    private JPanel loginPanel;
    private static JFrame Loginframe;

    private UserControl() {
        nextButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String user = validate(username, password);
            if (user.equals("0")) //If incorrect details are entered(Decided by validate function)
                JOptionPane.showMessageDialog(null, "Please enter " +
                        "the correct Details");
            else
            {
                //Switches role depending on the username and password
                if (user.equals("staff")) {
                    Staff.showWindow();
                    Loginframe.dispose();
                }
                if(user.equals("admin")) {
                    admin.showWindow();
                    Loginframe.dispose();
                }
                if(user.equals("customer")) {
                    Customer.showWindow();
                    Loginframe.dispose();
                }
                usernameField.setText("");
                passwordField.setText("");
            }
        });
    }

    private static boolean usernameAlreadyExists(String username) {
        /*This function is used when a new Username is created.
        It checks whether the new Username is unique.
         */
        BufferedReader br;
        String usersDetailsLine;
        try {
            String filepath = "cars-users.txt";
            br = new BufferedReader(new FileReader(filepath));
            while ((usersDetailsLine = br.readLine()) != null) {
                String[] userDetails = usersDetailsLine.split("\\s*,\\s*");
                if ((userDetails[0].toLowerCase()).equals(username.toLowerCase()))
                    return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean passwordIsWierd(String password) {
        /*Some characters are not accepted as passwords in this program. Therefore
        on creating the new user, it also checks if the password contains any of the below characters
         */

        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) == ',' || password.charAt(i) == '+' || password.charAt(i) == '-'
                    || password.charAt(i) == '[' || password.charAt(i) == ']' || password.charAt(i) == '\\'
                    || password.charAt(i) == '~')
                return true;
        }
        return false;
    }
    static boolean createCredentials(String username, String password, String role) {
        /*This function is used by admin to create staff. On recieving username,password and role, this function writes
        the data on the car-users.txt
         */
        if (usernameAlreadyExists(username)||passwordIsWierd(password) || username.equals("") || password.equals(""))
            return false;
        try {
            String filepath = "cars-users.txt";
            BufferedWriter bw = new BufferedWriter(new
                    FileWriter(filepath,true));
            bw.newLine();
            bw.write(username + "," + encode(password) + "," + role);
            bw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String validate(String username, String password) {
        /*This function validates if the entered username and password are returns the role */
        BufferedReader br;
        String usersDetailsLine;
        try {
            String filepath = "cars-users.txt";
            br = new BufferedReader(new FileReader(filepath));
            while ((usersDetailsLine = br.readLine()) != null) {
                String[] userDetails = usersDetailsLine.split("\\s*,\\s*");
                if (userDetails[0].toLowerCase().equals(username.toLowerCase()) &&
                        password.equals(decode(userDetails[1])))
                    return userDetails[2];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "0";
    }

    private static String decode(String password) {
        /* This function decodes the password by using ascii values */
        int ascii;
        StringBuilder decodedPassword = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            ascii = password.charAt(i);
            if (ascii == 0) {
                ascii = 127;
            } else
                ascii--;
            decodedPassword.append((char) ascii);
        }
        return decodedPassword.toString();
    }

    private static String encode(String password) {
        /*This function encodes the password by using ascii values */
        int ascii;
        StringBuilder encodedPassword = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            ascii = password.charAt(i);
            if (ascii == 127) {
                ascii = 0;
            } else
                ascii++;
            encodedPassword.append((char) ascii);
        }
        return encodedPassword.toString();
    }


    static void showWindow(){//Displays the Login window
        Loginframe = new JFrame();
        Loginframe.setContentPane(new UserControl().loginPanel);
        Loginframe.pack();
        Loginframe.setVisible(true);
    }
    public static void main(String[] args) {
        showWindow();
    }
}