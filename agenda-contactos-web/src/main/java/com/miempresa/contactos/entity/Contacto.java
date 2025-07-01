package com.miempresa.contactos.entity;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "tac02_contacto")
public class Contacto extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contacto")
    public Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    public Usuario usuario;

    @Column(name = "tx_nombre", nullable = false)
    public String nombre;

    @Column(name = "tx_primer_apellido", nullable = false)
    public String primerApellido;

    @Column(name = "tx_segundo_apellido")
    public String segundoApellido;

    @Column(name = "tx_apodo")
    public String apodo;

    // Si quieres agregar fecha de creación
    @Column(name = "fh_creacion")
    public java.time.LocalDateTime fechaCreacion;

    @PrePersist
    public void onCreate() {
        this.fechaCreacion = java.time.LocalDateTime.now();
    }
}
