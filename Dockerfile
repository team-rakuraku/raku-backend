FROM openjdk:11-jdk-slim

WORKDIR /app

# JAR 파일 복사 (정확한 JAR 파일명 사용)
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# MongoDB SSH 연결 (EC2 SSH 접속 & 포트 포워딩)
COPY ./src/main/resources/setup-ssh.sh /app/setup-ssh.sh
RUN chmod +x /app/setup-ssh.sh

# 실행 명령어
ENTRYPOINT ["/bin/sh", "-c", "/app/setup-ssh.sh && java -jar /app.jar"]

