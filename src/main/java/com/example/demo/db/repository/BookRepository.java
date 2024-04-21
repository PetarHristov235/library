package com.example.demo.db.repository;

import com.example.demo.db.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query("""
            SELECT b FROM BookEntity b
            WHERE b.bookName = :bookName""")
    BookEntity findBookByBookName(@Param("bookName") String bookName);

    @Query("""
            SELECT b FROM BookEntity b
            WHERE b.genre = :genre""")
    BookEntity findBooksByGenre(@Param("genre") String bookName);

    @Query("""
        DELETE FROM BookEntity b
        WHERE b.bookName = :bookName""")
    void deleteBookByBookName(@Param("bookName") String bookName);



}
