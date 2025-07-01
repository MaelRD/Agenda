package com.miempresa.contactos.service;

import com.miempresa.contactos.entity.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioService {

    public Usuario buscarPorLogin(String login) {
        return Usuario.find("login", login).firstResult();
    }

    @Transactional
    public Usuario registrar(Usuario usuario) {
        usuario.persist();
        return usuario;
    }

    @Transactional
    public void cambiarPassword(Usuario usuario, String nuevaPassword) {
        usuario.setPassword(nuevaPassword); // ✅ usa el setter
        usuario.persist();
    }

    @Transactional
    public void recuperarPassword(String login, String nuevaPassword) {
        Usuario usuario = buscarPorLogin(login);
        if (usuario != null) {
            usuario.setPassword(nuevaPassword); // ✅ usa el setter
            usuario.persist();
        }
    }
}
