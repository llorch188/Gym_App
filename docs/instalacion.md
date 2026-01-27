# Instalación / Acceso :gear:

A continuación se muestran las opciones para instalar o acceder a Gym App en los distintos sistemas operativos.

## Requisitos previos
- :email: Cuenta de usuario en la app (registro por email)
- :globe_with_meridians: Conexión a internet para sincronización (opcional modo offline limitado)

## Opciones de acceso
Android
    1. Abre Google Play Store.
    2. Busca "Gym App".
    3. Pulsa "Instalar" y accede con tu cuenta.

iOS :iphone:
    1. Abre App Store.
    2. Busca "Gym App".
    3. Pulsa "Obtener" y accede con tu cuenta.

Web :computer:
    ```bash
    # Accede desde el navegador:
    https://gym-app.example.com
    ```

"Advertencia de compatibilidad" :warning:

En dispositivos muy antiguos algunas funciones gráficas pueden no mostrarse correctamente.

## Instalación desde código (desarrolladores o builds locales) :hammer_and_wrench:
A continuación un ejemplo real de cómo ejecutar un servidor local para pruebas (si tu proyecto lo soporta).

    ```bash
# Clona el repositorio
git clone https://github.com/llorch188/Gym_App.git
cd Gym_App

# Instala dependencias (ejemplo con Gradle — sustituye por tu sistema real)
./gradlew build
./gradlew run