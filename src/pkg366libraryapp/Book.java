package pkg366libraryapp;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RyanWetzstein
 */
public class Book {

    private int isbn;
    private String title;
    private String description;
    private int publication_year;
    private int available_copies;
    private int total_copies;

    public Book() {

    }

    //with copies
    public Book(int isbn, String title, String description, int publication_year, int available_copies, int total_copies) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.publication_year = publication_year;
        this.available_copies = available_copies;
        this.total_copies = total_copies;
    }

    public Book(int isbn, String title, String description, int publicationYear) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.publication_year = publicationYear;
    }

    /**
     *
     */
    public void insertBook() {
        String query = "INSERT INTO Book (isbn, title, description, publication_year, available_copies, total_copies) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query)) {

            stmt.setInt(1, this.isbn);
            stmt.setString(2, this.title);
            stmt.setString(3, this.description);
            stmt.setInt(4, this.publication_year);
            stmt.setInt(5, this.available_copies);
            stmt.setInt(6, this.total_copies);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param isbn
     */
    public static void removeBook(int isbn) {
        String query = "DELETE FROM Book WHERE isbn = ?";

        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query)) {

            stmt.setInt(1, isbn);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return list of books
     */
    public static List<Book> listAllBooks() {
        List<Book> books = new ArrayList<>();
        // Database connection details

        String query = "SELECT * FROM Book";

        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int isbn = rs.getInt("isbn");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int publication_year = rs.getInt("publication_year");
                int available_copies = rs.getInt("available_copies");
                int total_copies = rs.getInt("total_copies");

                // Book book = new Book(bookID, title, author, publisher, genre, publicationYear);
                books.add(new Book(isbn, title, description, publication_year, available_copies, total_copies));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    /**
     *
     * @return list of books ordered by title
     */
    public static List<Book> listBooksByTitle() {
        List<Book> books = new ArrayList<>();
        // Database connection details

        String query = "SELECT * FROM Book ORDER BY title";

        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int isbn = rs.getInt("isbn");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int publication_year = rs.getInt("publication_year");
                int available_copies = rs.getInt("available_copies");
                int total_copies = rs.getInt("total_copies");

                books.add(new Book(isbn, title, description, publication_year, available_copies, total_copies));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    /**
     *
     * @return int of the total number of books
     */
    public static int listTotalCountOfBooks() {
        int totalCount = 0;
        String query = "SELECT COUNT(*) AS total_books FROM Book";

        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                totalCount = rs.getInt("total_books");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalCount;
    }

    /**
     *
     * @return count of least available books
     */
    public static int listCountOfLeastAvailableBook() {
        int leastAvailableCount = 0;
        String query = "SELECT MIN(available_copies) AS least_available FROM Book";

        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                leastAvailableCount = rs.getInt("least_available");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return leastAvailableCount;
    }

    /**
     *
     * @return count of most available books
     */
    public static int listCountOfMostAvailableBook() {
        int mostAvailableCount = 0;
        String query = "SELECT MAX(available_copies) AS most_available FROM Book";

        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                mostAvailableCount = rs.getInt("most_available");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mostAvailableCount;
    }

    /**
     *
     * @return list of books by publication year
     */
    public static List<Book> listBooksByPublicationYear() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM Book ORDER BY publication_year";

        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int isbn = rs.getInt("isbn");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int publication_year = rs.getInt("publication_year");
                int available_copies = rs.getInt("available_copies");
                int total_copies = rs.getInt("total_copies");

                books.add(new Book(isbn, title, description, publication_year, available_copies, total_copies));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    /**
     *
     * @param condition
     * @return list of books by specified condition
     */
    public static List<Book> listBooksByCondition(String condition) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM Book WHERE " + condition;

        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int isbn = rs.getInt("isbn");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int publication_year = rs.getInt("publication_year");
                int available_copies = rs.getInt("available_copies");
                int total_copies = rs.getInt("total_copies");

                books.add(new Book(isbn, title, description, publication_year, available_copies, total_copies));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    /**
     *
     * @param authorID
     * @return list of books by author using wrote table
     */
    public static List<Book> listBooksByAuthor(int authorID) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * "
                + "FROM Book b "
                + "JOIN Wrote w ON b.isbn = w.book_isbn "
                + "WHERE w.author_id = ?";

        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query)) {

            stmt.setInt(1, authorID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int isbn = rs.getInt("isbn");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    int publication_year = rs.getInt("publication_year");
                    int available_copies = rs.getInt("available_copies");
                    int total_copies = rs.getInt("total_copies");

                    books.add(new Book(isbn, title, description, publication_year, available_copies, total_copies));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    /**
     *
     * @param publisherID
     * @return list books by publisher using published table
     */
    public static List<Book> listBooksByPublisher(int publisherID) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * "
                + "FROM Book b "
                + "JOIN Published p ON b.isbn = p.book_isbn "
                + "WHERE p.publisher_id = ?";

        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query)) {

            stmt.setInt(1, publisherID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int isbn = rs.getInt("isbn");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    int publication_year = rs.getInt("publication_year");
                    int available_copies = rs.getInt("available_copies");
                    int total_copies = rs.getInt("total_copies");

                    books.add(new Book(isbn, title, description, publication_year, available_copies, total_copies));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    /**
     *
     * @param genreID
     * @return list books by genre using typeofbook table
     */
    public static List<Book> listBooksByGenre(int genreID) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * "
                + "FROM Book b "
                + "JOIN typeofbook t ON b.isbn = t.book_isbn "
                + "WHERE t.genre_id = ?";

        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query)) {

            stmt.setInt(1, genreID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int isbn = rs.getInt("isbn");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    int publication_year = rs.getInt("publication_year");
                    int available_copies = rs.getInt("available_copies");
                    int total_copies = rs.getInt("total_copies");

                    books.add(new Book(isbn, title, description, publication_year, available_copies, total_copies));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    //get and set//
    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(int publication_year) {
        this.publication_year = publication_year;
    }

    public int getAvailable_copies() {
        return available_copies;
    }

    public void setAvailable_copies(int available_copies) {
        this.available_copies = available_copies;
    }

    public int getTotal_copies() {
        return total_copies;
    }

    public void setTotal_copies(int total_copies) {
        this.total_copies = total_copies;
    }
}
