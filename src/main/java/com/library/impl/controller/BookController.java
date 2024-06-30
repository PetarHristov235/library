package com.library.impl.controller;


import com.library.impl.db.entity.BookEntity;
import com.library.impl.db.entity.RateEntity;
import com.library.impl.db.repository.BookRepository;
import com.library.impl.service.BookService;
import com.library.impl.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final BookRepository bookRepository;
    private final RateService rateService;
    private List<BookEntity> currentBooks;

    @GetMapping("/")
    public ModelAndView showHomePage(Model model) {
        currentBooks = bookService.findAllBooks();

        return new ModelAndView("index",
                "books",
                currentBooks);
    }


    @GetMapping("/books/sort")
    public ModelAndView sortBooksList(@RequestParam String sortBy, Model model) {
        if (currentBooks == null) {
            currentBooks = bookService.findAllBooks();
        }
        currentBooks = bookService.sortBooks(currentBooks, sortBy);

        return new ModelAndView("index",
                "books",
                currentBooks);
    }

    @GetMapping("/books/filter")
    public ModelAndView filterBooksList(@RequestParam String filterBy, String filterText, Model model) {
        if (currentBooks == null) {
            currentBooks = bookService.findAllBooks();
        }
        currentBooks = bookService.filterBooks(currentBooks, filterBy, filterText);

        return new ModelAndView("index",
                "books",
                currentBooks);
    }

    @GetMapping("/random")
    public ModelAndView getRandomBook() {
        BookEntity randomBook = bookService.getRandomBook();

        if (randomBook != null) {
            return new ModelAndView("redirect:/books/" + randomBook.getId());
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @GetMapping("/books/{id}")
    public ModelAndView showBookDetails(@PathVariable("id") Long id) {
        BookEntity book = bookService.getBookById(id);
        List<RateEntity> rates = rateService.findRatesByBookId(id);

        ModelAndView modelAndView = new ModelAndView("bookDetails");
        modelAndView.addObject("book", book);
        modelAndView.addObject("rates", rates);

        return modelAndView;
    }

    @GetMapping("/bookStock")
    public ModelAndView showBooksStock(Model model) {
        List<BookEntity> allBooks = bookService.findAllBooks();

        return new ModelAndView("booksStock",
                "books",
                allBooks);
    }

    @GetMapping("/addBook")
    public ModelAndView addNewBook() {
        BookEntity book = new BookEntity();

        return new ModelAndView("addBook",
                "book",
                book);
    }

    @PostMapping("/saveBook")
    public String saveNewBook(@ModelAttribute("book") BookEntity book, BindingResult result,
                              @RequestParam("image") MultipartFile image, Model model) {
        if (bookRepository.existsByTitle(book.getBookName())) {
            result.rejectValue("bookName", "error.book", "Книгата, която се опитвате да добавите," +
                    " вече съществува");
        }

        if (result.hasErrors()) {
            return "addBook";
        }

        try {
            if (!image.isEmpty()) {
                book.setCover(image.getBytes());
            }
            bookService.saveBook(book);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/";
    }

    @GetMapping("/editBook/{id}")
    public ModelAndView showEditBookForm(@PathVariable(value = "id") long id) {
        BookEntity book = bookService.getBookById(id);

        return new ModelAndView("editBook",
                "book",
                book);
    }

    @PostMapping("/editBook")
    public ModelAndView saveEditBook(@ModelAttribute BookEntity book,
                                     @RequestParam("image") MultipartFile image) {
        try {
            BookEntity existingBook = bookService.getBookById(book.getId());
            if (!image.isEmpty()) {
                existingBook.setCover(image.getBytes());
            }
            existingBook.setBookName(book.getBookName());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setGenre(book.getGenre());
            existingBook.setBookDetails(book.getBookDetails());
            existingBook.setStockCount(book.getStockCount());

            bookService.saveBook(existingBook);
        } catch (IOException e) {
            return new ModelAndView("redirect:/editBook/" + book.getId() + "?error");
        }

        return new ModelAndView("redirect:/books/" + book.getId());
    }

    @GetMapping(value = "/deleteBook/{id}")
    public ModelAndView deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);

        return new ModelAndView("redirect:/");
    }
}
