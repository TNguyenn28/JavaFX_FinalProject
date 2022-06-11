package com.example.tnguyenpnv23a_finalproject.models;

public class Book {
    public int id;
    public String name;
    public String image;
    public String type;
    public String author;
    public int price;
    public int quantity;
    public String description;

    public Book (int id, String name, String image, String type, String author, int price, int quantity, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.type = type;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public Book (String name, String image, String type, String author, int price,int quantity, String description) {
        this.name = name;
        this.image = image;
        this.type = type;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int name) {
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
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
