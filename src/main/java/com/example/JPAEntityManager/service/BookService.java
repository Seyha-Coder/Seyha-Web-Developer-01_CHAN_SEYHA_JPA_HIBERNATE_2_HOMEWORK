package com.example.JPAEntityManager.service;

import com.example.JPAEntityManager.entity.Book;
import com.example.JPAEntityManager.request.BookRequest;

import java.util.List;
import java.util.UUID;

public interface BookService {
    Book saveBook(BookRequest studentRequest);
    Book getBookById(UUID id);
    List<Book> getAllBooks(Integer limit, Integer offset);
    Book updateBook(UUID id, BookRequest bookRequest);
    void deleteBook(UUID id);
    List<Book> getBookByTitle(String title);
}
