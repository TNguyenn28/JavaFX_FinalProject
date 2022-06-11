package com.example.tnguyenpnv23a_finalproject.DBconnection;
//import java.sql.*;
//
//public class DbConnection {
//  public Connection con;
//
//    public static final String URL = "jdbc:mysql://localhost/pnvstudent";
//    public static final String USERNAME = "" ;
//    public static final String PASSWORD = "";
//
//    public DbConnection() {
//        try {
//            con = DriverManager.getConnection(URL,USERNAME, PASSWORD);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//}
import com.example.tnguyenpnv23a_finalproject.models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private Connection connection;

    public static final String URL = "jdbc:mysql://localhost/bookmanagement";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    public DBConnection(){
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connect successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Book> getBooks(){
        ArrayList<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM book";
        try {
            ResultSet results = connection.prepareStatement(sql).executeQuery();
            while (results.next()){
//                System.out.println(results.getInt("id"));
//                System.out.println(results.getString("name"));
//                System.out.println(results.getFloat("score"));
                Book book = new Book(
                        results.getInt("id"),
                        results.getString("name"),
                        results.getString("image"),
                        results.getString("type"),
                        results.getString("author"),
                        results.getInt("price"),
                        results.getInt("quantity"),
                        results.getString("description")
                );
                list.add(book);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
    public void insertBook(Book book){
        String sql = "INSERT INTO book (name, image, type, author, price, quantity, description) VALUE ('"+book.name+"','"+book.image+"','"+book.type+"','"+book.author+"','"+book.price+"','"+book.quantity+"','"+book.description+"')";
        try {
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBook(Book book){
        String sql = "UPDATE book SET name = '"+book.name+"','"+book.image+"','"+book.type+"','"+book.author+"','"+book.price+"','"+book.quantity+"','"+book.description+"' WHERE id = "+ book.id;
        try {
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBook(int id){
        String sql = "DELETE FROM book WHERE id = "+ id;
        try {
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}