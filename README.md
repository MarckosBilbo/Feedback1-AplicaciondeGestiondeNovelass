# Feedback1-Aplicación de Gestión de Novelas

Repositorio --> https://github.com/MarckosBilbo/Feedback1-AplicaciondeGestiondeNovelass

## Descripción

Esta aplicación orientada de forma individual a cada usuario (Log-in + registro), permite gestionar una colección de novelas. Los usuarios pueden agregar, editar, eliminar y marcar como favoritas las novelas. La aplicación está desarrollada utilizando Kotlin y Java, y utiliza Jetpack Compose para la interfaz de usuario. Los datos se almacenan y gestionan mediante Firebase.

## Características

- **Agregar Novelas**: Los usuarios pueden agregar nuevas novelas proporcionando el título, autor, año y sinopsis.
- **Editar Novelas**: Los usuarios pueden editar las novelas existentes haciendo clic en ellas.
- **Eliminar Novelas**: Los usuarios pueden eliminar novelas de la lista.
- **Marcar como Favoritas**: Los usuarios pueden marcar y desmarcar novelas como favoritas.
- **Validación de Campos**: No se pueden agregar novelas cuyos campos no hayan sido rellenados. El campo de año solo permite números y muestra "Año no especificado" si no se proporciona un número válido.
- **Login y Registro**: Los usuarios pueden registrarse y autenticarse utilizando Firebase Authentication.
- **Configuración de Tema**: Los usuarios pueden cambiar entre modo claro y oscuro. La preferencia de tema se guarda utilizando `SharedPreferences`.
- **Persistencia de Datos**: La aplicación utiliza Firebase Realtime Database con persistencia habilitada para trabajar sin conexión.
- **Widget de Novelas**: La aplicación incluye un widget que muestra las novelas favoritas del usuario.

## Estructura del Proyecto

### `MainActivity.kt`

Este archivo contiene la actividad principal que configura la navegación y el tema de la aplicación.

### `PantallaPrincipal.kt`

Este archivo contiene la pantalla principal de la aplicación, donde se muestran las novelas y se gestionan las acciones de agregar, editar, eliminar y marcar como favoritas.

### `PantallaLogin.kt`

Este archivo contiene la pantalla de login donde los usuarios pueden autenticarse.

### `PantallaRegistro.kt`

Este archivo contiene la pantalla de registro donde los usuarios pueden crear una nueva cuenta.

### `PantallaConfiguracion.kt`

Este archivo contiene la pantalla de configuración donde los usuarios pueden cambiar el tema de la aplicación.

### `ConectivityWorker.java`

Gestiona la conectividad de red y asegura que los datos se sincronicen correctamente cuando hay conexión disponible.

### `Novel.java`

Este archivo define la entidad `Novel` que representa una novela en la base de datos.

### `NovelRepository.java`

Este archivo actúa como un repositorio para acceder a los datos de las novelas en Firebase.

### `VistaModeloNovela.java`

Este archivo define el ViewModel que maneja la lógica de negocio y la comunicación entre la interfaz de usuario y el repositorio.

### `VistaModeloConfiguracion.java`

Este archivo define el ViewModel que maneja la lógica de negocio y la comunicación entre la interfaz de usuario y las preferencias de configuración.

### `MyApplication.java`

Este archivo configura la persistencia de Firebase para la aplicación.

### `Theme.kt`

Este archivo define el tema de la aplicación utilizando Material Design 3.

### `PreferencesManager.java`

Este archivo maneja las preferencias de usuario, incluyendo la configuración del tema.

## Clases de Nueva Adición

### `DetallesNovelaFragment.kt`

Este archivo contiene el fragmento que muestra los detalles de una novela específica.

### `ListaNovelasFragment.kt`

Este archivo contiene el fragmento que muestra la lista de novelas.

### `WidgetNovelas.kt`

Este archivo define el composable que muestra el widget de novelas favoritas en un desplegable interactivo.

