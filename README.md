# Telegram bot для отслеживания ссылок

[![botCI](https://github.com/PorterNight/UrlParsers/actions/workflows/bot.yml/badge.svg)](https://github.com/PorterNight/UrlParsers/actions/workflows/bot.yml)
[![scrapperCI](https://github.com/PorterNight/UrlParsers/actions/workflows/scrapper.yml/badge.svg)](https://github.com/PorterNight/UrlParsers/actions/workflows/scrapper.yml)

## General info
Пользователь регистрирует ссылки с сайтов Stackoverflow и Github используя 
команду /track. После чего telegram bot периодически отслеживает ссылки
на предмет обновлений. При наличии обновлений присылается уведомление 
в чат.

Проект состоит из трех модулей: Scrapper (http-клиент и база данных), 
Bot (взаимодействие с телеграм бот), Link-Parser (парсинг ссылок), 
обмен данными между модулями происходит через HTTP запросы и Rabbit MQ.

### Requirements
* Maven
* Docker

### Technologies
* Spring-Boot
* OpenAPI, Swagger
* Liquibase
* PostgreSQL, Jpa, Jdbc, Jooq
* Docker
* Mockito, Testcontainers
* RabbitMQ
* Metrics (Grafana, Prometheus)
* CI/CD (Github Actions)
* Checkstyle
