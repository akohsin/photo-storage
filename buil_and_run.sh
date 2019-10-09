#!/bin/bash

cd photo-storage-backend
mvn package
cd ..
docker-compose build
docker-compose up -d
