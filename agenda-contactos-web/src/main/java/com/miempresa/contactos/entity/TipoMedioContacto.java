package com.miempresa.contactos.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;


@Entity
@Table(name = "cac01_tipo_medio_contacto")
public class TipoMedioContacto extends PanacheEntityBase {

    @Id
    @Column(name = "id_tipo")
    public Long id;

    @Column(name = "tx_nombre", nullable = false)
    public String nombre;

    @Column(name = "tx_descripcion")
    public String descripcion;

    @Column(name = "st_activo")
    public Boolean activo;
}
