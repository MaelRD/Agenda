package com.miempresa.contactos.resource;

import com.miempresa.contactos.dto.LoginDTO;
import com.miempresa.contactos.dto.RegistroDTO;
import com.miempresa.contactos.entity.Usuario;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @POST
    @Path("/login")
    public Response login(LoginDTO dto) {
        Usuario usuario = Usuario.find("login = ?1 and password = ?2", dto.getLogin(), dto.getPassword()).firstResult();

        if (usuario == null) {
            return Response.status(401).entity("Credenciales incorrectas").build();
        }

        return Response.ok(usuario).build();
    }

    @POST
    @Path("/registrar")
    @Transactional
    public Response registrar(RegistroDTO dto) {
        if (Usuario.find("login", dto.getLogin()).firstResult() != null) {
            return Response.status(409).entity("El usuario ya existe").build();
        }

        Usuario usuario = new Usuario();
        usuario.nombre = dto.getNombre();
        usuario.primerApellido = dto.getPrimerApellido();
        usuario.segundoApellido = dto.getSegundoApellido();
        usuario.login = dto.getLogin();
        usuario.password = dto.getPassword();

        usuario.persist();

        return Response.ok(usuario).build();
    }
}
