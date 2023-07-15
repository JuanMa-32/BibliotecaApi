package com.juanma.bibliotek.service;

import com.juanma.bibliotek.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book save(Book book);
    List<Book> findAll();
    Optional<Book> findById(Long id);
    void remove(Long id);
}
