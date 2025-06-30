package com.miempresa.contactos.resource;

import com.miempresa.contactos.entity.Contacto;
import com.miempresa.contactos.entity.Usuario;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/contactos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactoResource {

    @GET
    @Path("/usuario/{idUsuario}")
    public List<Contacto> listarPorUsuario(@PathParam("idUsuario") Long idUsuario) {
        return Contacto.list("usuario.id", idUsuario);
    }

    @POST
    @Transactional
    public Contacto crear(Contacto contacto) {
        contacto.persist();
        return contacto;
    }

    @GET
    @Path("/{id}")
    public Contacto obtener(@PathParam("id") Long id) {
        return Contacto.findById(id);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Contacto actualizar(@PathParam("id") Long id, Contacto datos) {
        Contacto contacto = Contacto.findById(id);
        if (contacto == null) {
            throw new NotFoundException();
        }

        contacto.nombre = datos.nombre;
        contacto.primerApellido = datos.primerApellido;
        contacto.segundoApellido = datos.segundoApellido;
        contacto.usuario = datos.usuario;

        contacto.persist();
        return contacto;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void eliminar(@PathParam("id") Long id) {
        Contacto.deleteById(id);
    }
}
