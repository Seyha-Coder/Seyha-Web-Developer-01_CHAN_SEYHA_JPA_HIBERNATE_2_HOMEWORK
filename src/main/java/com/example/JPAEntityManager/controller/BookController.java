package com.example.JPAEntityManager.controller;

import com.example.JPAEntityManager.entity.Book;
import com.example.JPAEntityManager.request.BookRequest;
import com.example.JPAEntityManager.service.BookService;
import com.example.JPAEntityManager.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody BookRequest bookRequest) {
        Book book = bookService.saveBook(bookRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Inserted new book successfully.")
                .status(HttpStatus.CREATED)
                .code(201)
                .payload(book)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable UUID id) {
        Book book = bookService.getBookById(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Book with id " + id + " retrieved successfully.")
                .status(HttpStatus.OK)
                .code(200)
                .payload(book)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/title")
    public ResponseEntity<ApiResponse<List<Book>>> getBookByTitle(@RequestParam String title) {
        List<Book> books = bookService.getBookByTitle(title);
        ApiResponse<List<Book>> apiResponse = ApiResponse.<List<Book>>builder()
                .message("The book which contain title "+ title + " retrieved successfully.")
                .status(HttpStatus.OK)
                .code(200)
                .payload(books)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping
    public ResponseEntity<?> getAllBooks(
            @RequestParam(defaultValue = "1") Integer limit,
            @RequestParam(defaultValue = "5") Integer offset
    ) {
        List<Book> books = bookService.getAllBooks(limit,offset);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Retrieved all books successfully.")
                .status(HttpStatus.OK)
                .code(200)
                .payload(books)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable UUID id, @RequestBody BookRequest bookRequest) {
        Book book = bookService.updateBook(id, bookRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("The book has been updated successfully.")
                .status(HttpStatus.OK)
                .code(200)
                .payload(book)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Deleted book successfully.")
                .status(HttpStatus.OK)
                .code(200)
                .dateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
