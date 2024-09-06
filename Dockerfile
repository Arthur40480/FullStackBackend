# Utiliser une image de base OpenJDK
FROM openjdk:8-jdk

# Ajouter un argument pour le fichier JAR généré par Maven ou Gradle
ARG JAR_FILE=target/FullStackBackend-0.0.1-SNAPSHOT.jar

# Copier le fichier JAR dans l'image
COPY ${JAR_FILE} app.jar

# Exposer le port 8080
EXPOSE 8080

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "/app.jar"]