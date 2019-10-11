#!/usr/bin/env bash

if [[ -f ${PROPERTIES_FILE} ]]; then
   PROPERTIES=-Dspring.config.location=${PROPERTIES_FILE}
fi

java -Djava.security.egd=file:/dev/./urandom \
     ${PROPERTIES} \
     -XX:+PrintFlagsFinal \
     ${JAVA_OPTIONS} \
     -jar ni-server.jar
