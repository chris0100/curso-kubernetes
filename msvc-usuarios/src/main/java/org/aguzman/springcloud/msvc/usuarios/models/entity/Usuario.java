package org.aguzman.springcloud.msvc.usuarios.models.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "no puede estar vacio")
    private String nombre;

    @NotEmpty(message = "no puede estar vacio")
    @Email
    @Column(unique = true)
    private String email;

    @NotEmpty(message = "no puede estar vacio")
    @NotBlank
    private String password;
}
