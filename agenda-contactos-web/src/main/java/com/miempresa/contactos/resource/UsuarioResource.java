package com.miempresa.contactos.resource;

import com.miempresa.contactos.auth.AuthService;
import com.miempresa.contactos.dto.*;
import com.miempresa.contactos.entity.Usuario;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    AuthService authService;

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
        usuario.password = dto.getPassword(); // Reemplazar por hash si usas hashing
        usuario.persist();

        return Response.status(201).entity(usuario).build();
    }

    @POST
    @Path("/login")
    public Response login(LoginDTO dto) {
        Usuario usuario = authService.autenticar(dto.getLogin(), dto.getPassword());
        if (usuario == null) {
            return Response.status(401).entity("Credenciales incorrectas").build();
        }

        return Response.ok(usuario).build();
    }

    @PUT
    @Path("/cambiar-password")
    @Transactional
    public Response cambiarPassword(CambioPasswordDTO dto) {
        Usuario usuario = authService.autenticar(dto.getLogin(), dto.getPasswordActual());
        if (usuario == null) {
            return Response.status(401).entity("Credenciales incorrectas").build();
        }

        usuario.password = dto.getPasswordNueva(); // Reemplazar por hash si usas hashing
        usuario.persist();

        return Response.ok("Contraseña actualizada").build();
    }

    @PUT
    @Path("/recuperar-password")
    @Transactional
    public Response recuperarPassword(RecuperarPasswordDTO dto) {
        Usuario usuario = Usuario.find("login", dto.getLogin()).firstResult();
        if (usuario == null) {
            return Response.status(404).entity("Usuario no encontrado").build();
        }

        usuario.password = dto.getNuevaPassword(); // Reemplazar por hash si usas hashing
        usuario.persist();

        return Response.ok("Contraseña restablecida").build();
    }
}
