# back
# устанавливаем самую лёгкую версию JVM
FROM payara/server-full:5.2022.2

LABEL maintainer="2262288@gmail.com"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=./target/WorkerService-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} backend.jar

# Копируем драйвер PostgreSQL
COPY worker-ejb/postgresql-42.2.27.jar ${PAYARA_DIR}/glassfish/lib/

# Копируем EJB модуль
COPY worker-ejb/target/worker-ejb.jar $DEPLOY_DIR

# Копируем веб-модуль
COPY worker-web/target/worker-web.war $DEPLOY_DIR

# Копируем команды конфигурации
COPY post-boot-commands.asadmin $PAYARA_DIR/post-boot-commands.asadmin

# Настройка Consul агента
RUN apt-get update && apt-get install -y curl unzip
RUN curl -fsSL https://releases.hashicorp.com/consul/1.11.3/consul_1.11.3_linux_amd64.zip -o consul.zip \
    && unzip consul.zip \
    && mv consul /usr/local/bin/ \
    && rm consul.zip

# Скрипт запуска
COPY start.sh /opt/start.sh
RUN chmod +x /opt/start.sh

CMD ["/opt/start.sh"]