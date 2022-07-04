package com.example.tnguyenpnv23a_finalproject.DBconnection;

import com.example.tnguyenpnv23a_finalproject.models.Admin;
import com.example.tnguyenpnv23a_finalproject.models.Book;
import com.example.tnguyenpnv23a_finalproject.models.Category;

import java.sql.*;
import java.util.ArrayList;

public class DBConnection {
    private final Connection connection;

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
        String sql = "SELECT b.idBook, b.idCategory, b.name as bookName, b.image, b.author, b.price, b.quantity, b.description, c.name as categoryName FROM book as b LEFT JOIN category as c ON b.idCategory = c.idCategory ORDER BY b.idBook ASC";
        try {
            ResultSet results = connection.prepareStatement(sql).executeQuery();
            while (results.next()){
                Book book = new Book(
                        results.getInt("idBook"),
                        results.getString("bookName"),
                        results.getString("image"),
                        new Category(results.getInt("idCategory"), results.getString("categoryName")),
                        results.getString("author"),
                        results.getInt("price"),
                        results.getInt("quantity"),
                        results.getString("description")
                );
                System.out.println(book);
                list.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public ArrayList<Category> getListCategory () {
        ArrayList<Category> listCategory = new ArrayList<>();
        String sql = "SELECT * FROM category";
        try {
            ResultSet result = connection.prepareStatement(sql).executeQuery();
            while (result.next()){
                Category category = new Category(
                        result.getInt("idCategory"),
                        result.getString("name")
                );
                listCategory.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listCategory;
    }

    public Book getBookUpdate (int id) {
        String sql = "SELECT b.idBook, b.idCategory, b.name as bookName, b.image, b.author, b.price, b.quantity, b.description, c.name as categoryName FROM book as b LEFT JOIN category as c ON b.idCategory = c.idCategory WHERE idBook = " + id;
        Book book = null;
        try {
            ResultSet result = connection.prepareStatement(sql).executeQuery();
            while (result.next()) {
                book = new Book(
                        result.getString("bookName"),
                        result.getString("image"),
                        new Category(result.getInt("idCategory"), result.getString("categoryName")),
                        result.getString("author"),
                        result.getInt("price"),
                        result.getInt("quantity"),
                        result.getString("description"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(book.getCategoryName().getName());
        return book;
    }
    public void insertBook(Book book){
        String sql = "INSERT INTO book (name, image, idCategory, author, price, quantity, description) VALUE ('"+ book.getName() +"','"+ book.getImage() +"','"+book.getCategoryName().getId()+"','"+ book.getAuthor() +"','"+ book.getPrice() +"','"+ book.getQuantity() +"','"+ book.getDescription() +"')";
        System.out.println(sql);
        try {
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBook(int id, Book book){
        String sql = "UPDATE book SET name = '"+ book.getName() +"',image = '"+ book.getImage() +"', idCategory = "+book.getCategoryName().getId()+", author = '"+ book.getAuthor() +"',price = "+ book.getPrice() +", quantity ="+ book.getQuantity() +", description = '"+ book.getDescription() +"' WHERE idBook = "+ id;
        System.out.println(sql);
        try {
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBook(int id){
        String sql = "DELETE FROM book WHERE idBook = "+ id;
        try {
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Admin> getAdmin() {
        ArrayList<Admin> admins = new ArrayList<>();
        try {
            var result = this.connection.prepareStatement("Select * from admin").executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("userName");
                String password = result.getString("password");
                admins.add(new Admin(id, name, password));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return admins;
    }
}