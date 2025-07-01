package com.miempresa.contactos.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ContactoConMediosDTO {

    private String nombreCompleto;
    private List<MedioDTO> medios;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class MedioDTO {
        private String tipo;
        private String valor;
    }
}
