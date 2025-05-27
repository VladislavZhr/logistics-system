# Базовий імедж з підтримкою Java 21
FROM eclipse-temurin:21-jdk-alpine

# Робоча директорія всередині контейнера
WORKDIR /app

# Копіюємо зібраний jar-файл у контейнер
COPY target/notification-service-0.0.1-SNAPSHOT.jar app.jar

# Вказуємо порт (повинен відповідати тому, що в application.yml)
EXPOSE 8082

# Команда запуску
ENTRYPOINT ["java", "-jar", "app.jar"]
