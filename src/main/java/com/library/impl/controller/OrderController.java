package com.library.impl.controller;

import com.library.impl.db.entity.BookEntity;
import com.library.impl.db.entity.OrderEntity;
import com.library.impl.db.entity.UserEntity;
import com.library.impl.service.BookService;
import com.library.impl.service.OrderService;
import com.library.impl.service.UserService;
import com.library.impl.util.DataValidation;
import com.library.impl.util.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @GetMapping(value = "/orderBook/{id}")
    public ModelAndView showOrderForm(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("confirmOrder");

        modelAndView.addObject("book", bookService.getBookById(id));
        modelAndView.addObject("order", new OrderEntity());

        return modelAndView;
    }

    @PostMapping(value="/confirmOrder/{id}")
    public ModelAndView confirmOrder(@PathVariable Long id,
                                    @ModelAttribute("order") OrderEntity order,
                                    BindingResult result) {

        BookEntity book=bookService.getBookById(id);
        UserEntity receiver=userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if (!DataValidation.isValidPhoneNumber(order.getPhoneNumber())) {
            result.rejectValue("phoneNumber",
                    "invalid.phone",
                    "Невалиден телефонен номер!");}
        if (result.hasErrors()) {
            return new ModelAndView("confirmOrder",
                    "book",
                    book);
        }

        OrderEntity newOrder = new OrderEntity(
                order.getAddress(),
                LocalDate.now(),
                order.getPhoneNumber(),
                book.getBookName(),
                receiver.getUsername()
        );
        orderService.saveOrder(newOrder);

        emailService.orderConfirmationEmail(
                receiver.getEmail(),
                receiver.getName(),
                book.getBookName(),
                newOrder.getAddress(),
                newOrder.getPhoneNumber(),
                String.valueOf(newOrder.getDate()),
                String.valueOf(newOrder.getId())
        );
        bookService.decreaseBookStockCount(book);

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/listOrders")
    public ModelAndView listOrders(Model model) {
        return new ModelAndView("orders",
                "orders",
                orderService.findAllOrders());
    }

    @GetMapping("/deleteOrder/{id}")
    public ModelAndView deleteOrder(@PathVariable Long id){
        orderService.deleteOrderById(id);

        return new ModelAndView("redirect:/listOrders");
    }

    @GetMapping("/remind/{id}")
    public ModelAndView remindDeadline(@PathVariable Long id){
        OrderEntity order=orderService.findOrderById(id);
        UserEntity user = userService.getUserByUsername(order.getUsername());
        emailService.orderDeadlineOverdueEmail(user.getEmail(),
                                                user.getName(),
                                                order.getTitle());

        return new ModelAndView("redirect:/listOrders");
    }
}
