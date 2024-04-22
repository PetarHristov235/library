package com.example.demo.controller;

import org.springframework.web.servlet.ModelAndView;

public class BookController {

    @GetMapping("/search")
    public ModelAndView searchBooks(@RequestParam String filterType,
                              @RequestParam String filterText) {

        ModelAndView modelAndView = new ModelAndView("filteredBooks");

        //Search by filterText in the specific type
        List<Book> filtered = null;
        if ("author".equals(filterType)) {
            filtered = bookService.searchBooksByAuthor(filterText);
        } else if ("genre".equals(filterType)) {
            filtered = bookService.searchBooksByGenre(filterText);
        } else if ("title".equals(filterType)) {
            filtered = bookService.searchBooksByTitle(filterText);
        }

        model.addAttribute("searchResults", filtered);

        return modelAndView;
    }

    @GetMapping("/randomBook")
    public ModelAndView randomBook() {

        Book randomBook = bookService.getRandomBook();

        if (randomBook != null) {
            return new ModelAndView("redirect:/bookDetails?id=" + randomBook.getId());
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @GetMapping("/bookDetails")
    public ModelAndView bookDetails(@RequestParam Long id) {
        Book book = bookService.getBookById(id);

        ModelAndView modelAndView = new ModelAndView("book_details");

        modelAndView.addObject("book", book);

        return modelAndView;
    }
}
