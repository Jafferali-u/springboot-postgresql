FROM jafferaliu/springboot-postgresql

RUN yum install -y \
       java-1.8.0-openjdk \
       java-1.8.0-openjdk-devel

WORKDIR /app
RUN export PATH=$PATH:/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.242.b08-0.el8_1.x86_64/jre/bin
RUN echo "JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:bin/java::")" | tee -a /etc/profile && source /etc/profile && echo java_home is $JAVA_HOME
ENV PATH="/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.242.b08-0.el8_1.x86_64/jre:${PATH}"
ENV JAVA_HOME="/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.242.b08-0.el8_1.x86_64/jre"
RUN java -version
RUN echo $JAVA_HOME
RUN echo $PATH
WORKDIR /app
ADD springboot-postgresql/ /app/springboot-postgresql
WORKDIR /app/springboot-postgresql
RUN ./gradlew clean build
#COPY springboot-postgresql/build/libs/springboot-postgresql-0.0.1-SNAPSHOT.jar app.jar
VOLUME ["/app"]
EXPOSE 9090
ENTRYPOINT ["java","-jar","build/libs/springboot-postgresql-0.0.1-SNAPSHOT.jar"]
