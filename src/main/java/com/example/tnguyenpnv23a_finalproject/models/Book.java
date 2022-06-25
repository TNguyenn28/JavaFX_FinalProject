package com.example.tnguyenpnv23a_finalproject.models;

public class Book {
    public String type;
    private int id;
    private String name;
    private String image;
    private Category categoryName;
    private String author;
    private int price;
    private int quantity;
    private String description;

    public Book (int id, String name, String image, Category categoryName, String author, int price, int quantity, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.categoryName = categoryName;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public Book (String name, String image, Category categoryName, String author, int price,int quantity, String description) {
        this.name = name;
        this.image = image;
        this.categoryName = categoryName;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getImage() {

        return image;
    }

    public void setImage(String image) {

        this.image = image;
    }
    public Category getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(Class studentClass) {

        this.categoryName = categoryName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public int getPrice() {

        return price;
    }

    public void setPrice(int price) {

        this.price = price;
    }
    public int getQuantity() {

        return quantity;
    }

    public void setQuantity(int quantity) {

        this.quantity = quantity;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }
}
