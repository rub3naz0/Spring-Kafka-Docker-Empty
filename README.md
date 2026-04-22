# Spring-Kafka-Docker-Empty
Proyecto con el esqueleto para microservicios con Spring y Kafka corriendo en Docker

## Requisitos
Para correr este proyecto es necesario tener instalado Docker y Docker Compose en tu máquina. 

## Estructura del proyecto
El proyecto está organizado en dos servicios principales:
1. **KafkaProducer**: Este servicio se encarga de enviar mensajes a un tópico de Kafka. Está ubicado en el directorio `KafkaProducer`.
2. **KafkaConsumer**: Este servicio se encarga de recibir mensajes del tópico de Kafka. Está ubicado en el directorio `KafkaConsumer`.
3. **Kafka**: El servicio de Kafka se define en el archivo `docker-compose.yml` y se ejecuta en un contenedor separado.
4. **Zookeeper**: Kafka depende de Zookeeper para su funcionamiento, y también se define en el archivo `docker-compose.yml`.
5. **Docker Compose**: El archivo `docker-compose.yml` se encarga de orquestar los contenedores de Kafka, Zookeeper, Producer y Consumer.
6. **README.md**: Este archivo proporciona una descripción general del proyecto y las instrucciones para ejecutarlo.
7. **.gitignore**: Este archivo especifica los archivos y directorios que Git debe ignorar, como los archivos de configuración y los archivos generados por el sistema.

## Ejecución del proyecto
Para ejecutar el proyecto, sigue estos pasos:
1. Clona el repositorio en tu máquina local.
2. Navega al directorio raíz del proyecto.
3. Ejecuta el siguiente comando para iniciar los contenedores de Docker:
```
bash docker-compose up
```
4. Una vez que los contenedores estén en funcionamiento, puedes enviar mensajes al tópico de Kafka utilizando el servicio KafkaProducer. Los mensajes enviados serán recibidos por el servicio KafkaConsumer.
5. Para detener los contenedores, puedes usar el siguiente comando:
```
bash docker-compose down
```
