# 📱 Proyecto Android - MiApp

**Versión de Android:** 12 (API 31)  
**Nombre del proyecto:** Aplicación de Intents - Android Studio  
**Autores: Rodrigo Cisterna y Diego Urzúa**  
**IDE:** Android Studio 
**Lenguaje:** Java  

---
## 🧩 Resumen del Proyecto

Esta aplicación demuestra el uso de **intents implícitos y explícitos** en Android.  
Permite realizar acciones del sistema como abrir una página web, enviar correos, seleccionar imágenes, acceder a la configuración del dispositivo, en este caso, la configuración bluetooth y mostrar contactos.  
Además, incluye navegación entre actividades y la personalización de la interfaz.

La app cuenta con una **pantalla principal (HomeActivity)**, desde donde se accede a las distintas funciones, muestra el detalle del botón de abrir web mediante un AlertDialog y un menú de configuración accesible con transición a través del **Overflow Menu**.

---
## ⚙️ Intents Implementados

### 🟢 Intents Implícitos (5)

| Nº | Descripción | Acción del Intent | Actividad | Pasos de Prueba |
|----|--------------|------------------|------------|-----------------|
| 1 | **Abrir página web (Steam)** | `Intent.ACTION_VIEW` con URI de Steam | HomeActivity | Inicia la app.<br> | Pulsa “Abrir Web”.<br> | Se mostrará un cuadro de detalles.<br> Presiona “Sí” → Se abre la web de Steam en el navegador. |
| 2 | **Enviar correo electrónico** | `Intent.ACTION_SENDTO` con `mailto:` | HomeActivity | Pulsa “Enviar correo”.<br> | Se abre el cliente de correo con asunto y mensaje predefinido. |
| 3 | **Seleccionar imagen desde galería** | `ActivityResultContracts.GetContent()` | HomeActivity | Pulsa “Seleccionar imagen”.<br> Elige una imagen de la galería.<br> La imagen se muestra en el `ImageView`. |
| 4 | **Abrir configuración de Bluetooth** | `android.provider.Settings.ACTION_BLUETOOTH_SETTINGS` | HomeActivity | Pulsa “Configuración”.<br> Se abre la configuración de Bluetooth del sistema. |
| 5 | **Abrir contactos del sistema** | `Intent.ACTION_PICK` (implícito en ContactosActivity) | ContactosActivity | Pulsa “Contactos”.<br> Se abre la actividad con acceso a contactos o lista interna. |

---
### 🔵 Intents Explícitos (3)

| Nº | Descripción | Actividad origen → destino | Detalles / Pasos de Prueba |
|----|--------------|----------------------------|-----------------------------|
| 1 | **Mostrar detalles del ítem (Abrir Web)** | HomeActivity → DetalleActivity *(putExtra)* | Al pulsar “Abrir Web”, se envían los extras: `titulo`, `pais` y `url`.<br> | Se muestra un cuadro de diálogo con los detalles del sitio (AlertDialog).<br> | Si el usuario pulsa “Sí”, se abre la página en el navegador. |
| 2 | **Abrir Configuración (desde Toolbar / Overflow Menu)** | HomeActivity → ConfigActivity | Pulsa los tres puntos (Overflow Menu).<br> | Selecciona “Configuración”.<br> | Se abre la pantalla de ajustes del sistema de la app. |
| 3 | **Aplicar animaciones personalizadas entre actividades** | HomeActivity → ConfigActivity | Desde el menú, entra a Configuración.<br> | Observa el efecto de transición (`slide_in_right` / `slide_out_left`).<br> | La animación se ejecuta al abrir o cerrar la actividad. |

---
## 🎨 Funcionalidades Adicionales

- **SharedPreferences:** guarda el color de fondo seleccionado (blanco o gris).  
- **AlertDialog:** muestra información detallada del ítem antes de abrir la página web.  
- **Toolbar + Overflow Menu:** implementa opciones de configuración y navegación.  
- **Animaciones:** transiciones suaves entre actividades.

---
