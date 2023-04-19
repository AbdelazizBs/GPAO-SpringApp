FROM openjdk:11
LABEL maintainer="Dockerfile Maintainer"
ADD target/housstock-0.0.1-SNAPSHOT.jar housstock-0.0.1-SNAPSHOT.jar
#EXPOSE 8080
ENTRYPOINT ["java","-jar","housstock-0.0.1-SNAPSHOT.jar"]
   #run this cmd in the CLI : docker build -t azzouz97/housstock-backend:1.0.0 .