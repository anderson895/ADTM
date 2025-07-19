/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adtm;

/**
 *
 * @author Desktop
 */
import java.sql.*;

public class DBConnection {
   public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/atm_db", "root", "");
            System.out.println("Database Connected");
            return conn;
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e);
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e);
        }
        return null;
    }

}
