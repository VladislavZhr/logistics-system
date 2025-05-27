# Встановлюємо NodeJS
FROM node:18 as build

# Робоча директорія всередині контейнера
WORKDIR /app

# Копіюємо package.json та package-lock.json
COPY package*.json ./

# Встановлюємо залежності
RUN npm install

# Копіюємо інші файли
COPY . .

# Білдимо React додаток
RUN npm run build

# Сервер для обслуговування статичних файлів
FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
