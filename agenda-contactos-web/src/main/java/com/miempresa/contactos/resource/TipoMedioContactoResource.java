package com.miempresa.contactos.resource;

import com.miempresa.contactos.entity.TipoMedioContacto;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/tipos-contacto")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoMedioContactoResource {

    @GET
    public List<TipoMedioContacto> listar() {
        return TipoMedioContacto.listAll();
    }

    @GET
    @Path("/{id}")
    public TipoMedioContacto obtener(@PathParam("id") Long id) {
        return TipoMedioContacto.findById(id);
    }

    @POST
    @Transactional
    public TipoMedioContacto crear(TipoMedioContacto tipo) {
        tipo.persist();
        return tipo;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public TipoMedioContacto actualizar(@PathParam("id") Long id, TipoMedioContacto datos) {
        TipoMedioContacto tipo = TipoMedioContacto.findById(id);
        if (tipo == null) throw new NotFoundException();

        tipo.nombre = datos.nombre;
        tipo.descripcion = datos.descripcion;
        tipo.activo = datos.activo;

        return tipo;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void eliminar(@PathParam("id") Long id) {
        TipoMedioContacto tipo = TipoMedioContacto.findById(id);
        if (tipo != null) {
            tipo.delete();
        }
    }
}
