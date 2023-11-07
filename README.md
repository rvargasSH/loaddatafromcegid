BUILD AND PACKAGE THE PROJECT
=============================

```sh
mvn clean  package
```

CREATE DOCKER IMAGE
===================

```sh

DOCKER_BUILDKIT=1 docker build --build-arg JAR_FILE=target/loaddatatotouroperator.jar -t reysie7e/load-data-to-touroperator:qa .
```

RUN AND STOP THE APP WITH DOCKER
================================

## RUN CONTAINER
```sh
--qa
docker container run -p 8085:8085 -d --network microservicesnetwork -e DATABASE_URL=jdbc:postgresql://10.238.107.217:5432/touroperator?currentSchema=public -e DATABASE_USERNAME=postgres -e DATABASE_PASSWORD=ShColombia2023 -e EUREKA_URL=http://10.238.107.217:8761/eureka -e ADMIN_SERVICE_URL=http://10.238.107.217:8083 -e SERVER_PORT=8085 -e SFTP_HOST=10.238.107.223 -e SFTP_USER=ftpuser1  --name load-data-to-touroperator reysie7e/load-data-to-touroperator:qa
--prod
docker container run -p 8085:8085 -d --network microservicesnetwork -e DATABASE_URL=jdbc:postgresql://10.238.107.219:5432/touroperator?currentSchema=public -e DATABASE_USERNAME=postgres -e DATABASE_PASSWORD=ShColombia2023 -e EUREKA_URL=http://10.238.107.219:8761/eureka -e ADMIN_SERVICE_URL=http://10.238.107.219:8083 -e SERVER_PORT=8085 -e SFTP_HOST=10.238.107.223 -e SFTP_USER=ftpuser1  --name load-data-to-touroperator reysie7e/load-data-to-touroperator:pro

```

## STOP CONTAINER AND REMOVE CONMTAINER
```sh
docker container stop load-data-to-touroperator
docker container rm -f load-data-to-touroperator
```