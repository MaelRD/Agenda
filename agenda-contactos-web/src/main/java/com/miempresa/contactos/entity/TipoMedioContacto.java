package com.miempresa.contactos.entity;

import jakarta.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name = "cac01_tipo_medio_contacto")
public class TipoMedioContacto extends PanacheEntity {

    @Column(name = "tx_nombre", nullable = false)
    public String nombre;

    @Column(name = "tx_descripcion")
    public String descripcion;

    @Column(name = "st_activo")
    public boolean activo;
}
