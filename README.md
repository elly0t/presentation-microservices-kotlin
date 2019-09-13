# presentation-microservices-kotlin
Presentation code and material for micro-services with kotlin

[![master](https://img.shields.io/badge/travis-master-blue.svg)][travis-url][![TravisCI][travis-master-badge]][travis-url]

## Introduction

Build microservices wth Kotlin + Spring Boot

Presented at GDG Kotlin/Everywhere Hong Kong : A Day of Kotlin, 31st Aug 2019

Slides can be found [here](https://drive.google.com/file/d/1EkioxNMzPW2sfPd3s8Cn7GyNuC7_u4-N/view?usp=sharing).

Three backend services are created in this project, `auth`, `app` and `product` respectively. A microservice-based architecture can be achieved locally with docker-compose. We have also deployed them on Oursky's k8s cluster.

MinIO is using in this project to store all the products images.

## Get Started
##### Generate secret
```
cd backend
make secret
```
##### Start servers
```
cd backend
docker-compose -p demo up
```
##### Testing api call
```
curl http://localhost:8080/auth/ping
curl http://localhost:8080/app/ping
curl http://localhost:8080/product/ping
curl http://localhost:8080/product/     //Return all the products in database.
curl -X POST http://localhost:8080/auth/signup -H "Content-Type: application/json" -d '{"user": "test", "pass": "1234"}'
curl -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" -d '{"user": "test", "pass": "1234"}'

# NOTE: replace {id} with product id
curl -X DELETE http://localhost:8080/product/{id}

# NOTE: replace with access token from signup/login response
curl -vvv http://localhost:8080/app/ping2 -H "Authorization: Bearer ACCESS_TOKEN"
```

<!-- Markdown link & img dfn's -->
[travis-url]: https://travis-ci.org/oursky/presentation-microservices-kotlin
[travis-master-badge]: https://travis-ci.org/oursky/presentation-microservices-kotlin.svg?branch=master
