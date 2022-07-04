package com.example.tnguyenpnv23a_finalproject;

import com.example.tnguyenpnv23a_finalproject.DBconnection.DBConnection;
import com.example.tnguyenpnv23a_finalproject.models.Admin;
import com.example.tnguyenpnv23a_finalproject.models.Book;
import com.example.tnguyenpnv23a_finalproject.models.Category;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Main extends Application {
    private Scene homepage, loginScreen, registerScreen, addScreen, updateScreen;
    private Stage window;
    private VBox formUpdate, formAdd, bookVBox ;
    private HBox root;
    DBConnection DB = new DBConnection();
    private static final String EMPTY = "";
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) {
        window = stage;

        root = new HBox(10);

        VBox formLogin = new VBox();
        formAdd = new VBox();
        VBox controlScreen = new VBox(10);
        VBox bookHBox = new VBox(10);
        bookVBox = new VBox();
        formUpdate = new VBox();

        Button btnLogOut = new Button("Log Out");
        btnLogOut.setPadding(new Insets(5, 15, 5, 15));
        btnLogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.setScene(loginScreen);
            }
        });
        Button btnAdd = new Button("Add");
        btnAdd.setPadding(new Insets(5, 15, 5, 15));
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.setScene(addScreen);
            }
        });
        controlScreen.getChildren().addAll(btnLogOut, btnAdd);

        bookHBox.getChildren().add(bookVBox);

        root.getChildren().addAll(controlScreen, bookHBox);

        showLoginPage(formLogin);
        displayHomePage(bookVBox);
        showFormAdd(formAdd);


        loginScreen = new Scene(formLogin,1200, 500);
        homepage = new Scene(root,1500, 1600);
        addScreen = new Scene(formAdd,1500, 1600);
        updateScreen = new Scene(formUpdate,1500, 1600);

        window.setTitle("Book");
        window.setScene(loginScreen);
        window.show();
    }
    public void displayHomePage (VBox vBox ) {
        vBox.getChildren().clear();
        ArrayList<Book> bookList = DB.getBooks();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);

        ChoiceBox choiceCategory = new ChoiceBox();
        ArrayList<Category> listCategories = DB.getListCategory();
        for (var category : listCategories) {
            choiceCategory.getItems().add(category.getName());
        }

        grid.add(new Label("NO" ), 0, 0);
        grid.add(new Label("Name"), 1, 0);
        grid.add(new Label("Image"), 2, 0);
        grid.add(new Label("Category"), 3, 0);
        grid.add(new Label("Author"), 4, 0);
        grid.add(new Label("Price"), 5, 0);
        grid.add(new Label("Quantity" ), 6, 0);
        grid.add(new Label("Description"), 7, 0);

        for(int i = 0; i < bookList.size(); i++) {
            Image image = new Image(bookList.get(i).getImage());
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(110);
            imageView.setFitHeight(110);

            grid.add(new Label("" + (i + 1)), 0, i + 2);
            grid.add(new Label(bookList.get(i).getName()), 1, i + 2);
            grid.add(imageView, 2, i + 2);
            grid.add(new Label(bookList.get(i).getCategoryName().getName()), 3, i + 2);
            grid.add(new Label(bookList.get(i).getAuthor()), 4, i + 2);
            grid.add(new Label("$" + bookList.get(i).getPrice()), 5, i + 2);
            grid.add(new Label("" + bookList.get(i).getQuantity()), 6, i + 2);
            grid.add(new Label(bookList.get(i).getDescription()), 7, i + 2);
            int finalI = i;
            Button btnEdit = new Button("Edit");
            btnEdit.setPadding(new Insets(5, 15, 5, 15));
            btnEdit.setOnAction(e -> {
                formUpdate(formUpdate, bookList.get(finalI));
                window.setScene(updateScreen);
            });
            grid.add(btnEdit, 8, i + 2);
            Button btnDelete = new Button("Delete");
            btnDelete.setPadding(new Insets(5, 15, 5, 15));
            btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    DB.deleteBook(bookList.get(finalI).getId());
                    vBox.getChildren().clear();
                    displayHomePage(vBox);
                }
            });
            grid.add(btnDelete, 9, i + 2);
        }
        vBox.getChildren().add(grid);
    }
    public void showLoginPage (VBox vbox) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);

        Label labelLogin =new Label("LOGIN");
        Label lName = new Label("Name: ");
        Label lPassword = new Label("Password: ");
        TextField name = new TextField();
        PasswordField  pass= new PasswordField ();
        HBox fieldName = new HBox();
        fieldName.getChildren().addAll(lName,name);
        fieldName.setSpacing(10);
        fieldName.setAlignment(Pos.BASELINE_CENTER);
        HBox fieldPass = new HBox();
        fieldPass.getChildren().addAll(lPassword,pass);
        fieldPass.setSpacing(10);
        fieldPass.setAlignment(Pos.BASELINE_CENTER);
        Button btnGoBack = new Button("Register");
        btnGoBack.setOnAction(actionEvent -> {
            window.setScene(registerScreen);
        });
        Button btnLogin = new Button("LOGIN");
        btnLogin.setOnAction(actionEvent -> {
           if (this.checkLogin(name, pass)) {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Login");
               alert.setHeaderText("Hi "+name.getText());
               alert.setContentText("Login successfully!");
//               alert.show();
               alert.showAndWait().ifPresent(response -> {
                   if (response == ButtonType.OK) {
                       name.setText("");
                       pass.setText("");
                       window.setScene(homepage);
                   }
               });

           } else {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("ERROR");
               alert.setContentText("Login fail!");
               alert.show();
           }
        });
        HBox btnLoginPage = new HBox();
        btnLoginPage.getChildren().addAll(btnLogin,btnGoBack);
        btnLoginPage.setSpacing(10);
        btnLoginPage.setAlignment(Pos.BASELINE_CENTER);
        vbox.getChildren().addAll(labelLogin,fieldName,fieldPass,btnLoginPage);
        vbox.setSpacing(15);
        vbox.setAlignment(Pos.BASELINE_CENTER);
    }
    public void showFormAdd (VBox vbox) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(new Label("Name:"), 0, 0);
        TextField tfName = new TextField();
        grid.add(tfName, 1, 0);
        //
        grid.add(new Label("Image:"), 0, 1);
        TextField tfImage = new TextField();
        grid.add(tfImage, 1, 1);
        //
        ChoiceBox choiceCategory = new ChoiceBox();
        ArrayList<Category> listCategories = DB.getListCategory();
        for (var category : listCategories){
            choiceCategory.getItems().add(category.getName());
        }
        //
        grid.add(new Label("Category:"), 0, 2);
        grid.add(choiceCategory, 1, 2);
        //
        grid.add(new Label("Author:"), 0, 3);
        TextField tfAuthor = new TextField();
        grid.add(tfAuthor, 1, 3);
        //
        grid.add(new Label("Price:"), 0, 4);
        TextField tfPrice = new TextField();
        grid.add(tfPrice, 1, 4);
        //
        grid.add(new Label("Quantity:"), 0, 5);
        TextField tfQuantity = new TextField();
        grid.add(tfQuantity, 1, 5);
        //
        grid.add(new Label("Description:"),0,  6);
        TextField tfDescription = new TextField();
        grid.add(tfDescription, 1, 6);
        //
        Button btnBack = new Button("Back Homepage");
        btnBack.setPadding(new Insets(5, 15, 5, 15));
        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.setScene(homepage);
            }
        });
        Button btnSave = new Button("save");
        btnSave.setPadding(new Insets(5, 15, 5, 15));
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = tfName.getText();
                String image = tfImage.getText();
                int idCategory = choiceCategory.getSelectionModel().getSelectedIndex()+1;
                String author = tfAuthor.getText();
                Integer price = Integer.valueOf(tfPrice.getText());
                Integer quantity = Integer.valueOf(tfQuantity.getText());
                String description = tfDescription.getText();
                if (!name.equals(EMPTY) && !image.equals(EMPTY) && !price.equals(EMPTY) && !description.equals(EMPTY)) {
                    DB.insertBook(new Book(name, image, new Category(idCategory), author, price, quantity, description));
                    displayHomePage(bookVBox);
                    window.setScene(homepage);
                }else {
                    var alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all blank!");
                    alert.showAndWait();
                }
            }
        });
        grid.add(btnBack, 0, 8);
        grid.add(btnSave, 1, 8);
        vbox.getChildren().add(grid);
    }

    public void formUpdate (VBox vbox, Book book) {
        vbox.getChildren().clear();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(new Label("Name:"), 0, 0);
        TextField tfName = new TextField();
        grid.add(tfName, 1, 0);
        //
        grid.add(new Label("Image:"), 0, 1);
        TextField tfImage = new TextField();
        grid.add(tfImage, 1, 1);
        //
        ChoiceBox choiceCategory = new ChoiceBox();
        ArrayList<Category> listCategories = DB.getListCategory();
        for (var category : listCategories){
            choiceCategory.getItems().add(category.getName());
        }
        //
        grid.add(new Label("Category:"), 0, 2);
        grid.add(choiceCategory, 1, 2);
        //
        grid.add(new Label("Author:"), 0, 3);
        TextField tfAuthor = new TextField();
        grid.add(tfAuthor, 1, 3);
        //
        grid.add(new Label("Price:"), 0, 4);
        TextField tfPrice = new TextField();
        grid.add(tfPrice, 1, 4);
        //
        grid.add(new Label("Quantity:"), 0, 5);
        TextField tfQuantity = new TextField();
        grid.add(tfQuantity, 1, 5);
        //
        grid.add(new Label("Description:"),0,  6);
        TextField tfDescription = new TextField();
        grid.add(tfDescription, 1, 6);
        //

        tfName.setText(book.getName());
        tfImage.setText(book.getImage());
        choiceCategory.setValue(book.getCategoryName().getName());
        tfAuthor.setText(book.getAuthor());
        tfPrice.setText("" + book.getPrice());
        tfQuantity.setText("" + book.getQuantity());
        tfDescription.setText(book.getDescription());
        Button btnBack = new Button("Back Homepage");
        btnBack.setPadding(new Insets(5, 15, 5, 15));
        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.setScene(homepage);
            }
        });
        Button btnUpdate= new Button("Update");
        btnUpdate.setPadding(new Insets(5, 15, 5, 15));
        btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = tfName.getText();
                String image = tfImage.getText();
                int idCategory = choiceCategory.getSelectionModel().getSelectedIndex()+1;
                String author = tfAuthor.getText();
                Integer price = Integer.valueOf(tfPrice.getText());
                Integer quantity = Integer.valueOf(tfQuantity.getText());
                String description = tfDescription.getText();
                if (!name.equals(EMPTY) && !image.equals(EMPTY) && !price.equals(EMPTY) && !description.equals(EMPTY)) {
                    DB.updateBook(book.getId(), new Book(name, image, new Category(idCategory), author, price, quantity, description));
                    displayHomePage(bookVBox);
                    window.setScene(homepage);
                }else {
                    var alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all blank!");
                    alert.showAndWait();
                }
            }
        });
        grid.add(btnBack, 0, 8);
        grid.add(btnUpdate, 1, 8);
        vbox.getChildren().add(grid);
    }

    public boolean checkLogin(TextField adminName, TextField password) {
        ArrayList<Admin> ad = new ArrayList<Admin>();
        ad = (ArrayList<Admin>) DB.getAdmin();
        String inputName = adminName.getText();
        String inputPass = password.getText();
        if(inputName.equals(ad.get(0).name)&& inputPass.equals(ad.get(0).password)){
            return true;
        }else{
            return false;
        }
    }


}