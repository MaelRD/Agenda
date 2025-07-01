package com.miempresa.contactos.resource;

import com.miempresa.contactos.dto.LoginDTO;
import com.miempresa.contactos.dto.RegistroDTO;
import com.miempresa.contactos.dto.UsuarioDTO;
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
    @Path("/registrar")
    @Transactional
    public Response registrar(RegistroDTO dto) {
        if (Usuario.find("login", dto.getLogin()).firstResult() != null) {
            return Response.status(409).entity("El usuario ya existe").build();
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setPrimerApellido(dto.getPrimerApellido());
        usuario.setSegundoApellido(dto.getSegundoApellido());
        usuario.setLogin(dto.getLogin());
        usuario.setPassword(dto.getPassword());

        usuario.persist();

        // Crear y retornar el DTO como respuesta
        UsuarioDTO responseDto = new UsuarioDTO();
        responseDto.setId(usuario.getId());
        responseDto.setNombre(usuario.getNombre());
        responseDto.setLogin(usuario.getLogin());

        return Response.ok(responseDto).build();
    }

    @POST
    @Path("/login")
    @Transactional
    public Response login(LoginDTO dto) {
        Usuario usuario = Usuario.find("login = ?1 and password = ?2", dto.getLogin(), dto.getPassword()).firstResult();

        if (usuario == null) {
            return Response.status(401).entity("Credenciales inválidas").build();
        }

        UsuarioDTO responseDto = new UsuarioDTO();
        responseDto.setId(usuario.getId());
        responseDto.setNombre(usuario.getNombre());
        responseDto.setLogin(usuario.getLogin());

        return Response.ok(responseDto).build();
    }

    @POST
    @Path("/logout")
    public Response logout() {
        return Response.ok("Sesión cerrada correctamente").build();
    }
}
