FROM openjdk:8-alpine

MAINTAINER Scott liuweigeek@outlook.com

ENV LANG C.UTF-8
ENV TIME_ZONE Asia/Shanghai
RUN echo "${TIME_ZONE}" > /etc/timezone && ln -sf /usr/share/zoneinfo/${TIME_ZONE} /etc/localtime

ARG JAR_FILE
ADD target/${JAR_FILE} app.jar

EXPOSE 8080

ARG PROFILE
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=${PROFILE}"]