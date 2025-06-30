package com.miempresa.contactos.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class LoginDTO {
    private String login;
    private String password;
}
