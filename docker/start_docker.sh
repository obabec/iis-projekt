#!/usr/bin/env bash

# Docker stack for library iis project
LIBRARY_FILE=app/library.jar
function build_app() {
    echo "Building the application"
    ./mvnw clean package -DskipTests
    cp target/library-0.0.1-SNAPSHOT.jar docker/$LIBRARY_FILE
    pushd docker
}

while getopts ":adkh" option; do
   case $option in
      a)
        echo "Deploying compose stack attached"
        build_app
        docker-compose up
        ;;
      d)
        echo "Deploying compose stack deattached"
        build_app
        docker-compose up -d
        ;;
      k)
        echo "Removing stack and deleting docker image"
        pushd docker
        docker-compose down
        docker rmi library-iis:latest
        docker rmi db-iis:latest
        rm -rf $LIBRARY_FILE
        ;;
      h | *)
        echo "Script for starting library application in docker stack.
              Application will be firstly build into docker image and than run in container
              together with PostgreSQL database."
        echo "Arguments:"
        echo "  -d starts stack in detached mode"
        echo "  -a starts stack in attached mode"
        echo "  -k stops application stack and delete docker image for application"
        echo "Default start up is in detached mode. Script will do this with no args filled."
        ;;
   esac
done
exit 0