package com.example.demo.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "book")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookIdSeqGenerator")
    @SequenceGenerator(name = "bookIdSeqGenerator", sequenceName = "book_id_seq", allocationSize = 1)
    Long id;

    @Column(name = "book_name")
    String bookName;

    @Column(name = "author")
    String author;

    @Column (name = "genre")
    String genre;

    @Column(name = "book_details")
    String bookDetails;

    @Column(name = "stock_count")
    Integer stockCount;
}