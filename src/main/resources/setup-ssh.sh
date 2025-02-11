#!/bin/sh

echo "✅ Setting up SSH Tunnel to MongoDB..."
mkdir -p /root/.ssh
chmod 700 /root/.ssh

# SSH Key 설정
echo "$MONGO_SSH_KEY" > /root/.ssh/mongodb-key.pem
chmod 400 /root/.ssh/mongodb-key.pem

# SSH 포트 포워딩 설정
ssh -i /root/.ssh/mongodb-key.pem -o StrictHostKeyChecking=no -o IdentitiesOnly=yes -N -L 27017:127.0.0.1:27017 ec2-user@${MONGO_HOST} &

echo "✅ SSH Tunnel established. Starting application..."
exec java -jar /app.jar

