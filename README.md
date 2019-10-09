# photo-storage

To run project on localhost just clone the repo and run build_and_run.sh script. It's essential to built jar (with mvn package)
before docker-compose build, because backend's Dockerfile just copy the .jar file to the docker image.

app is using ports: 80 and 8080. First one is easy to change with docker-compose. When it comes to the port of the backend 
(8080) - changes cannot be made.
