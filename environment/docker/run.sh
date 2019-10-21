#!/bin/bash

echo 'starting MySQL...'
docker start mysql

echo 'starting Redis...'
docker start redis

echo 'starting RabbitMQ...'
docker start rabbitmq

echo 'starting MinIO...'
docker start minio

echo 'starting Zipkin...'
docker start zipkin
