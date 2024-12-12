package pkg366libraryapp;

import java.util.Scanner;

public class StartingPoint {

    public void start() {
        String anotherRequest = "yes";

        while (anotherRequest.equalsIgnoreCase("yes")) {

            Scanner scanChoice = new Scanner(System.in);
            System.out.println("Enter 1 to manage department, "
                    + "2 to manage employee");
            int choice = scanChoice.nextInt();

            switch (choice) {
                case 1 ->
                    this.manageBooks();
                default ->
                    System.out.println("Invalid operation.");
            }

            System.out.println("Another request? ");
            Scanner scanRequest = new Scanner(System.in);
            anotherRequest = scanRequest.next();
        }
    }

    private void manageBooks() {

    }
}
