#!/bin/bash

cd photo-storage-backend
mvn package
cd ..
docker-compose up
