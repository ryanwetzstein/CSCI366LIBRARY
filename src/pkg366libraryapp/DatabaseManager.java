package pkg366libraryapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

        private static String jdbcURL = "jdbc:postgresql://localhost:5432/LibraryAdmin";
        private static String username = "LibraryAdmin";
        private static String password = "12345!";

        private static Connection connection;

        public static Connection getConnection() {
            if (connection == null) {
                setConnection();
            }
            return connection;
        }

        private static void setConnection() {
            try {
                // load the driver
                Class.forName("org.postgresql.Driver");
                // connect to the database
                connection = DriverManager.getConnection(jdbcURL, username, password);

            } catch (ClassNotFoundException e) {
                System.out.println("Cannot load the driver");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Got a SQL exception.");
                e.printStackTrace();
            }
        }
    }
