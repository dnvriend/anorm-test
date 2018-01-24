#!/bin/bash
export VM_HOST="${VM_HOST:-boot2docker}"

# Wait for a certain service to become available
# Usage: wait 3306 Mysql
wait() {
while true; do
  if ! nc -z $VM_HOST $1
  then
    echo "$2 not available, retrying..."
    sleep 1
  else
    echo "$2 is available"
    break;
  fi
done;
}

docker-compose -f docker-compose.yml kill
docker-compose -f docker-compose.yml rm -f
docker-compose -f docker-compose.yml up -d
wait 5432 Postgres