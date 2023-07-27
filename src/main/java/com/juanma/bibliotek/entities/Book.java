package com.juanma.bibliotek.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank
    @Size(min=10,max=30)
    private String description;
    @Temporal(TemporalType.DATE)
    private Date printingDate;
    @NotNull(message="Campo name obligatorio")
    private Integer copies;

    private Integer rented; //cantidad de copias que quedan para alquilar


    @ManyToOne
    private Author author;
    @ManyToOne
    private Editorial editorial;



    @PrePersist //logica adicional antes que la entidad sea peristida
    public void addDate(){
        this.printingDate = new Date();
        this.rented = copies;
    }

}
