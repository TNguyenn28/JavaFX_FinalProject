package com.example.tnguyenpnv23a_finalproject;

import com.example.tnguyenpnv23a_finalproject.DBconnection.DBConnection;
import com.example.tnguyenpnv23a_finalproject.models.Book;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.StageStyle;
import java.sql.*;

public class Main extends Application {

    private Scene scene;
    private static final String EMPTY = "";
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        DBConnection DB = new DBConnection();
        ArrayList<Book> bookList = DB.getBooks();
//        DB.insertStudent(new Student("hue",9));
//        DB.updateStudent(new Student(1,"tu",10));
//        DB.deleteStudent(1);
//        DB.getStudents();

        grid.add(new Label("Name:"), 0, 0);
        var tfName = new TextField();
        grid.add(tfName, 0, 1);
        //
        grid.add(new Label("Image:"), 1, 0);
        var tfImage = new TextField();
        grid.add(tfImage, 1, 1);
        //
        grid.add(new Label("Type:"), 1, 0);
        var tfType = new TextField();
        grid.add(tfType, 1, 1);
        //
        grid.add(new Label("Author:"), 1, 0);
        var tfAuthor = new TextField();
        grid.add(tfAuthor, 1, 1);
        //
        grid.add(new Label("Price:"), 2, 0);
        var tfPrice = new TextField();
        grid.add(tfPrice, 2, 1);
        //
        grid.add(new Label("Quantity:"), 2, 0);
        var tfQuantity = new TextField();
        grid.add(tfQuantity, 2, 1);
        //
        grid.add(new Label("Description:"),3,  0);
        var tfDescription = new TextField();
        grid.add(tfDescription, 3, 1);
        //

        // add
        var btnAdd = new Button("Add");
        btnAdd.setPadding(new Insets(5, 15, 5, 15));
        btnAdd.setOnAction(e -> {
            String name = tfName.getText();
            String image = tfImage.getText();
            String type = tfType.getText();
            String author = tfAuthor.getText();
            Integer price = Integer.valueOf(tfPrice.getText());
            Integer quantity = Integer.valueOf(tfQuantity.getText());
            String description = tfDescription.getText();
            if (!name.equals(EMPTY) && !image.equals(EMPTY) && !price.equals(EMPTY) && !description.equals(EMPTY)) {
                DB.insertBook(new Book(name, image, type, author, price, quantity, description));
                try {
                    start(stage);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                return;
            }
            var alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank!");
            alert.showAndWait();
        });
        grid.add(btnAdd, 4, 1);

        //show
        for(int i = 0; i < bookList.size(); i++){

            Image image = new Image(bookList.get(i).getImage());
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(110);
            imageView.setFitHeight(110);

            grid.add(new Label (bookList.get(i).getName()), 0, i+2);
            grid.add(imageView, 1, i+2);
            grid.add(new Label ("$"+String.valueOf(bookList.get(i).getPrice())), 2, i+2);
            grid.add(new Label (bookList.get(i).getDescription()), 3, i+2);

            // Update
            var btnUpdate = new Button("Update");
            btnUpdate.setId(String.valueOf(i));
            btnUpdate.setOnAction(e -> {
                btnAdd.setVisible(false);
                int id1 = Integer.parseInt(btnUpdate.getId());
                TextField tfname = (TextField) grid.getChildren().get(1);
                tfname.setText("" + bookList.get(id1).getName());
//
                TextField tfimage = (TextField) grid.getChildren().get(3);
                tfimage.setText("" + bookList.get(id1).getImage());
//
                TextField tftype = (TextField) grid.getChildren().get(3);
                tftype.setText("" + bookList.get(id1).getImage());
//
                TextField tfauthor = (TextField) grid.getChildren().get(3);
                tfauthor.setText("" + bookList.get(id1).getImage());
//
                TextField tfprice = (TextField) grid.getChildren().get(5);
                tfprice.setText("" + bookList.get(id1).getPrice());
//
                TextField tfquantity = (TextField) grid.getChildren().get(5);
                tfquantity.setText("" + bookList.get(id1).getPrice());
//
                TextField tfdescription = (TextField) grid.getChildren().get(7);
                tfdescription.setText("" + bookList.get(id1).getDescription());
                var newbtnAdd = new Button("Update");
                newbtnAdd.setPadding(new Insets(5, 15, 5, 15));
                newbtnAdd.setOnAction(newe -> {
                    Integer Newid = bookList.get(id1).id;
                    String Newname = tfname.getText();
                    String Newimage = tfimage.getText();
                    String Newytype = tfimage.getText();
                    String Newauthor = tfimage.getText();
                    Integer Newprice = Integer.valueOf(tfprice.getText());
                    Integer Newquantity = Integer.valueOf(tfprice.getText());
                    String Newdescription = tfdescription.getText();
                    if (!Newname.equals(EMPTY) && !Newimage.equals(EMPTY) && !Newprice.equals(EMPTY) && !Newdescription.equals(EMPTY)) {
                        DB.updateBook(new Book(Newid, Newname, Newimage, Newytype, Newauthor, Newprice, Newquantity,  Newdescription));
                        try {
                            start(stage);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        return;
                    }
                    var alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all blank!");
                    alert.showAndWait();
                });
                grid.add(newbtnAdd, 4, 1);
            });
            grid.add(btnUpdate, 4, i+2);

            // Delete
            var btnDelete = new Button("Delete");
            btnDelete.setId(String.valueOf(bookList.get(i).id));
            btnDelete.setOnAction(e -> {
                int id3 = Integer.parseInt(btnDelete.getId());
                DB.deleteBook(id3);
                var alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Deleted!");
                alert.showAndWait();
                try {
                    start(stage);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
            grid.add(btnDelete, 5, i+2);
        }

        scene = new Scene(grid, 1500, 1000);
        stage.setTitle("Book");
        stage.setScene(scene);
        stage.show();
    }
}