### `RedOptima.java`

Este archivo contiene la clase `RedOptima` que proporciona un método para comprimir datos utilizando GZIP.

### `MapaNovelas.kt`

Este archivo define la pantalla que muestra un mapa con las ubicaciones asociadas a las novelas. Cada novela se representa mediante un marcador en el mapa, utilizando las coordenadas de latitud y longitud almacenadas en la clase Novel. El mapa está centrado inicialmente en la Península Ibérica, y permite la interacción con los marcadores para identificar las novelas. La clase utiliza la biblioteca OSMDroid para renderizar el mapa.

### `MapaUbicacionUsuario.kt`

Este archivo define la pantalla que muestra la ubicación actual del usuario en un mapa. Si no se puede obtener la ubicación del usuario (por falta de permisos o problemas de conectividad), el mapa se centra en una ubicación predeterminada (Madrid). La clase utiliza OSMDroid para la visualización del mapa y el cliente de ubicación de Google Play Services para obtener la posición del usuario. Además, incluye un marcador que señala la ubicación mostrada en el mapa.


## Clases Modificadas

- **`MainActivity`**: Se ha modificado el método `scheduleConnectivityWorker` en `MainActivity` para utilizar `PeriodicWorkRequestBuilder` en lugar de `OneTimeWorkRequestBuilder`, lo que permite ejecutar la tarea de conectividad de manera periódica y optimizar el uso de batería ademas hace de puente entre la actividad principal y las pantallas de mapas.
- **`NovelRepository`**: Concretamente los metodos de envio y traida de datos de Firebase (insert y update) han sido modificados aprovechando la compresion de datos de `RedOptima` para mejorar el uso de red de la app. 
- **`PantallaPrincipal`**: Aniadido un boton para abrir el mapa de novelas y otro para abrir el mapa de ubicacion del usuario.
## Clases Borradas

- **`NovelDatabase.java`**: Esta clase fue eliminada porque la aplicación ya no utiliza Room Database para el almacenamiento de datos, sino que ahora utiliza Firebase.
- **`NovelDao.java`**: Esta clase fue eliminada junto con `NovelDatabase.java` ya que definía las operaciones de base de datos para Room, las cuales ya no son necesarias.
- **`SyncJobService.java`**: Servicio para sincronización de datos en segundo plano.
- **`NotificationHelper.java`**: Clase para mostrar notificaciones.
- **`CargaNovelas.java`**: Loader para cargar novelas desde Firebase.
- **`NetworkChangeReceiver.java`**: Receptor para cambios en la conectividad de red.
- **`ConnectivityReceiver.java`**: Adaptador para conectividad de redes externas como wifi en la aplicación.
- **`NovelasWidgetProvider.kt`**: Este archivo define el proveedor del widget de la aplicación.

## Uso

1. **Agregar Novela**: Completa todos los campos y haz clic en "Agregar Novela".
2. **Editar Novela**: Haz clic en una novela de la lista, edita los campos y haz clic en "Actualizar Novela".
3. **Eliminar Novela**: Haz clic en el icono de eliminar junto a la novela que deseas eliminar.
4. **Marcar como Favorita**: Haz clic en el icono de corazón junto a la novela que deseas marcar o desmarcar como favorita.
5. **Login**: Ingresa tu email y contraseña y haz clic en "Login".
6. **Registro**: Ingresa tu email y contraseña y haz clic en "Register".
7. **Cambiar Tema**: Ve a la pantalla de configuración y haz clic en "Cambiar fondo" para alternar entre modo claro y oscuro.
8. **Ver Mapas**: En la pantalla principal, selecciona la opción para abrir el mapa de novelas o mapa de ususario. Esto te mostrará un mapa con las ubicaciones asociadas a cada novela en tu colección o la ubicacion actual del usuario segun el mapa.

## Dependencias

- Kotlin
- Jetpack Compose
- Firebase
- Material Design 3
- GSon
- OSMDroid
- Google Play Services