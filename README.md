# Search Engine
Full implementation of a search engine using an inverted index structure,as well as creating an image of the entire project using Docker and implementing Load Balancer with Nginx.
## Overview 📋
The code comprises of the following architecture:

* Indexer, that extracts all the words from each document and builds the inverted index into a Datamart.

* Crawler, a small program that periodically determines what documents to download and feeds them to the document repository in Datalake.

* Stopwords folder, containing all stopwords stored in text files in three different languages; Spanish, English and French.

* A query engine that publishes an API for receiving search requests from users.


## The code 🔧
*Java 11 and json are pre-requirements for the execution of this project*

Ensure all the files from the repo are present in the same directory.

## Insight on the implementation ⚙️

The *API* module handles requests that are delivered to the system through the client. Later, this very request is processed and filtered through different methods in classes which have specific functionalities and are comprised within a module, the result is shown if there are matches to the given query. A *Datalake* and *Datamart* repository will contain all the data obtained from the *Crawler* and the *Indexer*. 

The engine in it's final state, is deployed into a docker container, to solve *scalability*. As well as a cluster is built with the following containers to distribute the load of tasks to various systems using Load Balancer with Nginx, this in in turn solves the problem of *availability*.

## Docker Deployment <img src="https://user-images.githubusercontent.com/92883393/210415818-bd1c73b0-4844-42a2-ade0-0800e6d37fad.png" width="60">

The Docker image is to be created when a JAR of the project is built, the manifest file specifies the right path for the main class and when the Dockerfile has the right commands to compile the project with all of it's dependencies into an image. 
Within a container, the image is then posteriorly executed, either through the command line, Docker Desktop or even with the Docker plugin in Intellij.

The image for this project is published in DockerHub publicly, within a repository: https://hub.docker.com/r/krisht97/search_engine

## Built With 🛠️

* [Intellij Idea](https://www.jetbrains.com/es-es/idea/) - The text editor used.
* [Java 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html) - The java version used.

## Authors ✒️
* **Javier García**
* **Jesús Matos**
* **Liam Mahmud**
* **Krish Sadhwani**
