#!/bin/bash
while read LINE 
do 
    curl -v -X POST -H "Content-Type: application/json" -d "$LINE" http://localhost:8080/customer
done < ../src/test/resources/mock.json


