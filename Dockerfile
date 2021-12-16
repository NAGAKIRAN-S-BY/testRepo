FROM adoptopenjdk/openjdk11:alpine-slim
ARG RELEASE_VERSION=*
ARG JAR_FILE=service/build/libs/exec-ud-daas-etl-service-$RELEASE_VERSION-application.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=prod

ENV SERVICE_NAME=exec-ud-daas-etl-service
ENV JAVA_ARGS=""

# AppD Setup
ENV APPDYNAMICS_AGENT_TIER_NAME=""
ENV APPDYNAMICS_AGENT_NODE_NAME=""

ENV APPD_DIR="/opt/appdynamics/appagent"
COPY service/build/docker/appdynamics-0.1.0-appdynamics.zip /tmp/appdynamics.zip
RUN mkdir -p $APPD_DIR && \
    unzip -oq /tmp/appdynamics.zip -d $APPD_DIR && \
    rm /tmp/appdynamics.zip
COPY appdynamics/custom-interceptors.xml $APPD_DIR/ver20.6.0.30246/conf/

# Elastic APM Setup
RUN mkdir -p /opt/elastic-apm
COPY service/build/docker/elastic-apm-agent-*.jar /opt/elastic-apm/agent.jar

# Run container as non root user
RUN addgroup -S nonrootgrp -g 2000
RUN adduser -S nonroot -G nonrootgrp --uid 2000
RUN chown -R 2000 $APPD_DIR
USER 2000

CMD if [ "$APPDYNAMICS_AGENT_ACCOUNT_ACCESS_KEY" != "" ]; then \
      APPDYNAMICS_AGENT_TIER_NAME=$SERVICE_NAME-$ENV_NAME \
      UNIQUE_HOST_ID=$(sed -rn '1s#.*/##; 1s/(.{12}).*/\1/p' /proc/self/cgroup) \
      JAVA_ARGS="-Dappdynamics.agent.uniqueHostId=$UNIQUE_HOST_ID -Dappdynamics.docker.container.containerIdAsHostId.enabled=true; -javaagent:/opt/appdynamics/appagent/javaagent.jar"; \
    elif [ "$ELASTIC_APM_SERVER_URL" != "" ]; then \
      JAVA_ARGS="-javaagent:/opt/elastic-apm/agent.jar -Delastic.apm.service_name=$SERVICE_NAME-$ENV_NAME  -Delastic.apm.application_packages=com.blueyonder -Delastic.apm.server_url=$ELASTIC_APM_SERVER_URL"; \
    fi; \
    java $JAVA_ARGS -jar /app.jar
