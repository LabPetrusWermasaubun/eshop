
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /src/advshop

# copy seluruh project
COPY . .

# pastikan gradlew bisa dieksekusi
RUN chmod +x gradlew

# build Spring Boot jar
RUN ./gradlew clean bootJar

FROM eclipse-temurin:21-jre-alpine AS runner

# argument untuk user non-root
ARG USER_NAME=advshop
ARG USER_UID=1000
ARG USER_GID=${USER_UID}

# buat group & user
RUN addgroup -g ${USER_GID} ${USER_NAME} \
 && adduser -h /opt/advshop -D -u ${USER_UID} -G ${USER_NAME} ${USER_NAME}

# gunakan user non-root
USER ${USER_NAME}

WORKDIR /opt/advshop

# copy hasil build dari stage builder
COPY --from=builder --chown=${USER_UID}:${USER_GID} \
    /src/advshop/build/libs/*.jar app.jar

# expose port Spring Boot
EXPOSE 8080

# jalankan aplikasi
ENTRYPOINT ["java"]
CMD ["-jar", "app.jar"]