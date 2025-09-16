# Etapa 1: build com Maven
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copia o pom e resolve dependências em cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o restante do código e compila
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: imagem final para rodar o app
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Copia apenas o jar final da etapa de build
COPY --from=builder /app/target/sga-0.1.jar app.jar

# Expõe a porta configurada (8086)
EXPOSE 8086

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]