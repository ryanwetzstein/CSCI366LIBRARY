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

    public Publisher() {

    }

    public Publisher(int publisher_ID, String name, Date founded_date, String email, String description) {
        this.publisher_ID = publisher_ID;
        this.name = name;
        this.founded_date = founded_date;
        this.email = email;
        this.description = description;
    }
    
    public Publisher(String name, Date founded_date, String email, String description){
        this.name = name;
        this.founded_date = founded_date;
        this.email = email;
        this.description = description;
    }

    public void insert() {
        String query = "INSERT INTO Publisher (publisher_ID, name, founded_date, email, description) VALUES (?, ?, ?, ?, ?)";

        try ( PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query)) {
            stmt.setInt(1, this.publisher_ID);
            stmt.setString(2, this.name);
            stmt.setDate(3, this.founded_date);
            stmt.setString(4, this.email);
            stmt.setString(5, this.description);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int updatePublisher(int pID, String column, String change) throws SQLException {
        String query = "UPDATE Publisher SET " + column + " = ? WHERE publisher_ID = ?";

        PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query);
            stmt.setString(1, change);
            stmt.setInt(2, pID);
            int count = stmt.executeUpdate();
        return count;
    }

    public static void removePublisher(int publisherID) {
        String query = "DELETE FROM Publisher WHERE publisher_ID = ?";

        try ( PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query)) {
            stmt.setInt(1, publisherID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Publisher> listPublishers() {
        List<Publisher> publishers = new ArrayList<>();
        String query = "SELECT * FROM Publisher";

        try ( PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

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
