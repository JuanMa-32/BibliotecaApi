package com.juanma.bibliotek.controllers;

import com.juanma.bibliotek.entities.UserEntity;
import com.juanma.bibliotek.enumerators.Roles;
import com.juanma.bibliotek.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/")
public class IndexController {

    @Autowired
    private UserServiceImpl service;

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody UserEntity user, BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }
        user.setRol(Roles.USER);
        return ResponseEntity.status(201).body(service.save(user));
    }

    private ResponseEntity<?> validation (BindingResult result){
        HashMap<String,String> errors = new HashMap();

        result.getFieldErrors().forEach( e -> {
            errors.put(e.getField(),e.getDefaultMessage());
        } );
        return ResponseEntity.badRequest().body(errors);
    }
}
