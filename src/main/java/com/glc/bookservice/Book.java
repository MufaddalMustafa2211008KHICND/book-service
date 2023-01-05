package com.glc.bookservice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String author;
    private int year;
    private int pages;

    protected Book() {}

    public Book(Long id, String title, String author, int year, int pages){
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
    }
    
}
