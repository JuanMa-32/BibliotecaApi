package com.juanma.bibliotek.controllers;

import com.juanma.bibliotek.entities.Book;
import com.juanma.bibliotek.service.BookServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "http://localhost:5173")
public class BookController {

    @Autowired
    private BookServiceImpl service;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Book book, BindingResult result){
        if(result.hasErrors()){
           return  validation(result);
        }
        return ResponseEntity.status(201).body(service.save(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        service.remove(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Book book,BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validation(result);
        }
        Optional<Book> o = service.findById(id);
        if(o.isPresent()){
            Book bookDb = o.get();
            bookDb.setDescription(book.getDescription());
            bookDb.setName(book.getName());
            return ResponseEntity.status(201).body(service.save(bookDb));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> byId(@PathVariable Long id){
        Optional<Book> o = service.findById(id);
        if(o.isPresent()){
            Book book = o.get();
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<?> all(){
        return ResponseEntity.ok(service.findAll());
    }



    private ResponseEntity<?> validation(BindingResult result){
        HashMap<String,String> errors = new HashMap<>();

       result.getFieldErrors().forEach(error -> { //getFieldErrors se usa para obtener la lista de errores de validacion en el objeto
           errors.put(error.getField(),error.getDefaultMessage());
       });
       return ResponseEntity.badRequest().body(errors);
    }
}
