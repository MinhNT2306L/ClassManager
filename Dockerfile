FROM tomcat:10.1-jdk17

RUN rm -rf /usr/local/tomcat/webapps/*

RUN mkdir -p /usr/local/tomcat/webapps/ROOT

COPY target/ClassManager-1.0-SNAPSHOT.war /tmp/ROOT.war

WORKDIR /usr/local/tomcat/webapps/ROOT
RUN jar -xvf /tmp/ROOT.war && rm /tmp/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]