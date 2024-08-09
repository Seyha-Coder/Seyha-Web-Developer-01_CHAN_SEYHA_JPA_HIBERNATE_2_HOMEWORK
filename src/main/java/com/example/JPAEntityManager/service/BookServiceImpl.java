package com.example.JPAEntityManager.service;

import com.example.JPAEntityManager.entity.Book;
import com.example.JPAEntityManager.exception.CustomNotfoundException;
import com.example.JPAEntityManager.request.BookRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    @Transactional
    public Book saveBook(BookRequest bookRequest) {
        Book book = new Book(null,bookRequest.getTitle(),bookRequest.getDescription(),bookRequest.getAuthor(),bookRequest.getPublicationYear());
        entityManager.persist(book);
        return book;
    }

    @Override
    public Book getBookById(UUID id) {
        Book book = entityManager.find(Book.class,id);
        if(book == null){
            throw new CustomNotfoundException("No book with that id found!");
        }
        return book;
    }

    @Override
    public List<Book> getAllBooks(Integer limit, Integer offset) {
        String query = "SELECT s FROM Book s";

        List<Book> books = entityManager.createQuery(query, Book.class)
                .setFirstResult((limit - 1) * offset) // Sets the starting position of the first result
                .setMaxResults(offset)                    // Limits the number of results returned
                .getResultList();
        return books;
    }

    @Override
    @Transactional
    public Book updateBook(UUID id, BookRequest bookRequest) {
        Book book= entityManager.find(Book.class, id);
        if(book == null){
            throw new CustomNotfoundException("No book with that id found!");
        }
        entityManager.detach(book);
        if (book != null) {
            book.setTitle(bookRequest.getTitle());
            book.setDescription(bookRequest.getDescription());
            book.setAuthor(bookRequest.getAuthor());
            book.setPublicationYear(bookRequest.getPublicationYear());
            entityManager.merge(book);
        }
        return book;
    }

    @Override
    @Transactional
    public void deleteBook(UUID id) {
        Book book = entityManager.find(Book.class, id);
        if(book == null){
            throw new CustomNotfoundException("No book with that id found!");
        }
        if (book != null) {
            entityManager.remove(book);
        }
    }

    @Override
    public List<Book> getBookByTitle(String title) {
        String query = "SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(:title)";
        List<Book> books = entityManager.createQuery(query, Book.class)
                .setParameter("title", "%" + title.toLowerCase() + "%")
                .getResultList();
        if(books.isEmpty()){
            throw new CustomNotfoundException("No book which contain title "+ title + " found!");
        }
        return books;
    }


}
