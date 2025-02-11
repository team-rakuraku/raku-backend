FROM openjdk:11-jdk-slim

WORKDIR /app

# JAR 파일 복사 (올바른 방식)
COPY build/libs/*.jar app.jar

# MongoDB SSH 연결 (EC2 SSH 접속 & 포트 포워딩)
COPY src/main/resources/setup-ssh.sh /app/setup-ssh.sh
RUN chmod +x /app/setup-ssh.sh

# 실행 명령어
ENTRYPOINT ["/bin/sh", "-c", "/app/setup-ssh.sh && java -jar /app.jar"]

