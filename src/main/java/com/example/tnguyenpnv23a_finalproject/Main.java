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
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Main extends Application {
    private Scene homepage, loginScreen, registerScreen, addScreen, updateScreen;
    private Stage window;
    private VBox formUpdate, formAdd, bookVBox, formRegister, formLogin ;
    private HBox root;
    DBConnection DB = new DBConnection(); //tao doi tuong Db connect moi
    private static final String EMPTY = "";
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) {
        window = stage;

        root = new HBox(10);

        formAdd = new VBox();
        formRegister = new VBox();
        formLogin = new VBox();
        VBox controlScreen = new VBox(10);
        VBox bookHBox = new VBox(10);
        bookVBox = new VBox();
        formUpdate = new VBox();



        Button btnLogOut = new Button("Log Out");
        btnLogOut.setStyle("-fx-background-color: #FF6600");
        btnLogOut.setPadding(new Insets(5, 15, 5, 15));
        btnLogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.setScene(loginScreen);
            }
        });
        Button btnAdd = new Button("Add");
        btnAdd.setStyle("-fx-background-color: #6666FF");
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
        showRegisterForm(formRegister);


        loginScreen = new Scene(formLogin,1200, 800);
        homepage = new Scene(root,1200, 800);
        addScreen = new Scene(formAdd,1200, 800);
        updateScreen = new Scene(formUpdate,1200, 800);
        registerScreen = new Scene(formRegister,1200, 800);

