# Docker image for {tomcat? springBoot?}
# VERSION {版本号}
# Author: uhyils
# docker stop prod-my-service-user
# docker rm prod-my-service-user
# docker rmi my-service/prod-my-service-user:latest
# docker build --build-arg version=0.0.7-my-SNAPSHOT --build-arg runEnv=prod -t my-service/prod-my-service-user .
# docker run -it -d  -p 8061:8061 -p 2085220861:20861 -v /my/logs:/my/logs --name prod-my-service-user my-service/prod-my-service-user:latest

FROM java:8

#作者
MAINTAINER uhyils <uhyils@qq.com>


#挂载
VOLUME /my/logs

#暴露端口
EXPOSE 8061 20861

#应用构建成功后的jar文件被复制到镜像内，名字也改成了app.jar
ADD target/my-*.jar /app.jar

#启动容器时的进程
ENTRYPOINT ["java","-Xms512m","-Xmx512m","-XX:+PrintGCTimeStamps","-XX:+UseG1GC","-XX:+HeapDumpOnOutOfMemoryError","-XX:HeapDumpPath=/my/logs/myOrder/JVM_DOWN.dump","-XX:NewRatio=1","-Xloggc:/my/logs/myOrder/JVM.log","-jar","/app.jar","--spring.profiles.active=prod"]
