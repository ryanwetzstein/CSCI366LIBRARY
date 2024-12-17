/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg366libraryapp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nashat Khan
 */
public class Borrowings 
{
    private int borrow_id;
    private String borrow_date;
    private String due_date;
    private String return_date;
    private int customer_id;
    private int book_isbn;
    
    public Borrowings (int borrow_id, String borrow_date, String due_date, String return_date, int customer_id, int book_isbn)
    {
        borrow_id = this.borrow_id;
        borrow_date = this.borrow_date;
        due_date = this.due_date;
        return_date = this.return_date;
        customer_id = this.customer_id;
        book_isbn = this.book_isbn;
        
    }
    public Borrowings (String borrow_date, String return_date)
    {
        borrow_date = this.borrow_date;
        return_date = this.return_date;

    }
    public Borrowings (int book_isbn)
    {
        book_isbn = this.book_isbn;

    }
    
    public static List<Borrowings> listBorrowings()
    {
        ArrayList<Borrowings> borrowingsList = new ArrayList<>();
        String query = "Select * FROM Borrowing";

        try ( PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query); ResultSet rs = stmt.executeQuery()){

            while (rs.next()) {
                int borrow_id = rs.getInt("borrow_id");
                String borrow_date = rs.getString("borrow_date");
                String due_date = rs.getString("due_date");
                String return_date = rs.getString("return_date");
                int customer_id = rs.getInt("customer_id");
                int book_isbn = rs.getInt("book_isbn");

                borrowingsList.add(new Borrowings(borrow_id,borrow_date,due_date,return_date,customer_id,book_isbn));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowingsList;
    }
    public static List<Borrowings> listMostBorrowed()
    {
        ArrayList<Borrowings> borrowingsList = new ArrayList<>();
        String query = "SELECT book.title, COUNT(book_isbn) AS frequency "
                + "FROM borrowing JOIN book on borrowing.book_isbn = book.isbn "
                + "GROUP BY book.title "
                + "ORDER BY frequency DESC "
                + "LIMIT 1";

        try ( PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query); ResultSet rs = stmt.executeQuery()){

            while (rs.next()) {
                
                int book_isbn = rs.getInt("book_isbn");

                borrowingsList.add(new Borrowings(book_isbn));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowingsList;
    }
    public static List<Borrowings> listBorrowedBooksAndUserInfo() {
        List<Borrowings> borrowings = new ArrayList<>();
        String query = "SELECT title, borrow_date,return_date, first_name, last_name FROM borrowing" +
        "FUll JOIN customer" +
        "on borrowing.customer_id = customer.customer_id" +
        "FUll JOIN book" +
        "on book.isbn= borrowing.book_isbn" +
        "Order by borrowing.customer_id";

        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String title = rs.getString("title");
                    String borrow_date = rs.getString("borrow_date");
                    String return_date= rs.getString("return_date");
                    String first_name= rs.getString("first_name");
                    String last_name= rs.getString("last_name");

                    borrowings.add(new Borrowings(borrow_date, return_date));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowings;
    }
    
    
    public void addBorrowing() {
        String query = "INSERT INTO borrowing (borrow_date, due_date, return_date, customer_id, book_isbn) VALUES (CAST(? AS DATE),CAST(? AS DATE),CAST(? AS DATE)?, ?)";
        
        try ( PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query)) {
            stmt.setString(1, this.borrow_date);
            stmt.setString(2, this.due_date);
            stmt.setString(3, this.return_date);
            stmt.setInt(4, this.customer_id);
            stmt.setInt(5, this.book_isbn);
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static int updateBorrowing(int bID, String column, String change) throws SQLException {
        String query = "UPDATE borrowing SET " + column + " = ? WHERE borrow_id = ?";

        PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query);
            stmt.setString(1, change);
            stmt.setInt(2, bID);
            int count = stmt.executeUpdate();
        return count;
    }
    public static int updateBorrowingDate(int bID, String column, String change) throws SQLException {
        String query = "UPDATE borrowing SET " + column + " = CAST (? AS DATE) WHERE borrow_id = ?";

        PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query);
            stmt.setString(1, change);
            stmt.setInt(2, bID);
            int count = stmt.executeUpdate();
        return count;
    }
           
    
    
    public int getBorrowID(){
        return borrow_id;
    } 
    public String getBorrowDate()
    {
        return borrow_date;
    }
    public String getDueDate()
    {
        return due_date;
    }
    public String getReturnDate()
    {
        return return_date;
    }
    public int getCustomerID(){
        return customer_id;
    }
    public int getBookISBN(){
        return book_isbn;
    }
    
    
}
