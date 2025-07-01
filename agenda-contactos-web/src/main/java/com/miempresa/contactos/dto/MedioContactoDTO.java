package com.miempresa.contactos.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MedioContactoDTO {
    private Long idContacto; // ID del contacto al que pertenece
    private Long idTipo;     // ID del tipo de medio (correo, teléfono, etc.)
    private String valor;    // El valor del medio (ej: "correo@gmail.com")
}
