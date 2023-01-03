# Use the official Maven image as a base
FROM maven:3.6.3-openjdk-11

# Copy the pom.xml file and the src directory to the working directory
COPY pom.xml .
COPY /src ./src
COPY /Datalake ./Datalake
COPY /Datamart ./Datamart
COPY /Stopwords ./Stopwords

# Build the project with Maven
RUN mvn clean package

COPY /target/searchengine_jar/SearchEngine.jar /app/search-engine.jar

# Run the search engine when the container starts
CMD ["java", "-jar", "/app/search-engine.jar"]