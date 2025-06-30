package com.miempresa.contactos.service;

import com.miempresa.contactos.entity.MedioContacto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class MedioContactoService {

    public List<MedioContacto> listarPorContacto(Long idContacto) {
        return MedioContacto.list("contacto.id", idContacto);
    }

    public MedioContacto buscarPorId(Long id) {
        return MedioContacto.findById(id);
    }

    @Transactional
    public MedioContacto crear(MedioContacto medio) {
        medio.persist();
        return medio;
    }

    @Transactional
    public MedioContacto actualizar(Long id, MedioContacto datos) {
        MedioContacto medio = buscarPorId(id);
        if (medio == null) return null;

        medio.tipo = datos.tipo;
        medio.valor = datos.valor;
        medio.activo = datos.activo;
        medio.contacto = datos.contacto;

        medio.persist();
        return medio;
    }

    @Transactional
    public void eliminar(Long id) {
        MedioContacto.deleteById(id);
    }
}
