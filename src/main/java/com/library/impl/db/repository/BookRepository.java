package com.library.impl.db.repository;

import com.library.impl.db.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query("""
            SELECT b FROM BookEntity b
            WHERE b.id = :id""")
    Optional<BookEntity> findById(Long id);
}
