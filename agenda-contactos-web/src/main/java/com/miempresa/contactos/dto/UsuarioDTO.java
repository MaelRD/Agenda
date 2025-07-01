package com.miempresa.contactos.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String login;
}
