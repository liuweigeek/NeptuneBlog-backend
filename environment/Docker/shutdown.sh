#!/bin/bash

echo 'stoping all containers for NeptuneBlog'

echo 'stoping MySQL...'
docker stop mysql

echo 'stoping Redis...'
docker stop redis

echo 'stoping RabbitMQ...'
docker stop rabbitmq

echo 'stoping MinIO...'
docker stop minio

echo 'stoping Zipkin...'
docker stop zipkin