//        loginScreen.setFill(Color.web("#C6E2FF"));

        window.setTitle("Book");
        window.setScene(homepage);
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
            imageView.setFitWidth(130);
            imageView.setFitHeight(140);

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
            btnEdit.setStyle("-fx-background-color: #FFFF33");
            btnEdit.setPadding(new Insets(5, 15, 5, 15));
            btnEdit.setOnAction(e -> {
                formUpdate(formUpdate, bookList.get(finalI));
                window.setScene(updateScreen);
            });
            grid.add(btnEdit, 8, i + 2);
            Button btnDelete = new Button("Delete");
            btnDelete.setStyle("-fx-background-color: #FF3300");
            btnDelete.setPadding(new Insets(5, 15, 5, 15));
            btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete File");
                    alert.setHeaderText("Are you sure want to move this item to the Recycle Bin?");
                    alert.setContentText("C:/MyFile.txt");
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                            alert2.setTitle("Inform");
                            alert2.setHeaderText("This item is moved to the Recycle Bin!");
                            alert2.setContentText("Continue!");
                            DB.deleteBook(bookList.get(finalI).getId());
                            vBox.getChildren().clear();
                            displayHomePage(vBox);
                            alert2.show();
                        } else {
                            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                            alert2.setTitle("Inform");
                            alert2.setHeaderText("This item isn't moved to the Recycle Bin!");
                            alert2.setContentText("Continue!");
                            alert2.show();
                        }
                    });
                }
            });
            grid.add(btnDelete, 9, i + 2);
        }
        ScrollPane scrollHomepage = new ScrollPane(grid);
        scrollHomepage.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollHomepage.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollHomepage.setContent(grid);
        vBox.getChildren().add(scrollHomepage);
    }
    public void showLoginPage (VBox vbox) {
        vbox.getChildren().clear();
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
        btnGoBack.setStyle("-fx-background-color: #FF9966");
        btnGoBack.setOnAction(actionEvent -> {
            window.setScene(registerScreen);
        });
        Button btnLogin = new Button("LOGIN");
        btnLogin.setStyle("-fx-background-color: #008800");
        btnLogin.setOnAction(actionEvent -> {
           if (this.checkLogin(name, pass)) {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Login");
               alert.setHeaderText("Hello "+name.getText());
               alert.setContentText("Login successfully!");
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

        //lay category trong mang category cho hien ra cac option
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
        Button btnSave = new Button("Save");
        btnSave.setPadding(new Insets(5, 15, 5, 15));
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (checkEmpty(tfName) && checkEmpty(tfImage) && checkEmpty(tfPrice) && checkEmpty(tfDescription) && checkEmpty(tfQuantity)) {
                    if (checkFormat(tfPrice.getText())){
                        if (checkFormat(tfQuantity.getText())){
                            String name = tfName.getText();
                            String image = tfImage.getText();
                            int idCategory = choiceCategory.getSelectionModel().getSelectedIndex()+1;
                            String author = tfAuthor.getText();
                            Integer price = Integer.valueOf(tfPrice.getText());
                            Integer quantity = Integer.valueOf(tfQuantity.getText());
                            String description = tfDescription.getText();
                            DB.insertBook(new Book(name, image, new Category(idCategory), author, price, quantity, description));
                            displayHomePage(bookVBox);
                            window.setScene(homepage);
                        }else {
                            var alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setContentText("Please input correct type of Quantity!");
                            alert.showAndWait();
                        }
                    }else {
                        var alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Please input correct type of Price!");
                        alert.showAndWait();
                    }
                }else {
                    var alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill in all blank!");
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
                if (checkEmpty(tfName) && checkEmpty(tfImage) && checkEmpty(tfPrice) && checkEmpty(tfDescription) && checkEmpty(tfQuantity)) {
                    if (checkFormat(tfPrice.getText())){
                        if (checkFormat(tfQuantity.getText())){
                            String name = tfName.getText();
                            String image = tfImage.getText();
                            int idCategory = choiceCategory.getSelectionModel().getSelectedIndex()+1;
                            String author = tfAuthor.getText();
                            Integer price = Integer.valueOf(tfPrice.getText());
                            Integer quantity = Integer.valueOf(tfQuantity.getText());
                            String description = tfDescription.getText();
                            DB.updateBook(book.getId(), new Book(name, image, new Category(idCategory), author, price, quantity, description));
                            displayHomePage(bookVBox);
                            window.setScene(homepage);
                        }else {
                            var alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setContentText("Please input correct type of Quantity!");
                            alert.showAndWait();
                        }
                    }else {
                        var alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Please input correct type of Price!");
                        alert.showAndWait();
                    }
                }else {
                    var alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill in all blank!");
                    alert.showAndWait();
                }
            }
        });
        grid.add(btnBack, 0, 8);
        grid.add(btnUpdate, 1, 8);
        vbox.getChildren().add(grid);
    }

    public void showRegisterForm (VBox registerView) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        Label labelRegister =new Label("REGISTER");
        Label LabelName = new Label("Name: ");
        Label LabelPassword = new Label("Password: ");
        Label LabelRePassword = new Label("RePassword: ");
        TextField name = new TextField();
        PasswordField password= new PasswordField();
        PasswordField rePassword= new PasswordField();
        Button btnRegister = new Button("Register");
        Button btnLogin = new Button("Back Login");
        btnRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (checkEmpty(name) && checkEmpty(password) && checkEmpty(rePassword)){
                    if (checkCorrectRePass(password.getText(), rePassword.getText())){
                        DB.registerAdmin(new Admin(name.getText(), password.getText()));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Register");
                        alert.setHeaderText("Congratulations! Register successfully!");
                        alert.setContentText("Please Login");
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                window.setScene(loginScreen);
                            }
                        });
                    }else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Password is not correct with RePassword");
                        alert.setContentText("Please enter password again.");
                        alert.show();
                    }
                }else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Empty Input");
                    alert.setContentText("Please enter all field.");
                    alert.show();
                }

            }
        });

        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                name.setText("");
                password.setText("");
                rePassword.setText("");
                window.setScene(loginScreen);
            }
        });
        grid.add(labelRegister, 0,0);
        grid.add(LabelName, 0, 1);
        grid.add(name, 1,1);
        grid.add(LabelPassword, 0, 2);
        grid.add(password, 1, 2);
        grid.add(LabelRePassword, 0, 3);
        grid.add(rePassword, 1, 3);
        grid.add(btnLogin, 0, 4);
        grid.add(btnRegister, 1,4);
        registerView.getChildren().add(grid);
        registerView.setSpacing(15);
        registerView.setAlignment(Pos.BASELINE_CENTER);
    }

    public boolean checkEmpty(TextField tf){
        if (tf.getText().isEmpty()){
            return false;
        }
        return true;
    }
    public boolean checkCorrectRePass(String pass, String rePass){
        if(pass.equals(rePass)){
            return true;
        }
        return false;
    }
    public boolean checkLogin(TextField adminName, TextField password) {
        ArrayList<Admin> ad = DB.getAdmin();
        for(var admin : ad){
            System.out.printf(admin.name);
            System.out.printf(admin.password);
            if(adminName.getText().equals(admin.name) && password.getText().equals(admin.password)){
                return true;
            }
        }
        return false;
    }
    public boolean checkFormat (String  value) {
        String regex = "[-+]?(\\d*\\.\\d+|\\d+)";
        Pattern p = Pattern.compile(regex);

        // Creates a matcher that will match input1 against regex
        Matcher m = p.matcher(value);

        // If match found and equal to input1
        if(m.find() && m.group().equals(value)){
            return true;
        }
        return false;
    }
}