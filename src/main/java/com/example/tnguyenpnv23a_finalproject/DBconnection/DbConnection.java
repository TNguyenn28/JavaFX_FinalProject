package com.example.tnguyenpnv23a_finalproject.DBconnection;
import java.sql.*;

public class DbConnection {
  public Connection con;

    public static final String URL = "jdbc:mysql://localhost/pnvstudent";
    public static final String USERNAME = "" ;
    public static final String PASSWORD = "";

    public DbConnection() {
        try {
            con = DriverManager.getConnection(URL,USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
