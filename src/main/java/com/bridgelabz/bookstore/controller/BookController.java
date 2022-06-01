package com.bridgelabz.bookstore.controller;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.service.IBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/book")
public class BookController {

    @Autowired
    public IBookService bookService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addBook(@Valid @RequestBody BookDTO bookDTO) {
        Book book = bookService.addBook(bookDTO);
        log.debug("Data" + book);
        ResponseDTO dto = new ResponseDTO("Book Added Successfully", book);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseDTO> getBooks() {
        List<Book> bookList = bookService.getBooks();
        ResponseDTO responseDTO = new ResponseDTO("Get call successful:", bookList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{bookId}")
    public ResponseEntity<ResponseDTO> getBookById(@PathVariable int bookId) {
        Book book = bookService.getBookById(bookId);
        ResponseDTO responseDTO = new ResponseDTO("Get call successful for Id " + bookId, book);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<ResponseDTO> deleteBook(@PathVariable("bookId") int bookId) {
        bookService.deleteBook(bookId);
        ResponseDTO responseDTO = new ResponseDTO("Delete call success for id ", "deleted id:" + bookId);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);

    }

    @GetMapping("/getbyname/{bookName}")
    public ResponseEntity<ResponseDTO> getBookByName(@PathVariable String bookName) {
        Book book = bookService.getBookByName(bookName);
        ResponseDTO responseDTO = new ResponseDTO("Get call successful " + bookName, book);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity<ResponseDTO> updateBook(@PathVariable("bookId") int bookId,
                                                      @Valid @RequestBody BookDTO bookDTO) {
        Book book = bookService.updateBook(bookId, bookDTO);
        log.debug(" After Update " + book.toString());
        ResponseDTO responseDTO = new ResponseDTO("Updated Book details successfully for Id " + bookId, book);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);

    }

    @PutMapping("/updatequantity/{bookId}/{bookQuantity}")
    public ResponseEntity<ResponseDTO> updateBookQuantityById(@PathVariable int bookId, @PathVariable int bookQuantity) {
        Book book = bookService.updateBookQuantityById(bookId, bookQuantity);
        ResponseDTO responseDTO = new ResponseDTO("Updated Book Quantity Successfully for Id " + bookId, book);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/sortbyasc")
    public ResponseEntity<ResponseDTO> sortBooksByPriceAsc() {
        List<Book> bookList = bookService.sortBooksByPriceAsc();
        if (!bookList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO("Books Found", bookList));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO("No Books Found", HttpStatus.NOT_FOUND.value()));
    }

    @GetMapping("/sortbydsc")
    public ResponseEntity<ResponseDTO> sortBooksByPriceDsc() {
        List<Book> bookList = bookService.sortBooksByPriceDsc();
        if (!bookList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO("Books Found", bookList));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO("No Books Found", HttpStatus.NOT_FOUND.value()));
    }
}