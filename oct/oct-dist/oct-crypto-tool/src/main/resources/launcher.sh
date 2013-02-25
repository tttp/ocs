#!/bin/bash
#if [ -f ${JAVA_HOME}/bin/java ] then
#    echo "!!! Bad JAVA_HOME environment variable: ${JAVA_HOME} is not pointing to a valid JRE"
#    exit 1
#fi
MEM_ARGS="-Xms128m -Xmx256m"
${JAVA_HOME}/bin/java ${MEM_ARGS} -jar ./../lib/oct-offline-1.0.0.jar
