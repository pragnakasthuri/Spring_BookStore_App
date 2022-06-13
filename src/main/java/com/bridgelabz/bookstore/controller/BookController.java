package com.bridgelabz.bookstore.controller;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.service.IBookService;
import com.bridgelabz.bookstore.util.TokenUtil;
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

    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addBook(@Valid @RequestBody BookDTO bookDTO) {
        Book book = bookService.addBook(bookDTO);
        String token = tokenUtil.createToken(book.getBookId());
        log.debug("Data" + book);
        ResponseDTO dto = new ResponseDTO("Book Added Successfully", book, token);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseDTO> getBooks() {
        List<Book> bookList = bookService.getBooks();
        ResponseDTO responseDTO = new ResponseDTO("Get call successful:", bookList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{token}")
    public ResponseEntity<ResponseDTO> getBookById(@PathVariable String token) {
        int tokenId = tokenUtil.decodeToken(token);
        Book book = bookService.getBookById(tokenId);
        ResponseDTO responseDTO = new ResponseDTO("Get call successful for Id " + tokenId, book);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{token}")
    public ResponseEntity<ResponseDTO> deleteBook(@PathVariable("token") String token) {
        int tokenId = tokenUtil.decodeToken(token);
        bookService.deleteBook(tokenId);
        ResponseDTO responseDTO = new ResponseDTO("Delete call successful for Id ", "deleted id:" + tokenId);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getbyname/{bookName}")
    public ResponseEntity<ResponseDTO> getBookByName(@PathVariable String bookName) {
        Book book = bookService.getBookByName(bookName);
        ResponseDTO responseDTO = new ResponseDTO("Get call successful for " + bookName, book);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getbyauthor/{authorName}")
    public ResponseEntity<ResponseDTO> getBookByAuthorName(@PathVariable String authorName) {
        Book book = bookService.getByAuthorName(authorName);
        ResponseDTO responseDTO = new ResponseDTO("Get call successful for " + authorName, book);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{token}")
    public ResponseEntity<ResponseDTO> updateBook(@PathVariable("token") String token,
                                                      @Valid @RequestBody BookDTO bookDTO) {
        int tokenId = tokenUtil.decodeToken(token);
        Book book = bookService.updateBook(tokenId, bookDTO);
        log.debug(" After Update " + book.toString());
        ResponseDTO responseDTO = new ResponseDTO("Updated Book details successfully for Id " + tokenId, book);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/updatequantity/{bookQuantity}/{token}")
    public ResponseEntity<ResponseDTO> updateBookQuantityById(@PathVariable String token, @PathVariable int bookQuantity) {
        int tokenId = tokenUtil.decodeToken(token);
        Book book = bookService.updateBookQuantityById(tokenId, bookQuantity);
        ResponseDTO responseDTO = new ResponseDTO("Updated Book Quantity Successfully for Id " + tokenId, book);
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