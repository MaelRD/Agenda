// ========== INICIAR SESIÓN ==========
document.getElementById("loginForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const login = document.getElementById("login").value;
  const password = document.getElementById("password").value;

  try {
    const response = await fetch("/usuarios/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ login, password })
    });

    const msg = document.getElementById("loginMessage");

    if (response.ok) {
      const usuario = await response.json();
      localStorage.setItem("usuarioId", usuario.id);
      msg.style.color = "lightgreen";
      msg.textContent = "¡Bienvenido, " + usuario.nombre + "!";
      // Aquí puedes redirigir o cargar datos adicionales
      cargarContactos();
    } else {
      msg.style.color = "orange";
      msg.textContent = "Credenciales incorrectas.";
    }
  } catch (err) {
    document.getElementById("loginMessage").textContent = "Error al conectar con el servidor.";
  }
});


// ========== REGISTRAR USUARIO ==========
document.getElementById("registroForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const nombre = document.getElementById("nombre").value;
  const primerApellido = document.getElementById("primerApellido").value;
  const segundoApellido = document.getElementById("segundoApellido").value;
  const login = document.getElementById("loginRegistro").value;
  const password = document.getElementById("passwordRegistro").value;

  try {
    const response = await fetch("/usuarios/registrar", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        nombre,
        primerApellido,
        segundoApellido,
        login,
        password
      })
    });

    const msg = document.getElementById("registroMessage");

    if (response.ok) {
      msg.style.color = "lightgreen";
      msg.textContent = "Usuario creado exitosamente. Ahora puedes iniciar sesión.";
    } else if (response.status === 409) {
      msg.style.color = "orange";
      msg.textContent = "El usuario ya existe.";
    } else {
      msg.style.color = "red";
      msg.textContent = "Error al registrar usuario.";
    }
  } catch (err) {
    document.getElementById("registroMessage").textContent = "Error al conectar con el servidor.";
  }
});


// ========== CARGAR CONTACTOS ==========
async function cargarContactos() {
  const usuarioId = localStorage.getItem("usuarioId");
  if (!usuarioId) return;

  try {
    const response = await fetch("/contactos/usuario/" + usuarioId);
    if (response.ok) {
      const contactos = await response.json();
      const lista = document.getElementById("listaContactos");
      if (!lista) return;

      lista.innerHTML = "";
      contactos.forEach(contacto => {
        const li = document.createElement("li");
        li.textContent = contacto.nombre + " " + contacto.primerApellido;
        lista.appendChild(li);
      });

      // Mostrar contenedor si existe
      const appContainer = document.getElementById("appContainer");
      if (appContainer) appContainer.style.display = "block";
    }
  } catch (err) {
    console.error("Error al cargar contactos:", err);
  }
}
