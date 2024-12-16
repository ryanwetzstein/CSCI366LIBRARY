package pkg366libraryapp;

import java.sql.SQLException;
import java.util.Scanner;

public class StartingPoint {

    /**
     * Logged in variable to track logged in user. 0 - not logged in 1 - logged
     * in 2 - special case
     *
     */
    public static int loggedIn = 0;
    Customer loggedInUser = null;

    public void start() {

        if (loggedIn == 0) {
            manageAuthCustomer();
        }

        String anotherRequest = "yes";

        while (loggedIn != 2 && anotherRequest.equalsIgnoreCase("yes")) {

            Scanner scanChoice = new Scanner(System.in);

            System.out.println("\nWhat would you like to do next?"
                    + "\n Enter 'a' to manage Books. "
                    + "\n Enter 'b' to manage your account. "
                    + "\n Enter 'c' to view admin options. ");
            String choice = scanChoice.next();

            switch (choice) {
                case "a" ->
                    this.manageBooks();
                case "b" ->
                    this.manageCustomer();
                case "c" ->
                    this.adminOptions();
                default ->
                    System.out.println("Invalid operation.");
            }

            System.out.println("Another request? (Yes/No) ");
            Scanner scanRequest = new Scanner(System.in);
            anotherRequest = scanRequest.next();
        }
    }

    private void manageAuthCustomer() {
        String choice = "";

        while (!choice.equals("q") && loggedIn == 0) {

            Scanner scanChoice = new Scanner(System.in);

            System.out.println("\nWelcome to our Library."
                    + "\n Enter 'a' to log in."
                    + "\n Enter 'b' to register."
                    + "\n Enter 'q' to Quit.");

            choice = scanChoice.next();

            switch (choice) {
                case "a" -> {
                    loggedInUser = Customer.login();
                    if (loggedInUser != null) {
                        loggedIn = 1;
                    }
                }
                case "b" -> {
                    Customer.register();
                    System.out.println("Now you can Login.");
                    loggedInUser = Customer.login();
                    if (loggedInUser != null) {
                        loggedIn = 1;
                    }
                }
                case "q" -> {
                    System.out.println("Have a nice day!");
                    loggedIn = 2;
                }
                default ->
                    System.out.println("Invalid operation.");
            }
        }
    }

    private void adminOptions() {
        if (!loggedInUser.getIsAdmin()) {
            System.out.println("You don't have permission to access this.");
        } else {
            String choice = "";

            while (!choice.equals("q")) {

                Scanner scanChoice = new Scanner(System.in);

                System.out.println("\nWelcome to the Admin menu."
                        + "\n Enter 'a' to list all users."
                        + "\n Enter 'b' to list users by first_name."
                        + "\n Enter 'c' to list users by last_name."
                        + "\n Enter 'd' to list all users with their addresses."
                        + "\n Enter 'e' to create a user."
                        + "\n Enter 'f' to update a user."
                        + "\n Enter 'g' to delete a user."
                        + "\n Enter 'q' to Quit.");

                choice = scanChoice.next();

                switch (choice) {
                    case "a" -> {
                        try {
                            Customer.ListCustomer(true);
                        } catch (SQLException e) {
                            System.out.println("Got a SQL exception.");
                            e.printStackTrace();
                        }
                    }
                    case "b" -> {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter an first name: ");
                        try {
                            Customer.ListCustomer("first_name", scan.next(), true);
                        } catch (SQLException e) {
                            System.out.println("Got a SQL exception.");
                            e.printStackTrace();
                        }
                    }
                    case "c" -> {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter an last name: ");
                        try {
                            Customer.ListCustomer("last_name", scan.next(), true);
                        } catch (SQLException e) {
                            System.out.println("Got a SQL exception.");
                            e.printStackTrace();
                        }
                    }
                    case "d" -> {
                        try {
                            System.out.println("Enter an last name: ");
                            Customer.ListCustomerWithAddress(true);
                        } catch (SQLException e) {
                            System.out.println("Got a SQL exception.");
                            e.printStackTrace();
                        }
                    }
                    case "e" -> {
                        Customer.register();
                    }
                    case "f" -> {
                        updateAccount(false);
                    }
                    case "g" -> {
                        try {
                            Scanner scan = new Scanner(System.in);
                            System.out.println("Enter a user ID: ");
                            Customer.removeCustomer(scan.nextInt());
                        } catch (SQLException e) {
                            System.out.println("Got a SQL exception.");
                            e.printStackTrace();
                        }
                    }
                    case "q" ->
                        System.out.println("\nExiting admin menu.\n");
                    default ->
                        System.out.println("Invalid operation.");
                }
            }
        }
    }

