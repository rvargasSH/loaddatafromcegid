FROM eclipse-temurin:17-jre-alpine AS builder
ARG JAR_FILE=target/*.jar
COPY $JAR_FILE app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:17-jre-alpine
ENV DATABASE_URL=
ENV DATABASE_USERNAME=
ENV DATABASE_PASSWORD=
ENV EUREKA_URL=
ENV SERVER_PORT=
ENV ADMIN_SERVICE_URL=
ENV SFTP_HOST=
ENV SFTP_USER=
EXPOSE 8081/tcp
RUN addgroup -S touroperatorapp && adduser -S touroperatorapp -G touroperatorapp
USER touroperatorapp
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder application/ ./
ENTRYPOINT java org.springframework.boot.loader.JarLauncher --dburl=$DATABASE_URL --dbusername=$DATABASE_USERNAME --dbpassword=$DATABASE_PASSWORD --eurekaUrl=$EUREKA_URL --port=$SERVER_PORT  --adminserviceurl=$ADMIN_SERVICE_URL  --sftp_host=$SFTP_HOST --sftp_user=$SFTP_USER