FROM maven:3.6.3-openjdk-11

COPY pom.xml .
COPY /src ./src
COPY /Datalake ./Datalake
COPY /Datamart ./Datamart
COPY /Stopwords ./Stopwords

RUN mvn clean package

COPY /target/searchengine_jar/SearchEngine.jar /app/search-engine.jar

CMD ["java", "-jar", "/app/search-engine.jar"]
