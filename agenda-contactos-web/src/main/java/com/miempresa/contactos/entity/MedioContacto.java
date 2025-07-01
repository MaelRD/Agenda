package com.miempresa.contactos.entity;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "tac03_medio_contacto")
public class MedioContacto extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medio_contacto")
    public Long id;

    @ManyToOne
    @JoinColumn(name = "id_contacto", nullable = false)
    public Contacto contacto;

    @ManyToOne
    @JoinColumn(name = "id_tipo", nullable = false)
    public TipoMedioContacto tipo;

    @Column(name = "valor", nullable = false)
    public String valor;

    @Column(name = "st_activo")
    public Boolean activo;
}
