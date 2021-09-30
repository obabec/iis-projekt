#!/usr/bin/env bash

# Docker stack for library iis project
LIBRARY_FILE=library.jar
function build_app() {
    ./mvnw clean package -DskipTests
    cp target/library-0.0.1-SNAPSHOT.jar docker/$LIBRARY_FILE
    pushd docker
}

while getopts ":dkh" option; do
   case $option in
      h)
        echo "Script for starting library application in docker stack.
              Application will be firstly build into docker image and than run in container
              together with PostgreSQL database."
        echo "Arguments:"
        echo "  -d starts stack in detached mode"
        echo "  -k stops application stack and delete docker image for application"
        echo "Default start up is in detached mode. Script will do this with no args filled."
        ;;
      d)
        build_app
        docker-compose up -d
        ;;
      k)
        pushd docker
        docker-compose down
        docker rmi library-iis:latest
        rm -rf $LIBRARY_FILE
        ;;
      *)
        build_app
        docker-compose up
        ;;
   esac
done
popd
exit 0