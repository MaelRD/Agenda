package com.miempresa.contactos.auth;

import com.miempresa.contactos.entity.Usuario;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthService {

    /**
     * Verifica que el login y la contraseña coincidan con un usuario registrado.
     * @param login nombre de usuario
     * @param password contraseña en texto plano
     * @return el usuario autenticado, o null si no coincide
     */
    public Usuario autenticar(String login, String password) {
        Usuario usuario = Usuario.find("login", login).firstResult();

        if (usuario != null && usuario.password.equals(password)) {
            return usuario;
        }
        return null;
    }
}
