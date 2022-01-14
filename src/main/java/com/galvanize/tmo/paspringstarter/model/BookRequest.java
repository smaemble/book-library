package com.galvanize.tmo.paspringstarter.model;

import java.io.Serializable;

public class BookRequest implements Serializable {
    private String title;
    private String author;
    private Integer yearPublished;

    public BookRequest(){

    }

    public BookRequest(String title, String author, Integer yearPublished) {
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
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

    public Integer getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(Integer yearPublished) {
        this.yearPublished = yearPublished;
    }

    @Override
    public String toString() {
        return "BookRequest{" +
                "title='" + title + '\'' +
                ", auhtor='" + author + '\'' +
                ", yearPublished=" + yearPublished +
                '}';
    }
}
