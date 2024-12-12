package pkg366libraryapp;


import java.util.Scanner;
import java.sql.SQLException;

/**
 *
 * @author group
 */
public class Main {

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
