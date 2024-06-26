package com.library.impl.db.repository;

import com.library.impl.db.entity.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RateRepository extends JpaRepository<RateEntity,Long> {
    @Query("""
            SELECT rate FROM RateEntity rate where rate.bookId = :bookId
            """)
    List<RateEntity> findRatesByBookId(@Param("bookId") Long bookId);
}