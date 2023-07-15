package com.juanma.bibliotek.service;

import com.juanma.bibliotek.entities.Editorial;

import java.util.List;
import java.util.Optional;

public interface EditorialService {

    Editorial save(Editorial edi);
    void delete(Long id);
    List<Editorial> findAll();
    Optional<Editorial> findById(Long id);

}
