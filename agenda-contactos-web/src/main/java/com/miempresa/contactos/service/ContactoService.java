package com.miempresa.contactos.service;

import com.miempresa.contactos.entity.Contacto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ContactoService {

    public List<Contacto> obtenerPorUsuario(Long idUsuario) {
        return Contacto.list("usuario.id", idUsuario);
    }

    public Contacto buscarPorId(Long id) {
        return Contacto.findById(id);
    }

    @Transactional
    public Contacto crear(Contacto contacto) {
        contacto.persist();
        return contacto;
    }

    @Transactional
    public Contacto actualizar(Long id, Contacto datos) {
        Contacto contacto = buscarPorId(id);
        if (contacto == null) return null;

        contacto.nombre = datos.nombre;
        contacto.primerApellido = datos.primerApellido;
        contacto.segundoApellido = datos.segundoApellido;
        contacto.usuario = datos.usuario;

        contacto.persist();
        return contacto;
    }

    @Transactional
    public void eliminar(Long id) {
        Contacto.deleteById(id);
    }
}
