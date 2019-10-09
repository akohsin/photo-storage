#!/bin/bash

cd photo-storage-backend
mvn package
cd ..
docker-compose down
docker-compose build
docker-compose up -d
