package com.juanma.bibliotek.controllers;

import com.juanma.bibliotek.entities.Editorial;
import com.juanma.bibliotek.service.EditorialServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/editorial")
@CrossOrigin(origins = "http://localhost:5173")
public class EditorialController {

    @Autowired
    private EditorialServiceImpl service;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Editorial editorial,BindingResult result){

        if(result.hasErrors()){
            return validation(result);
        }
        return ResponseEntity.status(201).body(service.save(editorial));
    }

    @GetMapping("/all")
    public ResponseEntity<?> all(){
        return ResponseEntity.status(200).body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Editorial> o = service.findById(id);
        if(o.isPresent()){
            Editorial edi = o.get();
            return ResponseEntity.ok(edi);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Editorial editorial,BindingResult result,@PathVariable Long id){
        if(result.hasErrors()){
            return validation(result);
        }
        Optional<Editorial> o = service.findById(id);
        if(o.isPresent()){
            Editorial e = o.get();
            e.setName(editorial.getName());
            return ResponseEntity.status(201).body(service.save(e));
        }
        return ResponseEntity.notFound().build();
    }
    private ResponseEntity<?> validation(BindingResult result){
        HashMap<String,String>errors = new HashMap<>();
        result.getFieldErrors().forEach( error -> {
            errors.put(error.getField(),error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
