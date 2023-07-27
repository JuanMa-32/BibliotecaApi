package com.juanma.bibliotek.service;

import com.juanma.bibliotek.entities.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Author save(Author a);
    List<Author> findAll();
    Optional<Author> findById(Long id);
    void delete(Long id);
}
