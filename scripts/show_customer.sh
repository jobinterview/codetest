#!/bin/bash
echo -n "Enter the ID of the Customer: "
read ID

curl -X GET -H "Content-Type: application/json" http://localhost:8080/customer/$ID | python -m json.tool 


