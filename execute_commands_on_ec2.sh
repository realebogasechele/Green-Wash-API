#!/usr/bin/env bash

kill -9 $(lsof -t -i:5000)
echo "Killed process running on port 5000"

java -jar ./target/greenwash-0.0.1-SNAPSHOT.jar
echo "Started server using java -jar command"