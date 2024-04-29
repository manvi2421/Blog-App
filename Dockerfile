FROM openjdk:8
EXPOSE 8080
ADD target/blogapp.jar blogapp.jar
ENTRYPOINT [ "java","-jar","blogapp.jar" ]