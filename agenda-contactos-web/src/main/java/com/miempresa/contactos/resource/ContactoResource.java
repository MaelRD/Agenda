package com.miempresa.contactos.resource;

import com.miempresa.contactos.dto.ContactoDTO;
import com.miempresa.contactos.dto.ContactoConMediosDTO;
import com.miempresa.contactos.entity.Contacto;
import com.miempresa.contactos.entity.MedioContacto;
import com.miempresa.contactos.entity.Usuario;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/contactos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContactoResource {

    @POST
    @Transactional
    public Response crearContacto(ContactoDTO dto) {
        if (dto.getIdUsuario() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El campo 'idUsuario' es obligatorio.").build();
        }

        Usuario usuario = Usuario.findById(dto.getIdUsuario());
        if (usuario == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Usuario no encontrado con id=" + dto.getIdUsuario()).build();
        }

        Contacto contacto = new Contacto();
        contacto.usuario = usuario;
        contacto.nombre = dto.getNombre();
        contacto.primerApellido = dto.getPrimerApellido();
        contacto.segundoApellido = dto.getSegundoApellido();
        contacto.apodo = dto.getApodo();

        contacto.persist();

        return Response.status(Response.Status.CREATED).entity(contacto).build();
    }

    @GET
    @Path("/completos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ContactoConMediosDTO> listarContactosConMedios() {
        List<Contacto> contactos = Contacto.listAll();
        List<ContactoConMediosDTO> resultado = new ArrayList<>();

        for (Contacto contacto : contactos) {
            ContactoConMediosDTO dto = new ContactoConMediosDTO();

            String nombreCompleto = String.join(" ",
                    contacto.nombre,
                    contacto.primerApellido,
                    contacto.segundoApellido != null ? contacto.segundoApellido : ""
            ).trim();

            dto.setNombreCompleto(nombreCompleto);

            List<MedioContacto> medios = MedioContacto.list("contacto.id", contacto.id);

            List<ContactoConMediosDTO.MedioDTO> medioDTOs = medios.stream().map(m -> {
                ContactoConMediosDTO.MedioDTO medio = new ContactoConMediosDTO.MedioDTO();
                medio.setTipo(m.tipo != null ? m.tipo.nombre : "Sin tipo");
                medio.setValor(m.valor);
                return medio;
            }).collect(Collectors.toList());

            dto.setMedios(medioDTOs);
            resultado.add(dto);
        }

        return resultado;
    }
    @PUT
    @Path("/{id}")
    @Transactional
    public Response actualizarContacto(@PathParam("id") Long id, ContactoDTO dto) {
        Contacto contacto = Contacto.findById(id);
        if (contacto == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Contacto no encontrado").build();
        }

        contacto.nombre = dto.getNombre();
        contacto.primerApellido = dto.getPrimerApellido();
        contacto.segundoApellido = dto.getSegundoApellido();
        contacto.apodo = dto.getApodo();

        return Response.ok(contacto).build();
    }
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id) {
        Contacto contacto = Contacto.findById(id);
        if (contacto == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Contacto no encontrado").build();
        }

        ContactoDTO dto = new ContactoDTO();
        dto.setIdUsuario(contacto.usuario.id);
        dto.setNombre(contacto.nombre);
        dto.setPrimerApellido(contacto.primerApellido);
        dto.setSegundoApellido(contacto.segundoApellido);
        dto.setApodo(contacto.apodo);

        return Response.ok(dto).build();
    }


}
