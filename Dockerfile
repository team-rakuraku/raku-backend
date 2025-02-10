FROM openjdk:11-jdk-slim
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

# MongoDB 패키지 설치
RUN apt-get update && apt-get install -y wget gnupg curl \
    && curl -fsSL https://pgp.mongodb.com/server-6.0.asc | gpg --dearmor -o /usr/share/keyrings/mongodb-server-keyring.gpg \
    && echo "deb [signed-by=/usr/share/keyrings/mongodb-server-keyring.gpg arch=amd64,arm64] https://repo.mongodb.org/apt/ubuntu focal/mongodb-org/6.0 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-6.0.list \
    && apt-get update && apt-get install -y mongodb-org-tools \
    && rm -rf /var/lib/apt/lists/*

