// ========== CONFIGURAR API BASE ==========
const API_BASE = "http://localhost:8888";

// ========== INICIAR SESIÓN ==========
document.getElementById("loginForm")?.addEventListener("submit", async function (e) {
  e.preventDefault();

  const login = document.getElementById("login").value;
  const password = document.getElementById("password").value;

  try {
    const response = await fetch(`${API_BASE}/usuarios/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ login, password })
    });

    const msg = document.getElementById("loginMessage");

    if (response.ok) {
      const usuario = await response.json();
      localStorage.setItem("usuarioId", usuario.id);
      window.location.href = "dashboard.html";
    } else {
      msg.style.color = "orange";
      msg.textContent = "Credenciales incorrectas.";
    }
  } catch (err) {
    document.getElementById("loginMessage").textContent = "Error al conectar con el servidor.";
  }
});

// ========== REGISTRAR USUARIO ==========
document.getElementById("registroForm")?.addEventListener("submit", async function (e) {
  e.preventDefault();

  const nombre = document.getElementById("nombre").value;
  const primerApellido = document.getElementById("primerApellido").value;
  const segundoApellido = document.getElementById("segundoApellido").value;
  const login = document.getElementById("loginRegistro").value;
  const password = document.getElementById("passwordRegistro").value;

  try {
    const response = await fetch(`${API_BASE}/usuarios/registrar`, {
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

// ========== VALIDAR SESIÓN EN DASHBOARD ==========
if (window.location.pathname.includes("dashboard.html")) {
  const usuarioId = localStorage.getItem("usuarioId");
  if (!usuarioId) {
    window.location.href = "login.html";
  } else {
    cargarContactos();
  }
}

// ========== GUARDAR NUEVO CONTACTO ==========
document.getElementById("formContacto")?.addEventListener("submit", async function (e) {
  e.preventDefault();

  const usuarioId = localStorage.getItem("usuarioId");
  const nombre = document.getElementById("nombreContacto").value;
  const apellido = document.getElementById("apellidoContacto").value;

  const nuevoContacto = {
    idUsuario: parseInt(usuarioId),
    nombre: nombre,
    primerApellido: apellido,
    segundoApellido: "",
    apodo: ""
  };

  console.log("Enviando:", JSON.stringify(nuevoContacto));

  try {
    const response = await fetch(`${API_BASE}/contactos`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(nuevoContacto)
    });

    if (response.ok) {
      alert("Contacto guardado exitosamente.");
      e.target.reset();
      cargarContactos();
    } else {
      const error = await response.text();
      alert("Error al guardar contacto:\n" + error);
    }
  } catch (err) {
    alert("Error al conectar con el servidor.");
    console.error(err);
  }
});

// ========== CARGAR CONTACTOS ==========
async function cargarContactos() {
  try {
    const response = await fetch(`${API_BASE}/contactos/completos`);
    if (response.ok) {
      const contactos = await response.json();
      const lista = document.getElementById("listaContactos");
      if (!lista) return;

      lista.innerHTML = "";
      contactos.forEach(c => {
        const li = document.createElement("li");
        const mediosTexto = c.medios.map(m => `${m.tipo}: ${m.valor}`).join(", ");
        li.textContent = `${c.nombreCompleto} – ${mediosTexto}`;
        lista.appendChild(li);
      });
    } else {
      const error = await response.text();
      console.error("Error al cargar contactos:", error);
    }
  } catch (err) {
    console.error("Error al conectar al backend:", err);
  }
}

// ========== CERRAR SESIÓN ==========
function cerrarSesion() {
  localStorage.removeItem("usuarioId");
  window.location.href = "login.html";
}
