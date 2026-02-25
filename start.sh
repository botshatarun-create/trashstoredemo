#!/bin/bash
# Self-healing startup for Linux/Codespaces
tr -d '\r' < mvnw > mvnw.fixed && mv mvnw.fixed mvnw
chmod +x mvnw
./mvnw spring-boot:run
