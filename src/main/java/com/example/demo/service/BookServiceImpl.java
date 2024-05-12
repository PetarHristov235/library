package com.example.demo.service;

import com.example.demo.db.entity.BookEntity;
import com.example.demo.db.repository.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public List<BookEntity> sortBooks(List<BookEntity> booksList, String sortBy) {
        if (isValid(sortBy)) {
            switch (sortBy) {
                case "title":
                    booksList.sort(Comparator.comparing(BookEntity::getTitle));
                    break;
                case "author":
                    booksList.sort(Comparator.comparing(BookEntity::getAuthor));
                    break;
                case "genre":
                    booksList.sort(Comparator.comparing(BookEntity::getGenre));
                    break;
            }
        }
        return booksList;
    }

    @Override
    public List<BookEntity> filterBooks(List<BookEntity> booksList, String filterBy, String filterText) {
        if (isValid(filterBy) && isValid(filterText)) {
            switch (filterBy) {
                case "author":
                    booksList = filterByAuthor(booksList, filterText);
                    break;
                case "title":
                    booksList = filterByTitle(booksList, filterText);
                    break;
                case "genre":
                    booksList = filterByGenre(booksList, filterText);
                    break;
            }
        }
        return booksList;
    }

    private List<BookEntity> filterByAuthor(List<BookEntity> books, String author) {
        List<BookEntity> filteredBooks = new ArrayList<>();
        for (BookEntity book : books) {
            if (matchesIgnoreCaseAndPartial(book.getAuthor(), author)) {
                filteredBooks.add(book);
            }
        }
        return filteredBooks;
    }

    private List<BookEntity> filterByTitle(List<BookEntity> books, String title) {
        List<BookEntity> filteredBooks = new ArrayList<>();
        for (BookEntity book : books) {
            if (matchesIgnoreCaseAndPartial(book.getTitle(), title)) {
                filteredBooks.add(book);
            }
        }
        return filteredBooks;
    }

    private List<BookEntity> filterByGenre(List<BookEntity> books, String genre) {
        List<BookEntity> filteredBooks = new ArrayList<>();
        for (BookEntity book : books) {
            if (matchesIgnoreCaseAndPartial(book.getGenre(), genre)) {
                filteredBooks.add(book);
            }
        }
        return filteredBooks;
    }

    private boolean matchesIgnoreCaseAndPartial(String text, String keyword) {
        String lowercaseText = text.toLowerCase();
        String lowercaseKeyword = keyword.toLowerCase();

        return lowercaseText.contains(lowercaseKeyword);
    }

    private boolean isValid(String str){
        return str != null && !str.isEmpty();
    }
}
