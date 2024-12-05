FROM openjdk:21-jdk-slim
WORKDIR /app

COPY build/install/launch/ server/

EXPOSE 8080
CMD ["./server/bin/launch"]