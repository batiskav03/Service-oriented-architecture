FROM bitnami/wildfly:32.0.0

COPY ./worker-ejb/worker-ejb-1.0-SNAPSHOT-jar-with-dependencies.jar /opt/bitnami/wildfly/standalone/deployments/
COPY ./wildfly/standalone.xml /opt/bitnami/wildfly/standalone/configuration/
COPY ./wildfly/module.xml /opt/bitnami/wildfly/modules/system/layers/base/org/postgresql/main/
COPY ./wildfly/postgresql-42.7.3.jar /opt/bitnami/wildfly/modules/system/layers/base/org/postgresql/main/

RUN /opt/bitnami/wildfly/bin/add-user.sh -a -u user -p user
RUN /opt/bitnami/wildfly/bin/add-user.sh -u admin -p admin

CMD ["/opt/bitnami/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]