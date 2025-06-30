package com.miempresa.contactos.entity;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name = "tac01_usuario")
public class Usuario extends PanacheEntity {

    @Column(name = "tx_nombre", nullable = false)
    public String nombre;

    @Column(name = "tx_primer_apellido", nullable = false)
    public String primerApellido;

    @Column(name = "tx_segundo_apellido")
    public String segundoApellido;

    @Column(name = "tx_login", unique = true, nullable = false)
    public String login;

    @Column(name = "tx_password", nullable = false)
    public String password;
}
