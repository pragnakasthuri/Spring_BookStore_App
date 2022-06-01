package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.exception.UserRegistrationException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class BookService implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getBooks() {
        List<Book> book = (List<Book>) bookRepository.findAll();
        System.out.println("AllBook" + book);
        return book;
    }

    @Override
    public List<Book> sortBooksByPriceAsc() {
        return bookRepository.sortBooksByPriceAsc();
    }

    @Override
    public List<Book> sortBooksByPriceDsc() {
        return bookRepository.sortBooksByPriceDsc();
    }

    @Override
    public Book addBook(BookDTO bookDTO) {
        Book book = new Book(bookDTO);
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(int bookId) {
        return bookRepository.findByBookId(bookId)
                .orElseThrow(() -> new UserRegistrationException("Book  with id " + bookId + " does not exists"));
    }

    @Override
    public Book getBookByName(String bookName) {
        return bookRepository.findByBookName(bookName)
                .orElseThrow(() -> new UserRegistrationException("Book does not exists"));
    }

    @Override
    public Book updateBook(int bookId, BookDTO bookDTO) {
        Book book = this.getBookById(bookId);
        book.updateBook(bookDTO);
        return bookRepository.save(book);
    }

    @Override
    public Book updateBookQuantityById(int bookId, int bookQuantity) {
        Book book = this.getBookById(bookId);
        book.setBookQuantity(bookQuantity);
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(int bookId) {
        Book book = this.getBookById(bookId);
        bookRepository.delete(book);
    }
}
