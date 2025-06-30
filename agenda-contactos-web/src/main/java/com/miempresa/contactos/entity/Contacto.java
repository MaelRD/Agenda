package com.miempresa.contactos.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;


@Entity
@Table(name = "tac02_contacto")
public class Contacto extends PanacheEntityBase {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    public Usuario usuario;

    @Column(name = "tx_nombre", nullable = false)
    public String nombre;

    @Column(name = "tx_primer_apellido", nullable = false)
    public String primerApellido;

    @Column(name = "tx_segundo_apellido")
    public String segundoApellido;
}
