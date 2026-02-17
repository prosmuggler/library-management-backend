import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/librarydb";
    private static final String USER = "root";
    private static final String PASSWORD = "Password"; // removed password for security

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                System.out.println("Attempting connection to: " + URL);
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connection successful!");
            } catch (SQLException e) {
                System.out.println("Database connection failed!");
                System.out.println("Error: " + e.getMessage());
                System.out.println("SQL State: " + e.getSQLState());
                System.out.println("Error Code: " + e.getErrorCode());
            }
        }
        return connection;
    }
}
