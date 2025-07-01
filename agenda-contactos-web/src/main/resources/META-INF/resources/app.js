// ========== CONFIGURACIÓN ==========
const API_BASE = "http://localhost:8888";

// ========== UTILIDADES ==========
function guardarUsuarioId(id) {
  localStorage.setItem("usuarioId", id);
}

function obtenerUsuarioId() {
  return localStorage.getItem("usuarioId");
}

function cerrarSesion() {
  localStorage.clear();
  window.location.href = "login.html";
}

// ========== LOGIN ==========
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
      console.log("✅ Usuario recibido del backend:", usuario);

      if (usuario.id) {
        guardarUsuarioId(usuario.id);
        window.location.href = "dashboard.html";
      } else {
        msg.style.color = "red";
        msg.textContent = "❌ Respuesta inválida del servidor (falta ID)";
      }
    } else {
      msg.style.color = "orange";
      msg.textContent = "⚠️ Credenciales incorrectas.";
    }
  } catch (err) {
    document.getElementById("loginMessage").textContent = "❌ Error al conectar con el servidor.";
    console.error(err);
  }
});

// ========== REGISTRO ==========
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
      msg.textContent = "✅ Usuario creado exitosamente. Ahora puedes iniciar sesión.";
    } else if (response.status === 409) {
      msg.style.color = "orange";
      msg.textContent = "⚠️ El usuario ya existe.";
    } else {
      msg.style.color = "red";
      msg.textContent = "❌ Error al registrar usuario.";
    }
  } catch (err) {
    document.getElementById("registroMessage").textContent = "❌ Error al conectar con el servidor.";
  }
});

// ========== VALIDACIÓN DE SESIÓN Y CARGA DE CONTACTOS ==========
if (window.location.pathname.includes("dashboard.html")) {
  const usuarioId = obtenerUsuarioId();
  if (!usuarioId) {
    alert("⚠️ No has iniciado sesión.");
    window.location.href = "login.html";
  } else {
    cargarContactos();
  }
}

// ========== CREAR CONTACTO ==========
document.getElementById("formContacto")?.addEventListener("submit", async function (e) {
  e.preventDefault();

  const usuarioId = obtenerUsuarioId();
  const contacto = {
    idUsuario: parseInt(usuarioId),
    nombre: document.getElementById("nombreContacto").value,
    primerApellido: document.getElementById("apellidoContacto").value,
    segundoApellido: document.getElementById("segundoApellidoContacto").value,
    apodo: document.getElementById("apodoContacto").value
  };

  try {
    const response = await fetch(`${API_BASE}/contactos`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(contacto)
    });

    if (response.ok) {
      alert("✅ Contacto guardado exitosamente.");
      e.target.reset();
      cargarContactos();
    } else {
      const error = await response.text();
      alert("❌ Error al guardar contacto:\n" + error);
    }
  } catch (err) {
    alert("❌ Error al conectar con el servidor.");
    console.error(err);
  }
});

// ========== CARGAR CONTACTOS ==========
async function cargarContactos() {
  const usuarioId = obtenerUsuarioId();
  console.log("🔄 cargando contactos para usuarioId =", usuarioId);

  if (!usuarioId) {
    console.warn("⚠️ usuarioId no encontrado.");
    alert("⚠️ No estás autenticado.");
    window.location.href = "login.html";
    return;
  }

  try {
    const response = await fetch(`${API_BASE}/contactos/completos?usuarioId=${usuarioId}`);
    if (response.ok) {
      const contactos = await response.json();
      const lista = document.getElementById("listaContactos");
      if (!lista) return;

      lista.innerHTML = "";
      contactos.forEach(c => {
        const mediosTexto = c.medios?.map(m => `${m.tipo}: ${m.valor}`).join(", ") || "Sin medios";
        const li = document.createElement("li");
        li.innerHTML = `
          <strong>${c.nombreCompleto}</strong> – ${mediosTexto}
          <button onclick="editarContacto(${c.id})">✏️</button>
        `;
        lista.appendChild(li);
      });
    } else {
      const error = await response.text();
      console.error("❌ Error al cargar contactos:", error);
      alert("❌ Error al cargar contactos:\n" + error);
    }
  } catch (err) {
    console.error("❌ Error de red al conectar con el backend:", err);
    alert("❌ No se pudo conectar con el servidor.");
  }
}

// ========== MODAL: EDITAR CONTACTO ==========
let contactoEditandoId = null;

async function editarContacto(id) {
  const res = await fetch(`${API_BASE}/contactos/${id}`);
  if (res.ok) {
    const contacto = await res.json();
    contactoEditandoId = id;

    document.getElementById("editNombre").value = contacto.nombre;
    document.getElementById("editApellido").value = contacto.primerApellido;
    document.getElementById("editSegundoApellido").value = contacto.segundoApellido || "";
    document.getElementById("editApodo").value = contacto.apodo || "";
    document.getElementById("modalEditar").style.display = "flex";
  } else {
    alert("❌ Error al cargar contacto para editar");
  }
}

// ========== GUARDAR CAMBIOS EN CONTACTO ==========
async function guardarEdicion() {
  const datos = {
    idUsuario: parseInt(obtenerUsuarioId()),
    nombre: document.getElementById("editNombre").value,
    primerApellido: document.getElementById("editApellido").value,
    segundoApellido: document.getElementById("editSegundoApellido").value,
    apodo: document.getElementById("editApodo").value
  };

  const res = await fetch(`${API_BASE}/contactos/${contactoEditandoId}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(datos)
  });

  if (res.ok) {
    alert("✅ Contacto actualizado");
    cerrarModal();
    cargarContactos();
  } else {
    const msg = await res.text();
    alert("❌ Error al actualizar contacto:\n" + msg);
  }
}

function cerrarModal() {
  document.getElementById("modalEditar").style.display = "none";
  contactoEditandoId = null;
}
