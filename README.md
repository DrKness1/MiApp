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
## Screenshots  y Gifs
### 🟢 Intents Implícitos

## /// Enviar Correo ///
![Correo](https://github.com/user-attachments/assets/c7a88061-9acd-4245-9f7a-c12132517e90)

## /// Añadir Contacto ///
![añadir contacto](https://github.com/user-attachments/assets/ef8cbe29-b846-4836-a13c-e8b686c97042)

## /// Abrir configuración Bluetooth ///
![Gif_Bt](https://github.com/user-attachments/assets/1f8c9707-c3b8-472b-b19c-63bf3dc8d94e)


## /// Seleccionar imagen de galería ///
![Galeria abierta](https://github.com/user-attachments/assets/b28144fa-4260-4ce4-af01-fbb63376c727)

### 🔵 Intents Explícitos

## /// Detalle de ítem ///

![Detalle item](https://github.com/user-attachments/assets/48fe0ff3-424c-41b7-a74e-74df3be33d60)

## /// Abrir configuración con OverflowMenu y transición a la derecha e izquierda ///

![Trahsiccioh](https://github.com/user-attachments/assets/59028fd2-b0af-4e07-b6dd-38cdc0a72ec5)



## // Explicación para compilar el apk de la aplicación //

https://github.com/user-attachments/assets/9fba2fc1-20d5-4928-b8d9-70f72f299a7e









