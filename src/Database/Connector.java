package Database;

import java.sql.*;

public class Connector {

    private static String jdbcDriver = "com.mysql.jdbc.Driver";
    private static String namadb = "database_ramalan";
    private static String urldb = "jdbc:mysql://localhost:3306/" + namadb;
    private static String username_db = "root";
    private static String password_db = "";

    static Connection conn;

    public static Connection Connect() {
        try{
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(urldb,username_db,password_db);
        }catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error : " + e.getLocalizedMessage());
        }
        return conn;
    }
}
