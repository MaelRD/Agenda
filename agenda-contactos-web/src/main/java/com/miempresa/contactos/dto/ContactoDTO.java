package com.miempresa.contactos.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactoDTO {
    private Long idUsuario;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String apodo;
}
