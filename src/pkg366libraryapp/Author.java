package pkg366libraryapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author THausauer
 */
public class Author {

    private int author_ID;
    private String first_name;
    private String last_name;
    private Date DOB;

    public Author() {

    }

    public Author(int author_ID, String first_name, String last_name, Date DOB) {
        this.author_ID = author_ID;
        this.first_name = first_name;
        this.last_name = last_name;
        this.DOB = DOB;
    }
    
    public Author(String first_name, String last_name, Date DOB){
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
                Date dob = rs.getDate("DOB");

                authors.add(new Author(id, firstName, lastName, dob));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public void insert() {
        String query = "INSERT INTO Author (author_ID, first_name, last_name, DOB) VALUES (?, ?, ?, ?, ?)";

        try ( PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query)) {
            stmt.setInt(1, this.author_ID);
            stmt.setString(2, this.first_name);
            stmt.setString(3, this.last_name);
            stmt.setDate(4, this.DOB);

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
                Date dob = rs.getDate("DOB");

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

    public int getAuthor_ID() {
        return author_ID;
    }

    public void setAuthor_ID(int author_ID) {
        this.author_ID = author_ID;
    }

    public String getFirst_Name() {
        return first_name;
    }

    public void setFirst_Name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_Name() {
        return last_name;
    }

    public void setLast_Name(String last_name) {
        this.last_name = last_name;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }
}
