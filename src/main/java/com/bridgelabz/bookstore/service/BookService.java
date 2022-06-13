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

    /**
     * Implemented getBooks method to find all books
     * @return
     */
    @Override
    public List<Book> getBooks() {
        List<Book> book = (List<Book>) bookRepository.findAll();
        System.out.println("AllBook" + book);
        return book;
    }

    /**
     * Implemented sortBooksByPriceAsc to sort books in ascending order by book name
     * @return
     */
    @Override
    public List<Book> sortBooksByPriceAsc() {
        return bookRepository.sortBooksByPriceAsc();
    }

    /**
     * Implemented sortBooksByPriceDsc to sort books in descending order by book name
     * @return
     */
    @Override
    public List<Book> sortBooksByPriceDsc() {
        return bookRepository.sortBooksByPriceDsc();
    }

    /**
     * Implemented addBook method to add book
     * @param bookDTO - passing bookDTO param
     * @return
     */
    @Override
    public Book addBook(BookDTO bookDTO) {
        Book book = new Book(bookDTO);
        return bookRepository.save(book);
    }

    /**
     * Implemented getBookById method to find book by id
     * @param bookId - passing bookId param
     * @return
     */
    @Override
    public Book getBookById(int bookId) {
        return bookRepository.findByBookId(bookId)
                .orElseThrow(() -> new UserRegistrationException("Book  with id " + bookId + " does not exists"));
    }

    @Override
    public Book getBookByName(String bookName) {
        return bookRepository.findByBookName(bookName)
                .orElseThrow(() -> new UserRegistrationException("Sorry Book does not exists"));
    }

    /**
     * Implemented getByAuthorName method to find book by author
     * @param authorName - passing bookAuthor param
     * @return
     */
    @Override
    public Book getByAuthorName(String authorName) {
        return bookRepository.findByAuthorName(authorName)
                .orElseThrow(() -> new UserRegistrationException("Sorry Book does not exists"));
    }

    /**
     * Implemented updateBook method to update book by id
     * @param bookId - passing bookId param
     * @param bookDTO - passing bookDTO param
     * @return
     */
    @Override
    public Book updateBook(int bookId, BookDTO bookDTO) {
        Book book = this.getBookById(bookId);
        book.updateBook(bookDTO);
        return bookRepository.save(book);
    }

    /**
     * Implemented updateBookQuantityById to update book quantity
     * @param bookId
     * @param bookQuantity
     * @return
     */
    @Override
    public Book updateBookQuantityById(int bookId, int bookQuantity) {
        Book book = this.getBookById(bookId);
        book.setBookQuantity(bookQuantity);
        return bookRepository.save(book);
    }

    /**
     * Implemented deleteBook method to delete book by id
     * @param bookId - passing bookId param
     */
    @Override
    public void deleteBook(int bookId) {
        Book book = this.getBookById(bookId);
        bookRepository.delete(book);
    }
}
