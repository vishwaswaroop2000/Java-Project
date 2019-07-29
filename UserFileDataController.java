package com.company;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class UserFileDataController {

    private static boolean validDate(String arrivalDate, String soldDate) {
        /*This function validates the dates entered when adding a car.
        It is only called if both fields(i.e, Arrival Date and Sold Date are entered.
         */
        DateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (soldDate.equals("")) {
                Date arrived = formattedDate.parse(arrivalDate);
                String arrivedDateString = String.valueOf(arrived);
                if ((arrivedDateString.equals(arrived)))
                    return false;
            } else {
                Date arrived = formattedDate.parse(arrivalDate);
                Date sold = formattedDate.parse(soldDate);
                String arrivedDate = String.valueOf(arrived);
                String soldDateString = String.valueOf(sold);
                if ((arrivedDate.equals(arrivalDate)) ||
                        soldDateString.equals(soldDate)) {
                    return false;
                }
                return arrived.compareTo(sold) <= 0;
            }
        } catch (ParseException e) {
            return false;
        }
        return false;
    }

    private static boolean validDate(String arrivalDate) {
        /*This function is used when adding a Car, specifically when the selling Date is left blank.*/
        DateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date arrived = formattedDate.parse(arrivalDate);
            String arrivedDate = String.valueOf(arrived);
            if ((arrivedDate.equals(arrivalDate))) {
                return false;
            }
            return true;
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    private static boolean validDateForRevenue(String date) {
        /*This function is helpful in calculating revenue as it checks the entered date and verifies it*/
        DateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formattedDate.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private static boolean validNumberPlate(String numberPlate) {
        /*This function validates if numberPlate already exists when using add Cars function , and this prevents from
        adding cars to cars-database from cars-import when it already exists in the cars-database
         */
        String filepath = "cars-import.txt";
        String carDetailsLine;
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filepath));
            while ((carDetailsLine = br.readLine()) != null) {
                String[] carDetailsArr = carDetailsLine.split("\\s*,\\s*");
                if (carDetailsArr[0].equals(numberPlate))
                    return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static boolean validDetails(String numberPlate, String colour, String mileage, String price, String model) {
        /* This function is used when the user wants to Add a car. It validates the numberplate, colour and
        checks if the price and model are of integer data type.
         */
        if (numberPlate.equals("") || colour.equals("") || model.equals(""))
            return false;
        try {
            Integer.parseInt(mileage);
            Integer.parseInt(price);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static boolean numberPlateIsUnique(String numberPlate) {
        /* This function is used to validate the numberPlate when Adding a Car. If the numberplate already exists it
        returns false.
         */
        BufferedReader br;
        String carDetailsLine;
        try {
            String filepath = "cars-database.txt";
            br = new BufferedReader(new FileReader(filepath));
            while ((carDetailsLine = br.readLine()) != null) {
                String[] carDetailsArr = carDetailsLine.split("\\s*,\\s*");
                if (carDetailsArr[0].equals(numberPlate))
                    return false;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String setCarDetailsAndWrite(String numberPlateField, String modelField, String carTypeField,
                                               String vanSizeField, String colourField, String mileageField,
                                               String accidentHistoryField, String transmissionTypeBox,
                                               String priceField, String arrivalDateField,
                                               String sellingDateField) throws IOException {
        /* This function recieves all the attributes of the car, and appends the Car to the car-database.If certain
        * attributes of the car are incorrect, it points out to corresponding attributes and requests the user to
        * set the appropriate values. */
        String vanSize = "";
        String carType;
        carType = carTypeField;
        if (carType.equals("van"))
            vanSize = vanSizeField;
        String accidentHistory = accidentHistoryField;
        if (accidentHistory.equals(""))
            accidentHistory = "none";
        String soldDate = sellingDateField;
        if (soldDate.equals("")) {
            soldDate = "Not yet sold";
            if (validNumberPlate(numberPlateField)
                    && validDetails(numberPlateField, colourField, mileageField, priceField, modelField) &&
                    validDate(arrivalDateField)
                    && numberPlateIsUnique(numberPlateField)) {
                String carLine = numberPlateField + "," + modelField + "," + carType + "," + vanSize + "," +
                        colourField + "," +
                        mileageField + "," + accidentHistory + "," +
                        transmissionTypeBox + "," + priceField + "," + arrivalDateField + "," + soldDate;
                String filepath = "cars-database.txt";
                BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, true));
                bw.newLine();
                bw.append(carLine);
                bw.close();
                return "DONE!";
            } else if (!validDate(arrivalDateField))
                return "Date";
            else if (!numberPlateIsUnique(numberPlateField))
                return "Numberplate";
            else
                return "MileageAndPrice";
        } else {
            if (validNumberPlate(numberPlateField)
                    && validDetails(numberPlateField, colourField, mileageField, priceField, modelField) &&
                    validDate(arrivalDateField, soldDate)
                    && numberPlateIsUnique(numberPlateField)) {
                String carLine = numberPlateField + "," + modelField + "," + carType + "," + vanSize + "," +
                        colourField + "," +
                        mileageField + "," + accidentHistory + "," +
                        transmissionTypeBox + "," + priceField + "," + arrivalDateField + "," + soldDate;
                String filepath = "cars-database.txt";
                BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, true));
                bw.newLine();
                bw.append(carLine);
                bw.close();
                return "DONE!";
            } else if (!validDate(arrivalDateField, soldDate))
                return "Date";
            else if (!numberPlateIsUnique(numberPlateField))
                return "Numberplate";
            else
                return "MileageAndPrice";
        }
    }

    static ArrayList<List<Object>> readCarsData() {
        /*This function reads the cars-import file and adds the appropriate dates and CHECKS IF the car with numberplate
        exists. If it the numberPlate is unique, it would add the car to the car-database.
         */
        BufferedReader br = null;
        BufferedReader brDatabase = null;
        String carDetailsLine, databaseCarDetailsLine;
        ArrayList<List<Object>> carDetails = new ArrayList();
        try {
            Date date = new Date();
            SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = formattedDate.format(date);
            String filepath = "cars-import.txt";
            String databasefilepath = "cars-database.txt";

            brDatabase = new BufferedReader(new FileReader(databasefilepath));
            br = new BufferedReader(new FileReader(filepath));
            while ((carDetailsLine = br.readLine()) != null) {
                String[] carDetailsArr = carDetailsLine.split("\\s*,\\s*");
                boolean carAlreadyExists = false;
                while ((databaseCarDetailsLine = brDatabase.readLine()) != null) {
                    String[] databaseCarDetails = databaseCarDetailsLine.split("\\s*,\\s*");
                    if (databaseCarDetails[0].equals(carDetailsArr[0])) {
                        carAlreadyExists = true;
                        break;
                    }
                }
                if (!carAlreadyExists) {
                    if (carDetailsArr.length == 9) {
                        carDetailsLine = carDetailsLine + "," + currentDate + "," + "Not yet sold";
                    }
                    if (carDetailsArr.length == 10) {
                        carDetailsLine = carDetailsLine + "," + "Not yet sold";
                    }
                    carDetails.add(Collections.singletonList(carDetailsLine));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return carDetails;
    }

    public static boolean writeCarsData(ArrayList<List<Object>> carDetails, String command) {
        /*This function is used to write car Details to car-database. It also takes a String command,
        and if the command is 'append', then it would append the car to car-database,else
        rewrite the file with the data(carDetails)
         */

        BufferedWriter bw;
        try {
            String filepath = "cars-database.txt";
            if (command.equals("append")) {
                bw = new BufferedWriter(new
                        FileWriter(filepath, true));
            } else
                bw = new BufferedWriter(new FileWriter((filepath)));

            for (List<Object> carDetail : carDetails) {
                for (Object o : carDetail) {
                    bw.write(String.valueOf(o));
                }
                bw.newLine();

            }
            bw.close();
            return true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    static String sellCar(String numberPlate) {
        /*This function is used to sell Car. When the admin/staff call this function with a numberplate as a parameter
        It uses BufferedReader to scan through every single Car, and if the numberplate of the car matches the
        numberPlate, then it would set the sold Date of that car to the current Date.
         */
        BufferedReader br;
        String carDetailsLine;
        ArrayList<List<Object>> carDetails = new ArrayList();
        try {
            Date date = new Date();
            SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = formattedDate.format(date);
            String filepath = "cars-database.txt";
            br = new BufferedReader(new FileReader(filepath));
            boolean carNotFound = true, carSold = false, carAlreadySold = false;
            while ((carDetailsLine = br.readLine()) != null) {
                String[] carDetailsArr = carDetailsLine.split(",");
                if (carDetailsArr[0].equals(numberPlate) && !(carDetailsArr[10].equals("Not yet sold"))) {
                    carAlreadySold = true;
                    carNotFound = false;
                }
                if (carDetailsArr[0].equals(numberPlate) && carDetailsArr[10].equals("Not yet sold")) {
                    carDetailsArr[10] = currentDate;
                    carSold = true;
                    carNotFound = false;
                }
                for (int i = 0; i <= 9; i++) {
                    carDetailsArr[i] += ",";
                }
                carDetails.add(Arrays.asList(carDetailsArr));
            }
            if (carSold) {
                writeCarsData(carDetails, "rewrite");
                return "Car Sold!";
            }
            if (carAlreadySold)
                return "Car Already Sold.";
            if (carNotFound)
                return "No car found with the given number plate";
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error selling car.";
    }

    public static ArrayList<Car> searchCarsByDetails(int minimumSeats, int maximumSeats, String user) {
        /* Method Overloading is used here, as there exist four functions with the same name but takes different
        parameter.This function takes the minimum and maximum number of seats and performs Search cars. Uses the user
        parameter to determine whether or not to display accident history(the same is applied to the rest of the
        functions
         */
        ArrayList<String> carsList = new ArrayList();
        for (int i = minimumSeats; i <= maximumSeats; i++) {
            if (i == 2)
                carsList.add("Coupe");
            if (i == 3)
                carsList.add("Van");
            if (i == 4)
                carsList.add("Hatchback");
            if (i == 5) {
                carsList.add("Saloon");
                carsList.add("SUV");
            }
            if (i == 7)
                carsList.add("MPV");
        }
        BufferedReader br;
        String carDetailsLine;
        ArrayList<Car> carDetails = new ArrayList();
        try {
            String filepath = "cars-database.txt";
            br = new BufferedReader(new FileReader(filepath));
            while ((carDetailsLine = br.readLine()) != null) {
                String[] carDetailsArr = carDetailsLine.split("\\s*,\\s*");
                Car car = new Car(carDetailsArr[0], carDetailsArr[1], carDetailsArr[2], carDetailsArr[3], carDetailsArr[4],
                        carDetailsArr[5], carDetailsArr[6], carDetailsArr[7], carDetailsArr[8], carDetailsArr[9],
                        carDetailsArr[10]);
                if (user.equals("Customer"))
                    car.accidentHistory = "";
                car.type = car.type.toLowerCase().trim();
                for (int i = 0; i < carsList.size(); i++) {
                    if ((car.type.equals(carsList.get(i).toLowerCase())) &&
                            car.sellingDate.equals("Not yet sold")) {
                        carDetails.add(car);
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return carDetails;
    }

    static ArrayList<Car> searchCarsByDetails(Object sizeOfVan, String user) {
        /* Method Overloading is used here, as there exist four functions with the same name but takes different
        parameter. This function takes the size of uses it to search.
         */
        String vanSize = (String) sizeOfVan;
        BufferedReader br;
        ArrayList<Car> carDetails = new ArrayList();
        String carDetailsLine;
        vanSize = vanSize.trim().toLowerCase();
        try {
            String filepath = "cars-database.txt";
            br = new BufferedReader(new FileReader(filepath));
            while ((carDetailsLine = br.readLine()) != null) {
                String[] carDetailsArr = carDetailsLine.split("\\s*,\\s*");
                Car car = new Car(carDetailsArr[0], carDetailsArr[1], carDetailsArr[2], carDetailsArr[3], carDetailsArr[4],
                        carDetailsArr[5], carDetailsArr[6], carDetailsArr[7], carDetailsArr[8], carDetailsArr[9],
                        carDetailsArr[10]);
                if (user.equals("Customer"))
                    car.accidentHistory = "";
                car.vanSize = car.vanSize.toLowerCase().trim();
                if ((car.vanSize.equals(vanSize)) && car.sellingDate.equals("Not yet sold")) {
                    carDetails.add(car);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return carDetails;
    }

    static ArrayList<Car> searchCarsByDetails(String colour, String user) {
        /* Method Overloading is used here, as there exist four functions with the same name but takes different
        parameter.This function recieves colour and uses it to search Cars.
         */
        BufferedReader br;
        String carDetailsLine;
        ArrayList<Car> carDetails = new ArrayList();
        colour = colour.trim().toLowerCase();
        try {
            String filepath = "cars-database.txt";
            br = new BufferedReader(new FileReader(filepath));
            while ((carDetailsLine = br.readLine()) != null) {
                String[] carDetailsArr = carDetailsLine.split("\\s*,\\s*");
                Car car = new Car(carDetailsArr[0], carDetailsArr[1], carDetailsArr[2], carDetailsArr[3], carDetailsArr[4],
                        carDetailsArr[5], carDetailsArr[6], carDetailsArr[7], carDetailsArr[8], carDetailsArr[9],
                        carDetailsArr[10]);
                if (user.equals("Customer"))
                    car.accidentHistory = "";
                car.colour = car.colour.toLowerCase().trim();
                if ((car.colour.equals(colour)) && car.sellingDate.equals("Not yet sold")) {
                    carDetails.add(car);
                }
            }
            return carDetails;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return carDetails;
    }

    static ArrayList<Car> searchCarsByDetails(String model, String transmissionType, String user) {
        /* Method Overloading is used here, as there exist four functions with the same name but takes different
        parameter. This function uses model and trasnmission type to search Cars.
         */
        BufferedReader br;
        String carDetailsLine;
        model = model.trim().toLowerCase();
        transmissionType = transmissionType.trim().toLowerCase();
        ArrayList<Car> carDetails = new ArrayList();
        try {
            String filepath = "cars-database.txt";
            br = new BufferedReader(new FileReader(filepath));
            while ((carDetailsLine = br.readLine()) != null) {
                String[] carDetailsArr = carDetailsLine.split("\\s*,\\s*");
                Car car = new Car(carDetailsArr[0], carDetailsArr[1], carDetailsArr[2], carDetailsArr[3], carDetailsArr[4],
                        carDetailsArr[5], carDetailsArr[6], carDetailsArr[7], carDetailsArr[8], carDetailsArr[9],
                        carDetailsArr[10]);
                if (user.equals("Customer"))
                    car.accidentHistory = "";
                car.model = car.model.toLowerCase().trim();
                car.transmissionType = car.transmissionType.toLowerCase().trim();
                if ((car.model.contains(model) && car.transmissionType.equals(transmissionType)) &&
                        car.sellingDate.equals("Not yet sold")) {
                    carDetails.add(car);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return carDetails;
    }


    private static boolean validYear(String year) {
        try {
            /*This function is used by the Add Car function when the user wants
            to add a Car / calculate revenue with selling Date after the then current Date.
             */
            Date date = new Date();
            SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = formattedDate.format(date);
            String[] currentDateArr = currentDate.split("-");
            return Integer.valueOf(currentDateArr[0]) >= Integer.valueOf(year);
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean validMonth(String month) {
        try {
            /* This function is used by the Add Car/Calculate Revenue function to validate the month */
            String[] monthAndYear = month.split("-");
            if (Integer.valueOf(monthAndYear[1]) >= 1 && Integer.valueOf(monthAndYear[1]) <= 12
                    && validYear(monthAndYear[0]))
                return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    static double calculateTheRevenue(String dateOrMonth) {
        /*This function recieves the date from the user(admin/customer) and calculates the revenue in the particular
        date/Month.*/
        double revenue = 0;
        BufferedReader br = null;
        String carDetailsLine;

        try {
            String filepath = "cars-database.txt";
            br = new BufferedReader(new FileReader(filepath));
            while ((carDetailsLine = br.readLine()) != null) {
                String[] carDetailsArr = carDetailsLine.split("\\s*,\\s*");
                if (validDateForRevenue(dateOrMonth)) {
                    if (carDetailsArr[10].equals(dateOrMonth)) {
                        revenue = revenue + Double.valueOf(carDetailsArr[8]);
                    }
                } else if (validMonth(dateOrMonth)) {
                    if (dateOrMonth.equals(carDetailsArr[10].substring(0, 7))) {
                        revenue = revenue + Double.valueOf(carDetailsArr[8]);
                    }
                } else
                    return -1;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return revenue;
    }

    private static boolean writeCarDetails(ArrayList<String> carDetails, String filepath) {
        /* This function can be used to write the car Details to any file which, can is specified by the parameter
        filepath.
         */
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new
                    FileWriter(filepath));
            for (String carDetail : carDetails) {
                bw.write(carDetail);
                bw.newLine();
            }
            bw.close();
            return true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                System.err.println(e.getMessage(
                ));
                e.printStackTrace();
            }
        }
        return false;
    }

    private static ArrayList<String> sortByDate(ArrayList<String> carDetails, int datePosition) {
        /* This function is used to sort the date for print cars function. The function is recieves the datePosition
        i.e, either the selling date or the arrival date and sorts it by the corresponding date, and returns the sorted
        ArrayList.
         */
        boolean notSorted = true;
        String switchedCarDetails;
        while (notSorted) {
            notSorted = false;
            for (int i = 1; i < (carDetails.size() - 1); i++) {
                String[] carDetail = carDetails.get(i).split("\\s*,\\s*");
                String[] nextCarDetail = carDetails.get(i + 1).split("\\s*,\\s*");
                if (carDetail[datePosition].compareTo(nextCarDetail[datePosition]) > 0) {
                    switchedCarDetails = carDetails.get(i);
                    carDetails.set(i, carDetails.get(i + 1));
                    carDetails.set(i + 1, switchedCarDetails);
                    notSorted = true;
                }
            }
        }
        return carDetails;
    }

    static boolean printCars() {
        /* This function is used to print-cars onto Cars-output. Outputs a list of sold and unsold cars,
         * and sorts the sold cars according to their selling date and unsold cars according to their arrival date. */
        BufferedReader br;
        String carDetailsLine;
        ArrayList<String> soldCarDetails = new ArrayList();
        ArrayList<String> unsoldCarDetails = new ArrayList();
        soldCarDetails.add("Sold:");
        unsoldCarDetails.add("Unsold:");
        try {
            String filepath = "cars-database.txt";
            br = new BufferedReader(new FileReader(filepath));
            while ((carDetailsLine = br.readLine()) != null) {
                String[] carDetailsArr = carDetailsLine.split("\\s*,\\s*");
                if (carDetailsArr[10].equals("Not yet sold"))
                    unsoldCarDetails.add(carDetailsLine);
                else
                    soldCarDetails.add(carDetailsLine);
            }
            ArrayList<String> sortedSoldCarDetails = sortByDate(soldCarDetails, 10);
            ArrayList<String> sortedUnsoldCarDetails = sortByDate(unsoldCarDetails, 9);
            sortedSoldCarDetails.addAll(sortedUnsoldCarDetails);
            return writeCarDetails(sortedSoldCarDetails, "cars-output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
