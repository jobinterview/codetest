#!/bin/bash
echo -n "Enter the ID of the Customer: "
read ID

curl -v -X DELETE -H "Content-Type: application/json" http://localhost:8080/customer/$ID


