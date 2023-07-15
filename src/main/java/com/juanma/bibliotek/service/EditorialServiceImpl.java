package com.juanma.bibliotek.service;

import com.juanma.bibliotek.entities.Editorial;
import com.juanma.bibliotek.repositories.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EditorialServiceImpl implements EditorialService{

    @Autowired
    private EditorialRepository repository;

    @Override
    @Transactional
    public Editorial save(Editorial edi) {
        return repository.save(edi);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Editorial> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Editorial> findById(Long id) {
        return repository.findById(id);
    }
}
