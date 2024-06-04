package com.example.demo.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;

@Entity
@Table(name = "\"rate\"")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RateEntity implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rateIdSeqGenerator")
    @SequenceGenerator(name = "rateIdSeqGenerator", sequenceName = "rate_id_seq", allocationSize = 1)
    Long id;

    @Column(name = "rate_stars")
    Short rate;

    @Column(name = "comment")
    String comment;

    @Column(name = "book_id")
    Long bookId;

    @Override
    public boolean isNew() {
        return false;
    }
}
