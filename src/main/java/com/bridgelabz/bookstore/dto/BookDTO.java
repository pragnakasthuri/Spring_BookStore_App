package com.bridgelabz.bookstore.dto;

import lombok.Data;

public @Data
class BookDTO {
    public String bookName;
    public String bookAuthor;
    public String bookDescription;
    public String bookLogo;
    public int bookPrice;
    public int bookQuantity;
}
