/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    private static String jdbcURL = "jdbc:postgresql://localhost:5432/LibraryAdmin";
    private static String username = "LibraryAdmin";
    private static String password = "12345!";

    public Author() {

    }

    public Author(int author_ID, String first_name, String last_name, Date DOB) {
        this.author_ID = author_ID;
        this.first_name = first_name;
        this.last_name = last_name;
        this.DOB = DOB;
    }

    public static List<Author> ListAuthors(String condition) {
        List<Author> authors = new ArrayList<>();
        String query = "SELECT * FROM Author WHERE " + condition;

        try ( Connection conn = DriverManager.getConnection(jdbcURL, username, password);  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(query)) {

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

    public void Insert() {
        String query = "INSERT INTO Author (author_ID, first_name, last_name, DOB) VALUES (?, ?, ?, ?, ?)";

        try ( Connection conn = DriverManager.getConnection(jdbcURL, username, password);  PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, this.author_ID);
            pstmt.setString(2, this.first_name);
            pstmt.setString(3, this.last_name);
            pstmt.setDate(4, this.DOB);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Author ListAuthorInfo(int authorID) {
        String query = "SELECT * FROM Author WHERE author_ID = ?";
        Author author = null;

        try ( Connection conn = DriverManager.getConnection(jdbcURL, username, password);  PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, authorID);
            ResultSet rs = pstmt.executeQuery();

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

        try ( Connection conn = DriverManager.getConnection(jdbcURL, username, password);  PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, authorID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateAuthor(int authorID, String condition) {
        String query = "UPDATE Author SET " + condition + " WHERE author_ID = ?";

        try ( Connection conn = DriverManager.getConnection(jdbcURL, username, password);  PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, authorID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
