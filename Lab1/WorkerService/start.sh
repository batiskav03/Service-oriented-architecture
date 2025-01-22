#!/bin/bash

# Настройка параметров БД из переменных окружения
DB_HOST=${DB_HOST:-localhost}
DB_PORT=${DB_PORT:-5432}
DB_NAME=${DB_NAME:-worker_db}
DB_USER=${DB_USER:-postgres}
DB_PASSWORD=${DB_PASSWORD:-postgres}

# Запуск домена с новыми портами
asadmin start-domain --verbose

# Создание пула соединений
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

# Создание JDBC ресурса
asadmin create-jdbc-resource --connectionpoolid WorkerJDBCPool jdbc/WorkerDS

# Развертывание приложений
asadmin deploy ${DEPLOY_DIR}/worker-ejb-1.0-SNAPSHOT.jar
asadmin deploy ${DEPLOY_DIR}/worker-web-1.0-SNAPSHOT.war

# Держим процесс активным
tail -f ${PAYARA_DIR}/glassfish/domains/domain1/logs/server.log 