package com.example.demo.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.binary.Base64;
import org.springframework.data.domain.Persistable;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Entity
@Table(name = "book")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookEntity implements Persistable<Long> {

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

    @Column(name = "title")
    String title;

    @Column(name = "stock_count")
    Integer stockCount;

    @Lob
    @Column(name = "cover")
    byte[] cover;

    @Transient
    String coverBase64encoded;

    @Override
    public boolean isNew() {
        return id == null;
    }

    @PostLoad
    @PreUpdate
    private void encodeCoverBase64() {
        if (cover != null) {
            byte[] encodeBase64 = Base64.encodeBase64(cover);
            String base64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
            coverBase64encoded = base64Encoded;
        }
    }
}