package com.example.demo.service;

import com.example.demo.db.entity.BookEntity;
import com.example.demo.db.repository.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BookServiceImpl implements  BookService {
    private final BookRepository bookRepository;

    @Override
    public List<BookEntity> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<BookEntity> searchBooksByAuthor(String author) {
        return bookRepository.searchBookByAuthor(author);
    }

    @Override
    public List<BookEntity> searchBooksByGenre(String genre) {
        return bookRepository.findBooksByGenre(genre);
    }

    @Override
    public BookEntity getRandomBook() {
        List<BookEntity> booksList = bookRepository.findAll();
        if(booksList.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(booksList.size());
        return booksList.get(randomIndex);
    }

    @Override
    public List<BookEntity> searchBooksByTitle(String title) {
        return bookRepository.findBooksByTitle(title);
    }

    @Override
    public BookEntity getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public BookEntity saveBook(BookEntity book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
