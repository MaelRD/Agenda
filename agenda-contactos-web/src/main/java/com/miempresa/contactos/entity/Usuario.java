package com.miempresa.contactos.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "tac01_usuario")
public class Usuario extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    public Long id;

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