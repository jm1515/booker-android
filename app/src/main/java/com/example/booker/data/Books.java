package com.example.booker.data;

import com.google.gson.annotations.SerializedName;

public class Books {

    @SerializedName("title")
    private String title;

    @SerializedName("author")
    private String author;

    @SerializedName("bestSeller")
    private boolean bestSeller;

    @SerializedName("price")
    private String price;

    @SerializedName("year")
    private String year;

    @SerializedName("description")
    private String description;

    @SerializedName("comment")
    private String comment;

    public Books(String title, String author, String bestSeller, String year, String price,
                 String description, String comment) {
        this.title = title;
        this.author = author;
        setBestSeller(bestSeller);
        this.price = price;
        this.year = year;
        this.description = description;
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBestSeller() {
        return bestSeller;
    }

    public void setBestSeller(String bestSeller) {
        this.bestSeller = (bestSeller.equals("Oui") ? true : false);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
