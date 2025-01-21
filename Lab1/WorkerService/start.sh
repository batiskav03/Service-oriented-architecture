#!/bin/bash

# Настройка параметров БД из переменных окружения
DB_HOST=${DB_HOST:-localhost}
DB_PORT=${DB_PORT:-5432}
DB_NAME=${DB_NAME:-worker_db}
DB_USER=${DB_USER:-postgres}
DB_PASSWORD=${DB_PASSWORD:-postgres}

# Обновление параметров пула соединений
asadmin start-domain
asadmin create-jdbc-connection-pool \
    --datasourceclassname org.postgresql.ds.PGSimpleDataSource \
    --restype javax.sql.DataSource \
    --property portNumber=${DB_PORT}:\
    serverName=${DB_HOST}:\
    databaseName=${DB_NAME}:\
    user=${DB_USER}:\
    password=${DB_PASSWORD}:\
    url="jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}" \
    WorkerJDBCPool

# Запуск Consul агента
consul agent -dev -client=0.0.0.0 &

# Запуск Payara
$PAYARA_DIR/bin/asadmin start-domain --verbose 