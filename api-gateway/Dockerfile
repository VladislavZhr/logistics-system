# 🐧 Використовуємо офіційний образ з Java 21
FROM eclipse-temurin:21-jdk-alpine

# 📁 Робоча директорія всередині контейнера
WORKDIR /app

# 📦 Копіюємо зібраний jar файл в контейнер
COPY target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

# 🔓 Відкриваємо порт
EXPOSE 8080

# 🚀 Запуск Spring Boot застосунку
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
