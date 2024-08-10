#!/usr/bin/env bash

docker rm -f $(docker ps -a -q --filter "name=notice-board-*")

docker compose down

docker compose up -d
