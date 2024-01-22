
# Android Recipes

Proyecto Master-Detail simple donde implementar todo lo relacionado con esta tecnología.
Necesitas un **APIKEY** de Chatgpt.

### Arquitectura
Este proyecto utiliza una Clean Architecture, busco separar la lógica en capas para mejorar la modularidad, flexibilidad y mantenibilidad.

Para la interfaz de usuario he combinado el patrón de diseño MVVM (Model-View-ViewModel).

| **Capa**              | **Función** |
| ---                   | ---       |
| **Data**             |    En esta capa, nos enfocamos en la manipulación de datos y la interacción con la capa de almacenamiento local y remoto. Room, nuestra base de datos local, se utiliza para el almacenamiento persistente de datos, permitiendo una gestión eficiente y escalable de la información.      |
| **Di**               |    La capa de Inyección de Dependencias (DI) es esencial para proporcionar una estructura flexible y desacoplada. Utilizando Koin, un inyector de dependencias ligero, logramos una configuración más sencilla y mantenible. Esto facilita la gestión de componentes y la sustitución de implementaciones, promoviendo la reutilización del código.    |
| **Domain**           |    En el corazón de la aplicación, la capa Domain alberga la lógica de negocio. Aquí definimos las reglas y procesos fundamentales que gobiernan la aplicación, asegurando que la funcionalidad cumpla con los requisitos y objetivos del negocio.    |
| **Model**            |    Esta capa se encarga de definir los modelos de datos utilizados en la aplicación. Estos modelos actúan como representaciones estructuradas de la información que fluye a través de la aplicación, facilitando la consistencia y la comprensión de los datos en todas las capas.    |
| **Presentación**     |    La capa de Presentación se centra en la interfaz de usuario y la interacción del usuario. Implementando el patrón MVVM, separamos la lógica de presentación (ViewModel) de la interfaz de usuario (View), lo que facilita la escalabilidad y prueba de la aplicación. LiveData se utiliza para una actualización reactiva de la interfaz de usuario, manteniendo una experiencia fluida.    |

## Librerías 
Librerias y herramientas que he utilizado en esta aplicación. 

- **Room:** Base de datos local.

- **Koin:** Inyector de dependencias.

- **Componente Navigation:** Gestión de navegación.

- **ViewBinding:** Acceso a vistas.

- **Live Data:** Gestión de datos.

- **Coroutines:** Asincronía.

- **Retrofit:** Gestión y comunicación con API.

- **OkHttp:** Servicios de red.

- **Gson:** Convertir datos json.

- [Animated Button Bar](https://github.com/Droppers/AnimatedBottomBar): Barra de navegación entre fragments.

## Chatgpt API 

#### Ejemplo

```
  POST https://api.openai.com/v1/completions
```
#### Headers
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| Content-Type | application/json |  
| Bearer `api_key` | `string` | **Required**. Your API key |

#### Data
| Parameter | Type     |
| :-------- | :------- | 
| `model` | `string` |  
| `prompt` | `string` | 
| `max_tokens` | `int` | 
| `temperature` | `int` | 


## Autor
El feedback es vital para aprender y mejorar.

Debatir y aportar ideas permite aprender y forjarse en el camino, puedes enviarme un mensaje a mí linkedIn.
- [Pablo Mediero](https://www.linkedin.com/in/pablo-mediero-mart%C3%ADn/)

