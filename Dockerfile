FROM openjdk:11-jdk-slim

WORKDIR /app

# JAR 파일 복사 (정확한 JAR 파일명 사용)
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# application.properties 복사
COPY src/main/resources/application.properties /app/config/application.properties

# MongoDB 패키지 설치
RUN apt-get update && apt-get install -y wget gnupg curl \
    && curl -fsSL https://pgp.mongodb.com/server-6.0.asc | gpg --dearmor -o /usr/share/keyrings/mongodb-server-keyring.gpg \
    && echo "deb [signed-by=/usr/share/keyrings/mongodb-server-keyring.gpg arch=amd64,arm64] https://repo.mongodb.org/apt/ubuntu focal/mongodb-org/6.0 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-6.0.list \
    && apt-get update && apt-get install -y mongodb-org-tools \
    && rm -rf /var/lib/apt/lists/*

# MongoDB SSH 연결 (EC2 SSH 접속 & 포트 포워딩)
COPY /home/ec2-user/raku-backend/src/main/resources/setup-ssh.sh /app/setup-ssh.sh
RUN chmod +x /app/setup-ssh.sh

# 실행 명령어
ENTRYPOINT ["/bin/sh", "-c", "/app/setup-ssh.sh && java -jar /app.jar"]

