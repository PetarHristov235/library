package com.example.demo.controller;

import com.example.demo.db.entity.BookEntity;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        List<BookEntity> books = bookService.findAllBooks();
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView searchBooks(@RequestParam String filterType,
                              @RequestParam String filterText) {

        ModelAndView modelAndView = new ModelAndView("filteredBooks");

        //Search by filterText in the specific type
        List<BookEntity> filtered = null;
        if ("author".equals(filterType)) {
            filtered = bookService.searchBooksByAuthor(filterText);
        } else if ("genre".equals(filterType)) {
            filtered = bookService.searchBooksByGenre(filterText);
        } else if ("title".equals(filterType)) {
            filtered = bookService.searchBooksByTitle(filterText);
        }


        modelAndView.addObject("searchResults", filtered);

        return modelAndView;
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

    @GetMapping("/bookDetails")
    public ModelAndView bookDetails(@RequestParam Long id) {
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
