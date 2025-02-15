#!/bin/sh

echo "✅ Setting up SSH Tunnel to MongoDB..."
mkdir -p /app/.ssh
chmod 700 /app/.ssh

# SSH Key 설정
echo "$MONGO_SSH_KEY" > /app/.ssh/raku-key.pem
chmod 400 /app/.ssh/raku-key.pem

# SSH 포트 포워딩 설정
ssh -i /home/ec2-user/raku-key.pem -o StrictHostKeyChecking=no -o IdentitiesOnly=yes -N -L 27017:127.0.0.1:27017 ec2-user@${MONGO_HOST} &

echo "✅ SSH Tunnel established. Starting application..."
exec java -jar /app.jar

