#Build application
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /builder
COPY pom.xml .
COPY checkstyle.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Builder stage
FROM eclipse-temurin:17-jdk AS extractor
WORKDIR /builder
COPY --from=builder /builder/target/*.jar application.jar
RUN java -Djarmode=tools -jar application.jar extract --layers --destination extracted

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /application
COPY --from=extractor /builder/extracted/dependencies/ ./
COPY --from=extractor /builder/extracted/spring-boot-loader/ ./
COPY --from=extractor /builder/extracted/snapshot-dependencies/ ./
COPY --from=extractor /builder/extracted/application/ ./
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]