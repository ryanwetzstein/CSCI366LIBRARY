package pkg366libraryapp;

import java.sql.SQLException;
import java.util.List;
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
                        + "\n Enter 'h' to insert a book."
                        + "\n Enter 'i' to remove a book."
                        + "\n Enter 'q' to Quit."
                );

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
                    case "h" -> {
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("Enter ISBN: ");
                        int isbn = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter Description: ");
                        String description = scanner.nextLine();
                        System.out.print("Enter Publication Year: ");
                        int publicationYear = scanner.nextInt();
                        System.out.print("Enter Available Copies: ");
                        int availableCopies = scanner.nextInt();
                        System.out.print("Enter Total Copies: ");
                        int totalCopies = scanner.nextInt();
                        scanner.nextLine();
                        Book newBook = new Book(isbn, title, description, publicationYear, availableCopies, totalCopies);
                        newBook.insertBook();
                        System.out.println("Book added successfully.");
                    }
                    case "i" -> {
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("Enter ISBN of the book to remove: ");
                        int removeIsbn = scanner.nextInt();
                        Book.removeBook(removeIsbn);
                        System.out.println("Book removed successfully.");
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
            if (self) {
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
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            // Display menu options
            System.out.println("\n--- Manage Books ---");
            System.out.println("1. List All Books");
            System.out.println("2. List Books by Title");
            System.out.println("3. List Books by Publication Year");
            System.out.println("4. List Total Count of Books");
            System.out.println("5. List Count of Most Available Books");
            System.out.println("6. List Count of Least Available Books");
            System.out.println("7. List Books by Author");
            System.out.println("8. List Books by Publisher");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            // Get user input
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // List All Books
                    List<Book> allBooks = Book.listAllBooks();
                    System.out.println("\n--- All Books ---");
                    for (Book book : allBooks) {
                        System.out.println("ISBN: " + book.getIsbn() + ", Title: " + book.getTitle()
                                + ", Available Copies: " + book.getAvailable_copies()
                                + ", Total Copies: " + book.getTotal_copies()
                                + ", Publication Year: " + book.getPublication_year());
                    }
                    break;
                case 2:
                    // List Books by Title
                    List<Book> booksByTitle = Book.listBooksByTitle();
                    System.out.println("\n--- Books Ordered by Title ---");
                    for (Book book : booksByTitle) {
                        System.out.println("ISBN: " + book.getIsbn() + ", Title: " + book.getTitle()
                                + ", Available Copies: " + book.getAvailable_copies()
                                + ", Total Copies: " + book.getTotal_copies()
                                + ", Publication Year: " + book.getPublication_year());
                    }
                    break;
                case 3:
                    // List Books by Publication Year
                    List<Book> booksByYear = Book.listBooksByPublicationYear();
                    System.out.println("\n--- Books Ordered by Publication Year ---");
                    for (Book book : booksByYear) {
                        System.out.println("ISBN: " + book.getIsbn() + ", Title: " + book.getTitle()
                                + ", Available Copies: " + book.getAvailable_copies()
                                + ", Total Copies: " + book.getTotal_copies()
                                + ", Publication Year: " + book.getPublication_year());
                    }
                    break;
                case 4:
                    // List Total Count of Books
                    int totalCount = Book.listTotalCountOfBooks();
                    System.out.println("Total number of books: " + totalCount);
                    break;
                case 5:
                    // List Count of Most Available Books
                    int mostAvailableCount = Book.listCountOfMostAvailableBook();
                    System.out.println("Most available books count: " + mostAvailableCount);
                    break;
                case 6:
                    // List Count of Least Available Books
                    int leastAvailableCount = Book.listCountOfLeastAvailableBook();
                    System.out.println("Least available books count: " + leastAvailableCount);
                    break;
                case 7:
                    Scanner scan = new Scanner(System.in);
                    System.out.println("Please provide an author ID: ");
                    int authorID = scan.nextInt();
                    List<Book> booksByAuthor = Book.listBooksByAuthor(authorID);
                    System.out.println("\n--- Books Ordered by Author ---");
                    for (Book book : booksByAuthor) {
                        System.out.println("ISBN: " + book.getIsbn() + ", Title: " + book.getTitle()
                                + ", Available Copies: " + book.getAvailable_copies()
                                + ", Total Copies: " + book.getTotal_copies()
                                + ", Publication Year: " + book.getPublication_year());
                    }
                    break;
                case 8:
                    scan = new Scanner(System.in);
                    System.out.println("Please provide a publisher ID: ");
                    int publisherID = scan.nextInt();
                    List<Book> booksByPublisher = Book.listBooksByPublisher(publisherID);
                    System.out.println("\n--- Books Ordered by Publisher ---");
                    for (Book book : booksByPublisher) {
                        System.out.println("ISBN: " + book.getIsbn() + ", Title: " + book.getTitle()
                                + ", Available Copies: " + book.getAvailable_copies()
                                + ", Total Copies: " + book.getTotal_copies()
                                + ", Publication Year: " + book.getPublication_year());
                    }
                    break;
                case 9:
                    // Exit the loop
                    System.out.println("Exiting...");
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 9); // Loop until the user chooses to exit

    }
}
