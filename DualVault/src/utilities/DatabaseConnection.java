package utilities;

import java.sql.*;

public class DatabaseConnection {
	private final String USERNAME = "root";
    private final String PASSWORD = "";
    private final String DATABASE = "DualVault";
    private final String HOST = "localhost:3306";
    private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
    
    private Connection con;
    private Statement st;
    public ResultSet rs;
    public ResultSetMetaData rsm;
    
    private static volatile DatabaseConnection instance;
    
    // Private Constructor untuk membuat connection
    private DatabaseConnection() {
        try {
            con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            st = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // DESIGN PATTERN
    // =================================================================
    // |                    DESIGN PATTERN SINGLETON                   |
    // =================================================================
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
	// =================================================================
    // =================================================================
    
    // Return the database connection
    public Connection getConnection() {
        return con;
    }
    
    // Making the statement to do query (SELECT, INSERT, UPDATE, DELETE, etc)
    public Statement createStatement() {
        try {
            return con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Membuat semua statement menjadi prepared statement sehingga tidak akan ada SQL injection
    public PreparedStatement prepareStatement(String query) {
        try {
            return con.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Execute Query adalah sebuah function yang melakukan query SELECT.
    // Dimana Query akan memiliki result set (rs) yang dapat dikembalikan.
    public void execQuery(String query) {
        try {
            rs = st.executeQuery(query);
            rsm = rs.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Execute Update adalah sebuah function yang melakukan query INSERT, DELETE, UPDATE.
    // Ini dilakukan untuk mengexecute query yang tidak membutuhkan result set (rs).
    public void execUpdate(String query) {
        try {
            st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
