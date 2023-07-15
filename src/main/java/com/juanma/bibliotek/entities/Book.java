package com.juanma.bibliotek.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name="books")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Campo name obligatorio")
    private String name;
    private Date printingDate;
    @NotNull(message="Campo name obligatorio")
    private Integer copies;


    @ManyToOne
    @NotNull
    private Author author;
    @ManyToOne
    @NotNull
    private Editorial editorial;



    @PrePersist //logica adicional antes que la entidad sea peristida
    public void addDate(){
        this.printingDate = new Date();
    }

}