    private void manageCustomer() {
        String choice = "";

        while (!choice.equals("q")) {

            Scanner scanChoice = new Scanner(System.in);

            System.out.println("\nWelcome to the Account menu."
                    + "\n Enter 'a' to view your account."
                    + "\n Enter 'b' to update your account."
                    + "\n Enter 'c' to delete your account."
                    + "\n Enter 'd' to view your address."
                    + "\n Enter 'e' to update your address."
                    + "\n Enter 'q' to Quit.");

            choice = scanChoice.next();

            switch (choice) {
                case "a" -> {
                    try {
                        Customer.ListCustomer("customer_id", loggedInUser.getCustomerID(), true);
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "b" -> {
                    this.updateAccount(true);
                }
                case "c" -> {
                    try {
                        Customer.removeCustomer(loggedInUser.getCustomerID());
                        System.out.println("Account successfully deleted.");
                        loggedIn = 0;
                        loggedInUser = null;
                        start();
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "d" -> {
                    try {
                        Address.ListAddresses("address_id", loggedInUser.getAddressID(), true);
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "e" -> {
                    this.updateAddress();
                }
                case "q" ->
                    System.out.println("\nExiting account manager.\n");
                default ->
                    System.out.println("Invalid operation.");
            }
        }
    }

    private void updateAccount(boolean self) {
        String choice = "";

        while (!choice.equals("q")) {

            int ID;
            if(self){
                ID = loggedInUser.getCustomerID();
            } else {
                Scanner scan = new Scanner(System.in);
                System.out.println("Enter the User ID of the User you are updating.");
                ID = scan.nextInt();
            }
            
            Scanner scanChoice = new Scanner(System.in);

            System.out.println("\nWhat would you like to update?."
                    + "\n Enter 'a' to update email."
                    + "\n Enter 'b' to update your password."
                    + "\n Enter 'c' to update first_name."
                    + "\n Enter 'd' to update last_name."
                    + "\n Enter 'e' to update phone_num."
                    + "\n Enter 'f' to update date of birth."
                    + "\n Enter 'q' to Quit.");

            choice = scanChoice.next();           
            
            switch (choice) {
                case "a" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new email: ");
                        String s = scan.next();
                        if (Customer.validateEmail(s)) {
                            Customer.updateCustomer(ID, "email", s);
                            System.out.println("Update Successful.");
                        } else {
                            System.out.println("Invalid Email.");
                        }

                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "b" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new password: ");
                        Customer.updateCustomer(ID, "password", scan.next());
                        System.out.println("Update Successful.");
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "c" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new first_name: ");
                        Customer.updateCustomer(ID, "first_name", scan.next());
                        System.out.println("Update Successful.");
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "d" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new last_name: ");
                        Customer.updateCustomer(ID, "last_name", scan.next());
                        System.out.println("Update Successful.");
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "e" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new phone number: ");
                        String s = scan.next();
                        if (Customer.validatePhoneNum(s)) {
                            Customer.updateCustomer(ID, "phone_num", s);
                            System.out.println("Update Successful.");
                        } else {
                            System.out.println("Invalid Email.");
                        }

                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "f" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new date of birth: ");
                        String s = scan.next();
                        if (Customer.validateDOB(s)) {
                            Customer.updateCustomer(ID, "DOB", s);
                            System.out.println("Update Successful.");
                        } else {
                            System.out.println("Invalid Email.");
                        }

                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "q" ->
                    System.out.println("\nExiting account manager.\n");
                default ->
                    System.out.println("Invalid operation.");
            }
        }
    }

    private void updateAddress() {
        String choice = "";

        while (!choice.equals("q")) {

            Scanner scanChoice = new Scanner(System.in);

            System.out.println("\nWhat would you like to update?."
                    + "\n Enter 'a' to update street."
                    + "\n Enter 'b' to update city."
                    + "\n Enter 'c' to update zipcode."
                    + "\n Enter 'd' to update state."
                    + "\n Enter 'e' to update country."
                    + "\n Enter 'q' to Quit.");

            choice = scanChoice.next();

            switch (choice) {
                case "a" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new street: ");
                        Address.updateAddress(loggedInUser.getAddressID(), "street", scan.next());
                        System.out.println("Update Successful.");
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "b" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new city: ");
                        Address.updateAddress(loggedInUser.getAddressID(), "city", scan.next());
                        System.out.println("Update Successful.");
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "c" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new zipcode: ");
                        Address.updateAddress(loggedInUser.getAddressID(), "zipcode", scan.next());
                        System.out.println("Update Successful.");
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "d" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new state: ");
                        Address.updateAddress(loggedInUser.getAddressID(), "state", scan.next());
                        System.out.println("Update Successful.");
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "e" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new country: ");
                        Address.updateAddress(loggedInUser.getAddressID(), "country", scan.next());
                        System.out.println("Update Successful.");
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "q" ->
                    System.out.println("\nExiting account manager.\n");
                default ->
                    System.out.println("Invalid operation.");
            }
        }
    }

    private void manageBooks() {

    }
}
