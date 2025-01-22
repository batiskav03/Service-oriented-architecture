# back
# Используем Payara с Java 17
FROM payara/server-full:6.2024.1-jdk17

LABEL maintainer="2262288@gmail.com"

USER root

# Копируем EJB модуль
COPY ./worker-ejb/target/worker-ejb-1.0-SNAPSHOT.jar ${DEPLOY_DIR}/

# Копируем веб-модуль
COPY ./worker-web/target/worker-web-1.0-SNAPSHOT.war ${DEPLOY_DIR}/

# Копируем скрипт запуска
COPY ./start.sh /opt/start.sh
RUN chmod +x /opt/start.sh

# Меняем владельца файлов на payara
RUN chown -R payara:payara ${DEPLOY_DIR} ${PAYARA_DIR} /opt/start.sh

USER payara

ENTRYPOINT ["/opt/start.sh"]