package pkg366libraryapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Trev Hausauer
 */
public class Author {

    private int author_ID;
    private String first_name;
    private String last_name;
    private String DOB;
    
    public Author(int author_ID, String first_name, String last_name, String DOB) {
        this.author_ID = author_ID;
        this.first_name = first_name;
        this.last_name = last_name;
        this.DOB = DOB;
    }
    
    public Author(String first_name, String last_name, String DOB){
        this.first_name = first_name;
        this.last_name = last_name;
        this.DOB = DOB;
    }

    public static List<Author> listAuthors() {
        List<Author> authors = new ArrayList<>();
        String query = "SELECT * FROM Author";

        try ( PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query); ResultSet rs = stmt.executeQuery()){

            while (rs.next()) {
                int id = rs.getInt("author_ID");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String dob = rs.getString("DOB");

                authors.add(new Author(id, firstName, lastName, dob));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public void insert() {
        String query = "INSERT INTO Author (first_name, last_name, DOB) VALUES (?, ?, CAST(? AS DATE))";
        
        try ( PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query)) {
            stmt.setString(1, this.first_name);
            stmt.setString(2, this.last_name);
            stmt.setString(3, this.DOB);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Author listAuthorInfo(int authorID) {
        String query = "SELECT * FROM Author WHERE author_ID = ?";
        Author author = null;

        try ( PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            stmt.setInt(1, authorID);

            if (rs.next()) {
                int id = rs.getInt("author_ID");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String dob = rs.getString("DOB");

                author = new Author(id, firstName, lastName, dob);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return author;
    }

    public static void removeAuthor(int authorID) {
        String query = "DELETE FROM Author WHERE author_ID = ?";

        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query)) {
            stmt.setInt(1, authorID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int updateAuthor(int aID, String column, String change) throws SQLException {
        String query = "UPDATE Author SET " + column + " = ? WHERE author_ID = ?";

        PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query);
            stmt.setString(1, change);
            stmt.setInt(2, aID);
            int count = stmt.executeUpdate();
        return count;
    }
    
    public static int updateAuthorDate(int aID, String column, String change) throws SQLException {
        String query = "UPDATE Author SET " + column + " = CAST (? AS DATE) WHERE author_ID = ?";

        PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query);
            stmt.setString(1, change);
            stmt.setInt(2, aID);
            int count = stmt.executeUpdate();
        return count;
    }
    
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

    public int getAuthor_ID() {
        return author_ID;
    }

    public String getFirst_Name() {
        return first_name;
    }

    public String getLast_Name() {
        return last_name;
    }

    public String getDOB() {
        return DOB;
    }
}
