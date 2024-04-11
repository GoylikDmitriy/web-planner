# Инструкция по запуску

Для запуска необходим установленный Docker.

## 1. Настройка базы данных

Перед запуском проекта необходимо настроить соединение с базой данных PostgreSQL. Для этого выполните следующие действия:

1. Откройте файл `docker-compose.yml` в корневой директории проекта.

2. В разделе `postgres-planner` измените значения переменных окружения `POSTGRES_DB`, `POSTGRES_USER` и `POSTGRES_PASSWORD` на свои настройки базы данных. Например:

   ```
   environment:
     - POSTGRES_DB=db
     - POSTGRES_USER=username
     - POSTGRES_PASSWORD=password
   ```

3. В разделе `planner` измените значения переменных окружения `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`. Например

    ```
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-planner:5432/db
      - SPRING_DATASOURCE_USERNAME=username
      - SPRING_DATASOURCE_PASSWORD=password
    ```
4. Сохраните изменения в файле `docker-compose.yml`.

## 2. Запуск проекта

1. Откройте командную строку или терминал в корневой папке, где находится файл `docker-compose.yml`.

2. Выполните следующую команду для запуска проекта:

   ```docker-compose
   docker-compose up
   ```

3. После успешного запуска, доступ к приложению можно получить по адресу `http://localhost:3000`.

## Примечание

- Если хотите изменить переменные окружения, можно отредактировать файл `docker-compose.yml`.

- Также можно передать эти значения при запуске контейнеров, не изменяя сам `docker-compose`. Например:

   ```docker-compose
   docker-compose up -e POSTGRES_DB=db -e POSTGRES_USER=username -e POSTGRES_PASSWORD=password -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-planner:5432/db -e SPRING_DATASOURCE_USERNAME=username -e SPRING_DATASOURCE_PASSWORD=password
   ```
   
- При запуске, в схеме `public`, будут созданы две таблицы `employees и tasks` и вставлены начальные данные.
