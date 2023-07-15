package com.juanma.bibliotek.service;

import com.juanma.bibliotek.entities.Book;
import com.juanma.bibliotek.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository repository;

    @Override
    @Transactional//anotacion cuando hago cambios en la db
    public Book save(Book book) {
        return repository.save(book);
    }

    @Override
    @Transactional(readOnly = true)//anotacione cuando traigo algo para leer de la db
    public List<Book> findAll() {
        List<Book> books = repository.findAll();
        return books;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }
}
