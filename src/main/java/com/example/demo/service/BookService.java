package com.example.demo.service;

import com.example.demo.db.entity.BookEntity;

import java.util.List;

public interface BookService {
    List<BookEntity> findAllBooks();
    List<BookEntity> searchBooksByAuthor(String author);
    List<BookEntity> searchBooksByGenre(String genre);
    BookEntity getRandomBook();
    List<BookEntity> searchBooksByTitle(String title);
    BookEntity getBookById(Long id);
    BookEntity saveBook(BookEntity book);
    void deleteBookById(Long id);
}