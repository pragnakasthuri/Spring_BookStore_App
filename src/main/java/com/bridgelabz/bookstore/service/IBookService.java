package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.model.Book;

import java.util.List;

public interface IBookService {

    List<Book> getBooks();

    Book addBook( BookDTO bookDTO);

    Book getBookById(int bookId);

    Book getBookByName(String bookName);

    Book updateBook(int bookId, BookDTO bookDTO);

    void deleteBook(int bookId);

    Book updateBookQuantityById(int bookId, int bookQuantity);

    List<Book> sortBooksByPriceAsc();

    List<Book> sortBooksByPriceDsc();
}
