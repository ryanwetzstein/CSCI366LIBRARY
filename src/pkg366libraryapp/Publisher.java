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
public class Publisher {

    private int publisher_ID;
    private String name;
    private Date founded_date;
    private String email;
    private String description;

    private static String jdbcURL = "jdbc:postgresql://localhost:5432/LibraryAdmin";
    private static String username = "LibraryAdmin";
    private static String password = "12345!";

    public Publisher() {

    }

    public Publisher(int publisher_ID, String name, Date founded_date, String email, String description) {
        this.publisher_ID = publisher_ID;
        this.name = name;
        this.founded_date = founded_date;
        this.email = email;
        this.description = description;
    }

    public void Insert() {
        String query = "INSERT INTO Publisher (publisher_ID, name, founded_date, email, description) VALUES (?, ?, ?, ?, ?)";

        try ( Connection conn = DriverManager.getConnection(jdbcURL, username, password);  PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, this.publisher_ID);
            pstmt.setString(2, this.name);
            pstmt.setDate(3, this.founded_date);
            pstmt.setString(4, this.email);
            pstmt.setString(5, this.description);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void UpdatePublisher(int publisherID, String condition) {
        String query = "UPDATE Publisher SET " + condition + " WHERE publisher_ID = ?";

        try ( Connection conn = DriverManager.getConnection(jdbcURL, username, password);  PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, publisherID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void RemovePublisher(int publisherID) {
        String query = "DELETE FROM Publisher WHERE publisher_ID = ?";

        try ( Connection conn = DriverManager.getConnection(jdbcURL, username, password);  PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, publisherID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Publisher> ListPublisher(String condition) {
        List<Publisher> publishers = new ArrayList<>();
        String query = "SELECT * FROM Publisher WHERE " + condition;

        try ( Connection conn = DriverManager.getConnection(jdbcURL, username, password);  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("publisher_ID");
                String name = rs.getString("name");
                Date foundedDate = rs.getDate("founded_date");
                String email = rs.getString("email");
                String description = rs.getString("description");

                publishers.add(new Publisher(id, name, foundedDate, email, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publishers;
    }

    public int getPublisher_ID() {
        return publisher_ID;
    }

    public void setPublisher_ID(int publisher_ID) {
        this.publisher_ID = publisher_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFounded_Date() {
        return founded_date;
    }

    public void setFounded_Date(Date founded_date) {
        this.founded_date = founded_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription() {
        this.description = description;
    }
}
