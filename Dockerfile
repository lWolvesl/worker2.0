FROM java:8
MAINTAINER li
ADD worker2.0-0.0.1-SNAPSHOT.jar worker2.jar
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
EXPOSE 8989
ENTRYPOINT ["java","-jar","worker2.jar"]
