```
./mvnw clean package liberty:run
```

Once the runtime starts, you can access the project at [http://localhost:9080](http://localhost:9080).
```
./mvnw clean package
docker build -t patitas-suite:v1 .
```
#ORDEN DE COMO EJECUTAR EL PROYECTO
 Comando para limpiar el target (cualquiera de los 2):
 ./mvnw clean
 mvnd clean
 
 Comando  para Crear el Target (Cualquiera de los 2):
 ./mvnw package
 mvnd package
 
 Comando para levantar el docker (hay que tener previamente abierto el docker desktop):
 #Está ejecutando el docker compose en modo dev (desarrollo)
 docker compose --env-file docker-env -f docker-compose-dev-mysql.yml up --remove-orphans 

 Comando para levantar el servidor de liberty en modo desarrollo:
 ./mvnw liberty:dev
 mvnd liberty:dev

#Lo de abajo solo es necesario ejecutarlo despues de ejecutar los comandos de arriba (todos) y el proyecto por primera vez:
------------------------------------------------------------------------------------------------------------------------------------

#PARA EJECUTAR EN WINDOWS LOS VALORES INICIALES DE LAS TABLAS: 

docker cp src\main\resources\META-INF\initial-data.sql patitas-suite-mysql-db-container:/tmp/initial-data.sql

docker exec -it patitas-suite-mysql-db-container mysql -u patitas_admin -p patitas_suite

# Cuando te pida la contraseña, escribe:
p12345678
Si no funciona esa clave: adminer2526

# Luego dentro del prompt MySQL ejecuta:
source /tmp/initial-data.sql;

------------------------------------------------------------------------------------------------------------------------------------
#PARA EJECUTAR EN LINUX LOS VALORES INICIALES DE LAS TABLAS:

docker cp src/main/resources/META-INF/initial-data.sql patitas-suite-mysql-db-container:/tmp/initial-data.sql

docker exec -it patitas-suite-mysql-db-container mysql -u patitas_admin -p patitas_suite
# Cuando te pida la contraseña, escribe:
p12345678
Si no funciona esa clave: adminer2526

# Luego dentro del prompt MySQL ejecuta:
source /tmp/initial-data.sql;
------------------------------------------------------------------------------------------------------------------------------------
