package com.miempresa.contactos.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class RegistroDTO {
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String login;
    private String password;
}
