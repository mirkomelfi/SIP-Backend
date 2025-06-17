# 📦 FindIT - Sistema de Localización Interna de Objetos con QR

FindIT es una solución full stack desarrollada como proyecto integrador para obtener el **título de Analista Informático**. Permite gestionar la ubicación de objetos físicos dentro de oficinas mediante códigos QR, facilitando la organización y disponibilidad de herramientas o insumos en tiempo real.

Diseñado como una aplicación **SaaS de uso interno**, FindIT está orientado a PyMEs, talleres y espacios colaborativos donde múltiples personas utilizan recursos compartidos.

---


## 🎯 Funcionalidades principales

- 📍 Localización de objetos físicos a través de códigos QR
- 📦 Estructura jerárquica: **Sectores → Contenedores → Ítems**
- 📲 Escaneo de QR de un sector para ver qué contenedores e ítems contiene
- 🔍 Búsqueda por nombre, o descripción de ítems desde la app
- 🏷️ Generación automática de códigos QR desde la app al registrar un ítem/contenedor/sector para pegarlo en el mismo y al escanear obtener su información
- 👤 Sistema de usuarios con roles diferenciados (`admin` y `user`)
- 🔐 Autenticación y control de acceso mediante JWT

---

## 🧑‍💼 Casos de uso

- Escaneás el QR de una oficina (sector) → ves todos los estantes (contenedores) y los ítems dentro
- Filtrás desde la app para buscar solo herramientas eléctricas, insumos del área de diagnóstico, etc.
- Sabés en todo momento **qué hay**, **dónde está** y **quién lo modificó por última vez**

---

## 👥 Roles y permisos

| Rol       | Permisos principales                                       |
|-----------|-------------------------------------------------------------|
| Usuario   | CRUD de ítems, escaneo de sectores, filtrado y búsqueda    |
| Admin     | Todo lo anterior + CRUD de sectores, contenedores y usuarios|

---

## 🔐 Autenticación

- Login seguro con **JSON Web Tokens (JWT)**
- Rutas protegidas por rol en backend y en frontend
- Sesiones gestionadas desde el frontend

---

## 🛠️ Tecnologías utilizadas

### Backend
- Java 17
- Spring Boot
- Spring Security + JWT
- MySQL

### Frontend
- React JS
- HTML5 + CSS3

---

## ⚙️ Instrucciones para correr localmente
Clonar ambos repositorios, instalar los node_modules, crear una bdd en MySQL y cargar en el application.properties sus credenciales.


### Requisitos

- Java 17
- Node.js y npm
- MySQL
