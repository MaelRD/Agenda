package com.miempresa.contactos.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tac01_usuario")
@Getter
@Setter
@NoArgsConstructor
public class Usuario extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "tx_nombre", nullable = false)
    private String nombre;

    @Column(name = "tx_primer_apellido", nullable = false)
    private String primerApellido;

    @Column(name = "tx_segundo_apellido")
    private String segundoApellido;

    @Column(name = "tx_login", unique = true, nullable = false)
    private String login;

    @Column(name = "tx_password", nullable = false)
    private String password;
}
