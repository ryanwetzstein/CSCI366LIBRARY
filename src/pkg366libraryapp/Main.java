package pkg366libraryapp;

import java.sql.*;
import java.util.Scanner;
import java.sql.SQLException;

/**
 *
 * @author group
 */
public class Main {

    public class Department {

        private int dept_number;
        private String dept_name;

        public Department() {
        }

        public Department(String dept_name) {
            this.dept_name = dept_name;
        }

        public Department(int dept_number, String dept_name) {
            this.dept_number = dept_number;
            this.dept_name = dept_name;
        }

        public static Department getDepartmentByName(String deptname) throws SQLException {

            String select = "SELECT * FROM department "
                    + " where department_name = ?";

            PreparedStatement stmtSelect = DatabaseManager.getConnection().prepareStatement(select);
            stmtSelect.setString(1, deptname);
            ResultSet rsOne = stmtSelect.executeQuery();

            Department dept = null;
            while (rsOne.next()) {
                dept = new Department(rsOne.getInt("department_num"),
                        rsOne.getString("department_name"));
            }

            return dept;
        }

        public int insert() throws SQLException {
            String insertDept = "INSERT INTO department (department_name) "
                    + "values (?)";

            PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(insertDept);
            stmt.setString(1, this.getDept_name());

            int count = stmt.executeUpdate();

            return count;
        }

        /**
         * @return the dept_number
         */
        public int getDept_number() {
            return dept_number;
        }

        /**
         * @param dept_number the dept_number to set
         */
        public void setDept_number(int dept_number) {
            this.dept_number = dept_number;
        }

        /**
         * @return the dept_name
         */
        public String getDept_name() {
            return dept_name;
        }

        /**
         * @param dept_name the dept_name to set
         */
        public void setDept_name(String dept_name) {
            this.dept_name = dept_name;
        }
    }

    /**
     */
    //public static void main(String[] args) {
//        System.out.println("test");
//        System.out.println("penis");
//        System.out.println("big dawg");
//        System.out.println("nashat was here");
//    }
    public class CSCI366 {

        public void start() {
            String anotherRequest = "yes";

            while (anotherRequest.equalsIgnoreCase("yes")) {

                Scanner scanChoice = new Scanner(System.in);
                System.out.println("Enter 1 to manage department, "
                        + "2 to manage employee");
                int choice = scanChoice.nextInt();

                switch (choice) {
                    case 1 ->
                        this.manageDepartment();
                    case 2 ->
                        this.manageEmployee();
                    default ->
                        System.out.println("Invalid operation.");
                }

                System.out.println("Another request? ");
                Scanner scanRequest = new Scanner(System.in);
                anotherRequest = scanRequest.next();
            }
        }

        private void manageDepartment() {

            System.out.println("Enter 1 to insert a new department"
                    + ", enter 2 to select a department by name");

            Scanner scan = new Scanner(System.in);
            int choice = scan.nextInt();

            switch (choice) {
                case 1 ->
                    this.insertDepartment();
                case 2 ->
                    this.selectADepartmentByName();
                default ->
                    System.out.println("Invalid operation.");
            }
        }

        private void manageEmployee() {

        }

        private void insertDepartment() {
            System.out.print("Please provide the name of the new department:");
            Scanner scan = new Scanner(System.in);
            String name = scan.next();
            Department d = new Department(name);

            int count = 0;
            try {
                count = d.insert();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(count + " department has been inserted.");
        }

        private void selectADepartmentByName() {
            System.out.print("Please provide the name of the department:");
            Scanner scan = new Scanner(System.in);
            String name = scan.next();

            Department d = null;
            try {
                d = Department.getDepartmentByName(name);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (d != null) {
                System.out.println(d.getDept_number() + "-" + d.getDept_name());
            } else {
                System.out.println("This department does not exist.");
            }
        }

        public static void main(String[] args) {
            new CSCI366().start();
        }

    }

}
