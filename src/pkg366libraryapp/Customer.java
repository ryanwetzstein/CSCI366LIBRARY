package pkg366libraryapp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Daniel Putnam
 */
public class Customer {

    private int customer_id;
    private String email;
    private String password;
    private String first_name;
    private String last_name;
    private String phone_num;
    private String DOB;
    private int address_id;
    private String creation_date;
    private boolean isAdmin;

    //Full Constructor
    public Customer(int customer_id, String email, String password, String first_name, String last_name, String phone_num, String DOB, int address_id, String creation_date, boolean isAdmin) {
        this.customer_id = customer_id;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_num = phone_num;
        this.DOB = DOB;
        this.address_id = address_id;
        this.creation_date = creation_date;
        this.isAdmin = isAdmin;
    }

    //Constructor without auto-generated stuff
    public Customer(String email, String password, String first_name, String last_name, String phone_num, String DOB, int address_id, boolean isAdmin) {
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_num = phone_num;
        this.DOB = DOB;
        this.address_id = address_id;
        this.isAdmin = isAdmin;
    }

    public static int insert(Customer c) throws SQLException {
        String query = "INSERT INTO Customer (email, password, first_name, last_name, phone_num, DOB, address_id, isAdmin) VALUES (?, ?, ?, ?, ?, CAST(? AS DATE), ?, ?)";

        PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(query);

        pstmt.setString(1, c.email);
        pstmt.setString(2, c.password);
        pstmt.setString(3, c.first_name);
        pstmt.setString(4, c.last_name);
        pstmt.setString(5, c.phone_num);
        pstmt.setString(6, c.DOB);
        pstmt.setInt(7, c.address_id);
        pstmt.setBoolean(8, c.isAdmin);

        int count = pstmt.executeUpdate();

        return count;
    }

    public static int updateCustomer(int cID, String column, String change) throws SQLException {
        String query = "UPDATE Customer SET " + column + " = ? WHERE customer_id = ?";

        PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(query);
        pstmt.setString(1, change);
        pstmt.setInt(2, cID);

        int count = pstmt.executeUpdate();

        return count;
    }
    
    public static int updateCustomerDate(int cID, String column, String change) throws SQLException {
        String query = "UPDATE Customer SET " + column + " = CAST (? AS DATE) WHERE customer_id = ?";

        PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(query);
        pstmt.setString(1, change);
        pstmt.setInt(2, cID);

        int count = pstmt.executeUpdate();

        return count;
    }


    public static int removeCustomer(int cID) throws SQLException {
        String query = "DELETE FROM Customer WHERE customer_id = ?";

        PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(query);
        pstmt.setInt(1, cID);

        int count = pstmt.executeUpdate();

        return count;
    }

    public static ArrayList<Customer> ListCustomer(boolean print) throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customer ORDER By customer_id asc";

        PreparedStatement pstmtSelect = DatabaseManager.getConnection().prepareStatement(query);

        ResultSet rs = pstmtSelect.executeQuery();

