package pkg366libraryapp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class StartingPoint {

    /**
     * Logged in variable to track logged in user. 0 - not logged in 1 - logged
     * in 2 - special case
     * 
     * Authors: Ryan Wetzstein, Trev Hausauer, Daniel Putnam, Nashat Khan
     *
     */
    public static int loggedIn = 0;
    Customer loggedInUser = null;

    public void start(){

        if (loggedIn == 0) {
            manageAuthCustomer();
        }

        String anotherRequest = "yes";

        while (loggedIn != 2 && anotherRequest.equalsIgnoreCase("yes")) {

            Scanner scanChoice = new Scanner(System.in);

            System.out.println("\nWhat would you like to do next?"
                    + "\n Enter 'a' to manage Books. "
                    + "\n Enter 'b' to manage your account. "
                    + "\n Enter 'c' to manage Authors."
                    + "\n Enter 'd' to manage Publishers."                
                    + "\n Enter 'e' to view admin options. "
                    + "\n Enter 'f' to manage Borrowings. ");
            String choice = scanChoice.next();

            switch (choice) {
                case "a" ->
                    this.manageBooks();
                case "b" ->
                    this.manageCustomer();
                case "c" ->
                    this.manageAuthor();
                case "d" ->
                    this.managePublisher();
                case "e" ->
                    this.adminOptions();
                case "f" ->
                    this.manageBorrowed();
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
                        +"\n Enter 'j' to create an author."
                        +"\n Enter 'k to delete an author."
                        +"\n Enter 'l' to update an author."
                        +"\n Enter 'm' to create a publisher."
                        +"\n Enter 'n' to delete a publisher." 
                        +"\n Enter 'o' to update a publisher."
                        +"\n Enter 'p' to add a genre."
                        +"\n Enter 'r' to update a genre."
                        +"\n Enter 's' to remove a genre."
                        +"\n Enter 't' to list all borrowed books."
                        +"\n Enter 'u' to list all borrowed books and user info."
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
                    case "j" ->{
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Enter a first name.");
                        String firstname = scanner.nextLine();
                        System.out.println("Enter a last name.");
                        String lastname = scanner.nextLine();
                        System.out.println("Enter a DOB.");
                        String dob = scanner.nextLine();
                        Author author = new Author(firstname, lastname, dob);
                        author.insert();
                        System.out.println("Author added successfully");
                    }
                    case "k" ->{
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("Enter the ID of the author to remove:");
                        int authorId = scanner.nextInt();
                        Author.removeAuthor(authorId);
                        System.out.println("Author removed successfully");
                    }
                    case "l" -> {
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("Enter the ID of the author you want to update:");
                        int author_ID = scanner.nextInt();
                        updateAuthor(author_ID);
                    }
                    case "m" ->{
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Enter a name.");
                        String name = scanner.nextLine();
                        System.out.println("Enter a founded date.");
                        String founded_date = scanner.nextLine();
                        System.out.println("Enter an email.");
                        String email = scanner.nextLine();
                        System.out.println("Enter a description.");
                        String description = scanner.nextLine();
                        Publisher publisher = new Publisher(name, founded_date, email, description);
                        publisher.insert();
                        System.out.println("Author added successfully");
                    }
                    case "n" ->{
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("Enter the ID of the publisher to remove:");
                        int publisher_Id = scanner.nextInt();
                        Publisher.removePublisher(publisher_Id);
                        System.out.println("Author removed successfully");
                    }
                    case "o" ->{
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("Enter the ID of the publisher you want to update:");
                        int publisher_ID = scanner.nextInt();
                        updatePublisher(publisher_ID);
                    }
                    case "p" ->{
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Enter a genre name.");
                        String genre_name = scanner.nextLine();
                        System.out.println("Enter a description.");
                        String description = scanner.nextLine();
                        
                        Genre genre = new Genre(genre_name, description);
                        genre.insertGenre();
                        System.out.println("Genre added successfully");
                    }
                    case "r" ->{
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("Enter the ID of the genre you want to update:");
                        int genre_ID = scanner.nextInt();
                        updateGenre(genre_ID);
                    }
                    case "s" ->{
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("Enter the ID of the genre to remove:");
                        int genreId = scanner.nextInt();
                        Genre.removeGenre(genreId);
                        System.out.println("genre removed successfully");
                    }
                    case "t" ->{
                        List<Borrowings> allBorrowings = Borrowings.listBorrowings();
                    System.out.println("\n--- All Borrowings ---");
                    for (Borrowings borrowing : allBorrowings) {
                        System.out.println("Borrowing Date: " + borrowing.getBorrowDate() + ", Due Date: " + borrowing.getDueDate()
                                + ", Return Date: " + borrowing.getReturnDate()
                                + ", ISBN: " + borrowing.getBookISBN()
                                + ", Borrowing ID: " + borrowing.getBorrowID()
                                + ", Customer ID: " + borrowing.getCustomerID()
                        );
                    }
                    break;
                    }
                    case "u" ->{
                        List<Borrowings> allBorrowings = Borrowings.listBorrowedBooksAndUserInfo();
                    System.out.println("\n--- All Borrowings ---");
                    for (Borrowings borrowing : allBorrowings) {
                        System.out.println("Borrowing Date: " + borrowing.getBorrowDate() + ", Due Date: " + borrowing.getDueDate()
                                + ", Return Date: " + borrowing.getReturnDate()
                                + ", ISBN: " + borrowing.getBookISBN()
                                + ", Borrowing ID: " + borrowing.getBorrowID()
                                + ", Customer ID: " + borrowing.getCustomerID()
                        );
                    }
                    break;
                    }
                    
                    case "q" ->
                        System.out.println("\nExiting admin menu.\n");
                    default ->
                        System.out.println("Invalid operation.");
                }
            }
        }
    }

    private void manageCustomer(){
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
                            Customer.updateCustomerDate(ID, "DOB", s);
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
            System.out.println("9. List All Genres");
            System.out.println("10. List View");
            System.out.println("11. Exit");
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
                    // List All Genres
                    List<Genre> allGenres = Genre.listGenres();
                    System.out.println("\n--- All Genres ---");
                    for (Genre genre : allGenres) {
                        System.out.println("Genre Name: " + genre.getGenreName() + ",Description: " + genre.getGenreDescription());
                    }
                    break;
                case 10:
                {
                    try {
                        // List View
                        listView();
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                    break;
                case 11:
                    // Exit the loop
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 11); // Loop until the user chooses to exit

    }
    
    private void manageAuthor(){
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            // Display menu options
            System.out.println("\n--- Manage Authors ---");
            System.out.println("1. List All Authors");
            System.out.println("2. List Books by Given Author");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            // Get user input
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // List All Books
                    List<Author> allAuthors = Author.listAuthors();
                    System.out.println("\n--- All Authors ---");
                    for (Author author : allAuthors) {
                        System.out.println("Author ID: " + author.getAuthor_ID() + ", First Name: " + author.getFirst_Name()
                                + ", Last Name: " + author.getLast_Name()
                                + ", DOB: " + author.getDOB());
                    }
                    break;
                case 2:
                    // List all books by given author
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
                case 9:
                    // Exit the loop
                    System.out.println("Exiting...");
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 9); // Loop until the user chooses to exit
    }
    
    private void managePublisher(){
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            // Display menu options
            System.out.println("\n--- Manage Publishers ---");
            System.out.println("1. List All Publishers");
            System.out.println("2. List Books by Given Publisher");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            // Get user input
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // List All Books
                    List<Publisher> allPublishers = Publisher.listPublishers();
                    System.out.println("\n--- All Authors ---");
                    for (Publisher publisher : allPublishers) {
                        System.out.println("Publisher ID: " + publisher.getPublisher_ID() + ", Name: " + publisher.getName()
                                + ", Founded Date: " + publisher.getFounded_Date()
                                + ", Email: " + publisher.getEmail()
                                + ", Description:" + publisher.getDescription());
                    }
                    break;
                    case 2:
                    Scanner scan = new Scanner(System.in);
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
    
    private void updateAuthor(int author_ID){
        String choice = "";

        while (!choice.equals("q")) {

            Scanner scanChoice = new Scanner(System.in);

            System.out.println("\nWhat would you like to update?."
                    + "\n Enter 'a' to update first name."
                    + "\n Enter 'b' to update last name."
                    + "\n Enter 'c' to update DOB."
                    + "\n Enter 'q' to Quit.");

            choice = scanChoice.next();

            switch (choice) {
                case "a" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new first name: ");
                        Author.updateAuthor(author_ID, "first_name", scan.next());
                        System.out.println("Update Successful.");
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "b" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new last name: ");
                        Author.updateAuthor(author_ID, "last_name", scan.next());
                        System.out.println("Update Successful.");
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "c" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new DOB: ");
                        Author.updateAuthorDate(author_ID, "DOB", scan.next());
                        System.out.println("Update Successful.");
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "q" ->
                    System.out.println("\nExiting author manager..\n");
                default ->
                    System.out.println("Invalid operation.");
            }
        }
    }
    
    private void updatePublisher(int publisher_ID){
        String choice = "";

        while (!choice.equals("q")) {

            Scanner scanChoice = new Scanner(System.in);

            System.out.println("\nWhat would you like to update?."
                    + "\n Enter 'a' to update name."
                    + "\n Enter 'b' to update founded date."
                    + "\n Enter 'c' to update email."
                    + "\n Enter 'd' to update description."
                    + "\n Enter 'q' to Quit.");

            choice = scanChoice.next();

            switch (choice) {
                case "a" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new first name: ");
                        Publisher.updatePublisher(publisher_ID, "first_name", scan.next());
                        System.out.println("Update Successful.");
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "b" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new last name: ");
                        Publisher.updatePublisher(publisher_ID, "last_name", scan.next());
                        System.out.println("Update Successful.");
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "c" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new DOB: ");
                        Publisher.updatePublisherDate(publisher_ID, "DOB", scan.next());
                        System.out.println("Update Successful.");
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "d" ->{
                    try{
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new description: ");
                        Publisher.updatePublisher(publisher_ID, "description", scan.next());
                        System.out.println("Update Successful.");
                    } catch (SQLException e){
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "q" ->
                    System.out.println("\nExiting author manager..\n");
                default ->
                    System.out.println("Invalid operation.");
            }
        }
    }
    private void updateGenre(int genre_ID){
        String choice = "";

        while (!choice.equals("q")) {

            Scanner scanChoice = new Scanner(System.in);

            System.out.println("\nWhat would you like to update?."
                    + "\n Enter 'a' to update genre name."
                    + "\n Enter 'b' to update description."
                    + "\n Enter 'q' to Quit.");

            choice = scanChoice.next();

            switch (choice) {
                case "a" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new genre name: ");
                        Genre.updateGenre("genre_name", scan.next(), genre_ID) ;
                        System.out.println("Update Successful.");
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "b" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new genre_description: ");
                        Genre.updateGenre("description", scan.next(), genre_ID);
                        System.out.println("Update Successful.");
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                
                
                case "q" ->
                    System.out.println("\nExiting author manager..\n");
                default ->
                    System.out.println("Invalid operation.");
            }
        }
    }
    private void manageBorrowed(){
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            // Display menu options
            System.out.println("\n--- Manage Your Borrowed Books ---");
            System.out.println("1. List Most Borrowed Book");
            System.out.println("2. Update Borrowing");
            System.out.println("3. Borrow Book");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            // Get user input
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    
                    List<Borrowings> mostBorrowed = Borrowings.listMostBorrowed();
                    System.out.println("\n--- Most Borrowed Book ---");
                    for (Borrowings borrow : mostBorrowed) {
                        System.out.println(" ID: " + borrow.getBorrowID());
                        //how do i do this method to show everyhing, show i just stick to borrowing class variables, or is this easily possible? 
                    }
                    
                    break;
                case 2:
                    // update borrowing
                    scanner = new Scanner(System.in); // this might cause an error, not sure
                    System.out.print("Enter the ID of the borrowing you want to update:");
                    int borrow_ID = scanner.nextInt();
                    updateBorrowing(borrow_ID);
                case 3:
                    // borrowBook
                    {
                        scanner = new Scanner(System.in); // might cause an error idk
                        System.out.print("Enter Borrow Date: ");
                        String borrow_date = scanner.nextLine();
                        System.out.print("Enter due date: ");
                        String due_date = scanner.nextLine();
                        System.out.print("Enter return date: ");
                        String return_date = scanner.nextLine();
                        System.out.print("Enter Customer ID: ");
                        int customer_ID = scanner.nextInt();
                        System.out.print("Enter Book ISBN: ");
                        int book_ISBN = scanner.nextInt();
                        scanner.nextLine();
                        Borrowings newBorrow = new Borrowings(borrow_date,due_date,return_date,customer_ID,book_ISBN);
                        newBorrow.addBorrowing();
                        System.out.println("Borrowing added successfully.");
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
    private void updateBorrowing(int borrowing_ID){
        String choice = "";

        while (!choice.equals("q")) {

            Scanner scanChoice = new Scanner(System.in);

            System.out.println("\nWhat would you like to update?."
                    + "\n Enter 'a' to update borrow_date."
                    + "\n Enter 'b' to update due_date."
                    + "\n Enter 'c' to update return_date."
                    + "\n Enter 'd' to update customer_id."
                    + "\n Enter 'e to update book_isbn."
                    + "\n Enter 'q' to Quit.");

            choice = scanChoice.next();

            switch (choice) {
                case "a" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new borrow date: ");
                        Borrowings.updateBorrowingDate(borrowing_ID, "borrow_date", scan.next());
                        System.out.println("Update Successful.");
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "b" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new due date: ");
                        Borrowings.updateBorrowingDate(borrowing_ID, "due_date", scan.next());
                        System.out.println("Update Successful.");
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "c" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new return date: ");
                        Borrowings.updateBorrowingDate(borrowing_ID, "return_date", scan.next());
                        System.out.println("Update Successful.");
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "d" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new customer_id: ");
                        Borrowings.updateBorrowing(borrowing_ID, "customer_id", scan.next());
                        System.out.println("Update Successful.");
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "e" -> {
                    try {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Enter your new book isbn: ");
                        Borrowings.updateBorrowing(borrowing_ID, "book_isbn", scan.next());
                        System.out.println("Update Successful.");
                    } catch (SQLException e) {
                        System.out.println("Got a SQL exception.");
                        e.printStackTrace();
                    }
                }
                case "q" ->
                    System.out.println("\nExiting borrowing manager..\n"); // doesnt exit out the loop
                    
                    
                default ->
                    System.out.println("Invalid operation.");
            }
        }
    }
    
    public static void listView() throws SQLException {
        String query = "SELECT * FROM public.bookdetails";

        PreparedStatement pstmtSelect = DatabaseManager.getConnection().prepareStatement(query);

        ResultSet rs = pstmtSelect.executeQuery();

        while (rs.next()) {
            int isbn = rs.getInt("isbn");
            String title = rs.getString("title");
            int publication_year = rs.getInt("publication_year");
            String author = rs.getString("author");
            String publisher = rs.getString("publisher");
            String genre_name = rs.getString("genre_name");
            
            System.out.println("\nISBN: " + isbn
                + "\nTitle: " + title
                + "\nPublication Year: " + publication_year
                + "\nAuthor: " + author
                + "\nPublisher: " + publisher
                +"\nGenre Name: " + genre_name);
        }
    }
}
