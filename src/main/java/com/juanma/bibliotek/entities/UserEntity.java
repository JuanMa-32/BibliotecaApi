package com.juanma.bibliotek.entities;

import com.juanma.bibliotek.enumerators.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="usuarios")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="campo USERNAME requerido")
    private String username;
    @Email(message="Este no es un formato EMAIL")
    private String email;
    @NotBlank(message="campo PASSWORD requerido")
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles rol;

    @OneToMany
    private List<Book> alquilados;

}
