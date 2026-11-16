FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn --batch-mode -DskipTests package

FROM tomcat:10.1-jdk17-temurin
COPY --from=build /workspace/target/garaman.war /usr/local/tomcat/webapps/ROOT.war
ENV GARAMAN_DB_URL="jdbc:mysql://db:3306/garaman_db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Ho_Chi_Minh"
ENV GARAMAN_DB_USER="garaman"
ENV GARAMAN_DB_PASSWORD="garaman_local_password"
EXPOSE 8080
