package pkg366libraryapp;

/**
 * 
 * @author Nashat Khan
 */

public class Genre {

    private int genre_ID;
    private String genre_name;
    private String genreDescription;

    public Genre(int genre_ID, String genre_name, String description) {
        genre_ID = this.genre_ID;
        genre_name = this.genre_name;
        genreDescription = description;
    }
    //list books by genres method
    //list all genres method

    public static void addGenre(String newGenre, String description) {
        String query = "INSERT INTO genre_name, description VALUES(" + newGenre +","+description+");";
        DatabaseManager.getConnection();
    }

    public static void removeGenre(String removeGenre) 
    {
        String query = "DELETE FROM genre WHERE genre_name=" + removeGenre + ";" ;
        DatabaseManager.getConnection();
    }
    public static void UpdateGenreName(String needGenre, String newGenre) 
    {
        String query = "UPDATE genre SET genre_name='"+newGenre+"'WHERE Genre='"+needGenre+"';"; 
        DatabaseManager.getConnection();
    }
    public static void UpdateGenreDescription(String needGenreDescrip, String newGenreDescrip) 
    {
        String query = "UPDATE genre SET genre_name='"+newGenreDescrip+"'WHERE Genre='"+needGenreDescrip+"';"; 
        DatabaseManager.getConnection();
    }

    public Genre(int genre_ID, String genre_name) {
        genre_ID = this.genre_ID;
        genre_name = this.genre_name;
    }

    public Genre(String genre_name) {
        genre_name = this.genre_name;
    }

    public Genre(int genre_ID) {
        genre_ID = this.genre_ID;
    }

    public int getGenreId() {
        return genre_ID;
    }

    public String getGenreName() {
        return genre_name;
    }

    public String getGenreDescription() {
        return genreDescription;
    }
}