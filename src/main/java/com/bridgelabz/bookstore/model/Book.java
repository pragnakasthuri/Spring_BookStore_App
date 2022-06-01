package com.bridgelabz.bookstore.model;

import com.bridgelabz.bookstore.dto.BookDTO;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="book_db")
public @Data class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookId;
    @Column(name = "book_name")
    private String bookName;
    @Column(name = "book_author")
    private String bookAuthor;
    @Column(name = "book_description")
    private String bookDescription;
    @Column(name = "book_logo")
    private String bookLogo;
    @Column(name = "book_price")
    private int bookPrice;
    @Column(name = "book_quantity")
    private int bookQuantity;

    public Book() {
    }

    public Book(BookDTO bookDTO) {
        this.updateBook(bookDTO);
    }

    public void updateBook(BookDTO bookDTO) {
        this.bookName = bookDTO.bookName;
        this.bookAuthor = bookDTO.bookAuthor;
        this.bookDescription = bookDTO.bookDescription;
        this.bookLogo = bookDTO.bookLogo;
        this.bookPrice = bookDTO.bookPrice;
        this.bookQuantity = bookDTO.bookQuantity;
    }
}
