package com.bridgelabz.bookstore.repository;

import com.bridgelabz.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository  extends JpaRepository<Book,Integer> {
    Optional<Book> findByBookId(int bookId);
    Optional<Book> findByBookName(String bookName);


    @Query(value = "SELECT * FROM book_db order by book_price ASC", nativeQuery = true)
    List<Book> sortBooksByPriceAsc();

    @Query(value = "SELECT * FROM book_db order by book_price DESC ", nativeQuery = true)
    List<Book> sortBooksByPriceDsc();
}
