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
 * @author Trev Hausauer
 */
public class Publisher {

    private int publisher_ID;
    private String name;
    private String founded_date;
    private String email;
    private String description;

    public Publisher(int publisher_ID, String name, String founded_date, String email, String description) {
        this.publisher_ID = publisher_ID;
        this.name = name;
        this.founded_date = founded_date;
        this.email = email;
        this.description = description;
    }
    
    public Publisher(String name, String founded_date, String email, String description){
        this.name = name;
        this.founded_date = founded_date;
        this.email = email;
        this.description = description;
    }

    public void insert() {
        String query = "INSERT INTO Publisher (name, founded_date, email, description) VALUES (?, ?, CAST (? AS DATE), ?, ?)";

        try ( PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query)) {
            stmt.setString(1, this.name);
            stmt.setString(2, this.founded_date);
            stmt.setString(3, this.email);
            stmt.setString(4, this.description);

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
    
    public static int updatePublisherDate(int pID, String column, String change) throws SQLException {
        String query = "UPDATE Publisher SET " + column + " = CAST (? AS DATE) WHERE publisher_ID = ?";

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
                String foundedDate = rs.getString("founded_date");
                String email = rs.getString("email");
                String description = rs.getString("description");

                publishers.add(new Publisher(id, name, foundedDate, email, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publishers;
    }
    
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

    public int getPublisher_ID() {
        return publisher_ID;
    }

    public String getName() {
        return name;
    }

    public String getFounded_Date() {
        return founded_date;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }
}
