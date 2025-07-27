
```
./mvnw clean package liberty:run
```

Once the runtime starts, you can access the project at [http://localhost:9080](http://localhost:9080).
```
./mvnw clean package
docker build -t patitas-suite:v1 .
```

You can then run the Docker image by executing:
------------------------------------------------------------------------------------------------------------------------------------

PARA EJECUTAR EN WINDOWS LOS VALORES INICIALES DE LAS TABLAS: 

docker cp src\main\resources\META-INF\initial-data.sql patitas-suite-mysql-db-container:/tmp/initial-data.sql

docker exec -it patitas-suite-mysql-db-container mysql -u patitas_admin -p patitas_suite

# Cuando te pida la contraseña, escribe:
# p12345678

source /tmp/initial-data.sql;

------------------------------------------------------------------------------------------------------------------------------------
PARA EJECUTAR EN LINUX LOS VALORES INICIALES DE LAS TABLAS:

docker cp src/main/resources/META-INF/initial-data.sql patitas-suite-mysql-db-container:/tmp/initial-data.sql

docker exec -it patitas-suite-mysql-db-container mysql -u patitas_admin -p patitas_suite
# Cuando te pida la contraseña, escribe:
# p12345678

# Luego dentro del prompt MySQL ejecuta:
source /tmp/initial-data.sql;
------------------------------------------------------------------------------------------------------------------------------------
