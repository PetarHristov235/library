package com.example.demo.controller;

import com.example.demo.db.entity.BookEntity;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private List<BookEntity> currentBooks;

    @GetMapping("/")
    public String index(Model model) {
        if (currentBooks == null) {
            currentBooks = bookService.findAllBooks();
        }
        model.addAttribute("books", currentBooks);
        return "index";
    }

    @GetMapping("/books/restart")
    public String showBooksStart(Model model) {
        model.addAttribute("books", bookService.findAllBooks());
        return "index";
    }

    @GetMapping("/books/sort")
    public String sortBooksList(@RequestParam String sortBy, Model model) {
        if (currentBooks == null) {
            currentBooks = bookService.findAllBooks();
        }
        currentBooks = bookService.sortBooks(currentBooks, sortBy);
        model.addAttribute("books", currentBooks);
        return "index";
    }

    @GetMapping("/books/filter")
    public String filterBooksList(@RequestParam String filterBy,String filterText, Model model) {
        if (currentBooks == null) {
            currentBooks = bookService.findAllBooks();
        }

        currentBooks = bookService.filterBooks(currentBooks, filterBy,filterText);
        model.addAttribute("books", currentBooks);
        return "index";
    }

    @GetMapping("/random")
    public ModelAndView randomBook() {

        BookEntity randomBook = bookService.getRandomBook();

        if (randomBook != null) {
            return new ModelAndView("redirect:/bookDetails?id=" + randomBook.getId());
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @GetMapping("/books/{id}")
    public ModelAndView bookDetails(@PathVariable Long id) {
        BookEntity book = bookService.getBookById(id);

        ModelAndView modelAndView = new ModelAndView("bookDetails");

        modelAndView.addObject("book", book);

        return modelAndView;
    }

    @GetMapping("/addBook")
    public ModelAndView addBook(){
        BookEntity book = new BookEntity();
        ModelAndView modelAndView = new ModelAndView("addBook");
        modelAndView.addObject("book", book);
        return modelAndView;
    }

    @PostMapping("/saveBook")
    public String addBook(@ModelAttribute("book") BookEntity book) {

        bookService.saveBook(book);
        return "redirect:/";
    }

    @GetMapping("/editBook/{id}")
    public ModelAndView editBook(@PathVariable(value="id")int id) {
        BookEntity book=bookService.findAllBooks().get(id);

        ModelAndView modelAndView = new ModelAndView("editBook");
        modelAndView.addObject("book",book);
        return modelAndView;
    }

    @PostMapping("/saveEdit")
    public String saveBook(@ModelAttribute("book") BookEntity book) {

        bookService.saveBook(book);

        return "redirect:/";
    }

    @GetMapping(value="/deleteBook/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return "redirect:/";
    }
}
