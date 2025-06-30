package com.miempresa.contactos.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class RecuperarPasswordDTO {
    private String login;
    private String nuevaPassword;
}
