package com.miempresa.contactos.resource;

import com.miempresa.contactos.entity.MedioContacto;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/medios-contacto")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MedioContactoResource {

    @GET
    @Path("/contacto/{idContacto}")
    public List<MedioContacto> listarPorContacto(@PathParam("idContacto") Long idContacto) {
        return MedioContacto.list("contacto.id", idContacto);
    }

    @POST
    @Transactional
    public MedioContacto crear(MedioContacto medio) {
        medio.persist();
        return medio;
    }

    @GET
    @Path("/{id}")
    public MedioContacto obtener(@PathParam("id") Long id) {
        return MedioContacto.findById(id);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public MedioContacto actualizar(@PathParam("id") Long id, MedioContacto datos) {
        MedioContacto medio = MedioContacto.findById(id);
        if (medio == null) {
            throw new NotFoundException();
        }

        medio.tipo = datos.tipo;
        medio.valor = datos.valor;
        medio.contacto = datos.contacto;

        medio.persist();
        return medio;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void eliminar(@PathParam("id") Long id) {
        MedioContacto.deleteById(id);
    }
}