        while (rs.next()) {
            int customer_id = rs.getInt("customer_id");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String first_name = rs.getString("first_name");
            String last_name = rs.getString("last_name");
            String phone_num = rs.getString("phone_num");
            String DOB = rs.getString("DOB");
            int address_ID = rs.getInt("address_ID");
            String creation_date = rs.getString("creation_date");
            boolean isAdmin = rs.getBoolean("isAdmin");

            customers.add(new Customer(customer_id, email, password, first_name, last_name, phone_num, DOB, address_ID, creation_date, isAdmin));
            if (print) {
                System.out.println("\nID: " + customer_id
                        + "\nEmail: " + email
                        + "\nPassword: " + password
                        + "\nFirst_Name: " + first_name
                        + "\nLast_Name: " + last_name
                        + "\nPhone_Num: " + phone_num
                        + "\nDOB: " + DOB
                        + "\nAddressID: " + address_ID
                        + "\nCreation_Date: " + creation_date
                        + "\nisAdmin: " + isAdmin);
            }
        }
        return customers;
    }

    //string condition
    public static ArrayList<Customer> ListCustomer(String column, String condition, boolean print) throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customer WHERE " + column + " = ? ORDER By customer_id asc";

        PreparedStatement pstmtSelect = DatabaseManager.getConnection().prepareStatement(query);
        pstmtSelect.setString(1, condition);

        ResultSet rs = pstmtSelect.executeQuery();

        while (rs.next()) {
            int customer_id = rs.getInt("customer_id");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String first_name = rs.getString("first_name");
            String last_name = rs.getString("last_name");
            String phone_num = rs.getString("phone_num");
            String DOB = rs.getString("DOB");
            int address_ID = rs.getInt("address_ID");
            String creation_date = rs.getString("creation_date");
            boolean isAdmin = rs.getBoolean("isAdmin");

            customers.add(new Customer(customer_id, email, password, first_name, last_name, phone_num, DOB, address_ID, creation_date, isAdmin));
            if (print) {
                System.out.println("\nID: " + customer_id
                        + "\nEmail: " + email
                        + "\nPassword: " + password
                        + "\nFirst_Name: " + first_name
                        + "\nLast_Name: " + last_name
                        + "\nPhone_Num: " + phone_num
                        + "\nDOB: " + DOB
                        + "\nAddressID: " + address_ID
                        + "\nCreation_Date: " + creation_date
                        + "\nisAdmin: " + isAdmin);
            }
        }
        return customers;
    }

    //int condition
    public static ArrayList<Customer> ListCustomer(String column, int condition, boolean print) throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customer WHERE " + column + " = ? ORDER By customer_id asc";

        PreparedStatement pstmtSelect = DatabaseManager.getConnection().prepareStatement(query);
        pstmtSelect.setInt(1, condition);

        ResultSet rs = pstmtSelect.executeQuery();

        while (rs.next()) {
            int customer_id = rs.getInt("customer_id");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String first_name = rs.getString("first_name");
            String last_name = rs.getString("last_name");
            String phone_num = rs.getString("phone_num");
            String DOB = rs.getString("DOB");
            int address_ID = rs.getInt("address_ID");
            String creation_date = rs.getString("creation_date");
            boolean isAdmin = rs.getBoolean("isAdmin");

            customers.add(new Customer(customer_id, email, password, first_name, last_name, phone_num, DOB, address_ID, creation_date, isAdmin));
            if (print) {
                System.out.println("\nID: " + customer_id
                        + "\nEmail: " + email
                        + "\nPassword: " + password
                        + "\nFirst_Name: " + first_name
                        + "\nLast_Name: " + last_name
                        + "\nPhone_Num: " + phone_num
                        + "\nDOB: " + DOB
                        + "\nAddressID: " + address_ID
                        + "\nCreation_Date: " + creation_date
                        + "\nisAdmin: " + isAdmin);
            }
        }
        return customers;
    }

    public static ArrayList<Customer> ListCustomerWithAddress(boolean print) throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customer JOIN address ON customer.address_id = address.address_id ORDER By customer_id asc";

        PreparedStatement pstmtSelect = DatabaseManager.getConnection().prepareStatement(query);

        ResultSet rs = pstmtSelect.executeQuery();

        while (rs.next()) {
            int customer_id = rs.getInt("customer_id");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String first_name = rs.getString("first_name");
            String last_name = rs.getString("last_name");
            String phone_num = rs.getString("phone_num");
            String DOB = rs.getString("DOB");
            int address_ID = rs.getInt("address_ID");
            String creation_date = rs.getString("creation_date");
            boolean isAdmin = rs.getBoolean("isAdmin");
            String street = rs.getString("street");
            String city = rs.getString("city");
            String zipcode = rs.getString("zipcode");
            String state = rs.getString("state");
            String country = rs.getString("country");

            customers.add(new Customer(customer_id, email, password, first_name, last_name, phone_num, DOB, address_ID, creation_date, isAdmin));
            if (print) {
                System.out.println("\nID: " + customer_id
                        + "\nEmail: " + email
                        + "\nPassword: " + password
                        + "\nFirst_Name: " + first_name
                        + "\nLast_Name: " + last_name
                        + "\nPhone_Num: " + phone_num
                        + "\nDOB: " + DOB
                        + "\nCreation_Date: " + creation_date
                        + "\nisAdmin: " + isAdmin
                        + "\nStreet: " + street
                        + "\nCity: " + city
                        + "\nState: " + state
                        + "\nZipCode: " + zipcode
                        + "\nCountry " + country);
            }
        }
        return customers;
    }

    public static Customer login() {

        boolean loggedIn = false;
        Scanner scan = new Scanner(System.in);

        while (!loggedIn) {

            try {
                System.out.println("\nEnter your Email or press 'q' to quit: ");
                String s = scan.next();

                if (s.equals("q")) {
                    return null;
                }

                ArrayList<Customer> cust;

                cust = ListCustomer("email", s, false);

                System.out.println("\nEnter your password: ");
                s = scan.next();

                if (!cust.isEmpty() && cust.get(0).password.equals(s)) {
                    System.out.println("\nLog In Successful.");
                    return cust.get(0);
                } else {
                    System.out.println("\nInvalid Login, Try Again.");
                }
            } catch (SQLException e) {
                System.out.println("Got a SQL exception.");
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void register() {

        String email = null;
        String password;
        String first_name;
        String last_name;
        String phone_num = null;
        String DOB = null;
        int address_id;
        boolean isAdmin = false;

        Scanner scan = new Scanner(System.in);
        boolean validated = false;

        while (!validated) {
            System.out.println("Enter the users email: ");
            email = scan.next();
            
            validated = validateEmail(email);
        }

        System.out.println("Enter the users Password: ");
        password = scan.next();

        System.out.println("Enter the users first name: ");
        first_name = scan.next();

        System.out.println("Enter the users last name: ");
        last_name = scan.next();

        validated = false;
        while (!validated) {

            System.out.println("Enter the users phone number (ex. 2129304923): ");
            phone_num = scan.next();

            validated = validatePhoneNum(phone_num);
        }

        validated = false;
        while (!validated) {
            System.out.println("Enter the users date of birth (YYYY-MM-DD: ");
            DOB = scan.next();

            validated = validateDOB(DOB);
        }

        address_id = Address.registerAddress();

        validated = false;
        while (!validated) {
            System.out.println("Is this user an Admin? (Yes/No): ");
            String s = scan.next();
            switch (s.toLowerCase()) {
                case "yes" -> {
                    isAdmin = true;
                    validated = true;
                }
                case "no" -> {
                    isAdmin = false;
                    validated = true;
                }
                default ->
                    System.out.println("Invalid operation.");
            }
        }

        try {
            insert(new Customer(email, password, first_name, last_name, phone_num, DOB, address_id, isAdmin));
            System.out.println("Account created successfully.");

        } catch (SQLException e) {
            System.out.println("Got a SQL exception.");
            e.printStackTrace();
        }

    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public int getCustomerID() {
        return customer_id;
    }

    public int getAddressID() {
        return address_id;
    }

    public static boolean validatePhoneNum(String input) {
        if (input.length() != 10) {
            System.out.println("Invalid phone number");
            return false;
        } else {
            for (int i = 0; i < input.length(); i++) {
                if (!Character.isDigit(input.charAt(i))) {
                    System.out.println("Invalid phone number");
                    return false;
                }
            }
            return true;
        }
    }

    public static boolean validateEmail(String input) {
        if (!input.contains("@")) {
                System.out.println("Invalid Email");
                return false;
            } else {
                return true;
            }
    }

    public static boolean validateDOB(String input) {
        if ((input.length() != 10) || (input.charAt(4) != '-' || input.charAt(7) != '-')) {
            System.out.println("Invalid date of birth.");
            return false;
        } else {
            try {
                LocalDate.parse(input);
            } catch (Exception e) {
                System.out.println("Invalid date of birth.");
                return false;
            }
            return true;
        }
    }
}
