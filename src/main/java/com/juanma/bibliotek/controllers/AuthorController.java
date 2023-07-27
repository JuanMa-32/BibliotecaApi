package com.juanma.bibliotek.controllers;

import com.juanma.bibliotek.entities.Author;
import com.juanma.bibliotek.service.AuthorServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/author")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthorController {

    @Autowired
    private AuthorServiceImpl service;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Author author,BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }
        return ResponseEntity.status(201).body(service.save(author));
    }
    @GetMapping("/all")
    public ResponseEntity<?> all(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Author> o = service.findById(id);
        if(o.isPresent()){
            Author author = o.get();
            return ResponseEntity.ok(author);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Author author,BindingResult result,@PathVariable Long id){
        if(result.hasErrors()){
            return validation(result);
        }
        Optional<Author> o = service.findById(id);
        if(o.isPresent()){
            Author authorDb = o.get();
            authorDb.setName(author.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(authorDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<?> validation(BindingResult result){
        HashMap<String,String> errors = new HashMap<>();

        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(),error.getDefaultMessage());
        });
        return  ResponseEntity.badRequest().build();
    }
}
