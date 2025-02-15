FROM openjdk:11-jdk-slim

WORKDIR /app

RUN apt-get update && apt-get install -y openssh-client

# JAR 파일 복사 (올바른 방식)
ARG JAR_FILE=build/libs/rakuraku-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar

# application.properties 파일 복사 추가
COPY src/main/resources/application.properties /app/config/application.properties

# MongoDB SSH 연결 (EC2 SSH 접속 & 포트 포워딩)
COPY src/main/resources/setup-ssh.sh /app/setup-ssh.sh
RUN chmod +x /app/setup-ssh.sh

# 실행 명령어
ENTRYPOINT ["/bin/sh", "-c", "/app/setup-ssh.sh && java -jar /app.jar"]