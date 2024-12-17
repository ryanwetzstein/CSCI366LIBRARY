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

public class Genre {

    private int genre_id;
    private String genre_name;
    private String description;

    public Genre(int genre_id, String genre_name, String description)
    {
        this.genre_id = genre_id;
        this.genre_name = genre_name;
        this.description = description;
    }
    public Genre(String genre_name, String description)
    {

        this.genre_name = genre_name;
        this.description = description;
    }
    //list books by genres method
    //list all genres method

    public void insertGenre() {
        String query = "INSERT INTO Genre (genre_name, genre_description) VALUES (?, ?)";

        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query)) {

            stmt.setString(1, this.genre_name);
            stmt.setString(2, this.description);
            

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeGenre(int removeGenreID) 
    {
        String query = "DELETE FROM genre WHERE genre_ID=?" ;


        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query)) {
            stmt.setInt(1, removeGenreID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseManager.getConnection();
    }
    public static int UpdateGenre(String column, String change, int genreID) throws SQLException
    {
 
        DatabaseManager.getConnection();
        String query = "UPDATE Author SET " + column + " = ? WHERE author_ID = ?";

        PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query);
            stmt.setString(1, change);
            stmt.setInt(2, genreID);
            int count = stmt.executeUpdate();
        
        return count;
    }
    
    public static List<Genre> listGenres ()
    {
        ArrayList<Genre> genres = new ArrayList<>();
        String query = "SELECT * FROM genre";

        try ( PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(query); ResultSet rs = stmt.executeQuery()){

            while (rs.next()) {
                int genre_id = rs.getInt("genre_id");
                String genre_name = rs.getString("genre_name");
                String description = rs.getString("description");
                
                genres.add(new Genre(genre_id, genre_name, description));
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }

    public Genre(int genre_ID, String genre_name) {
        genre_ID = this.genre_id;
        genre_name = this.genre_name;
    }

    public Genre(String genre_name) {
        this.genre_name = genre_name;
    }

    public Genre(int genre_ID) {
        this.genre_id = genre_ID;
    }

    public int getGenreId() {
        return genre_id;
    }

    public String getGenreName() {
        return genre_name;
    }

    public String getGenreDescription() {
        return description;
    }
}