Exchange Project

Overview

The Exchange Project is a real-time trading and analytics platform designed to manage market data, execute trades, and monitor price fluctuations effectively. It integrates technologies such as PostgreSQL (with TimescaleDB), Redis, and modern backend frameworks to ensure high performance and scalability.

Features

Real-Time Data Handling: Process market data in real-time using Redis as a messaging broker.

Historical Data Management: Utilize TimescaleDB for efficient storage and querying of time-series data.

Trade Execution: Enable seamless trade execution with secure user authentication.

Analytics and Aggregations: Generate candlestick charts and other trading metrics using materialized views.

Tech Stack

Backend: Java Spring Boot / Node.js

Database: PostgreSQL with TimescaleDB extension

Cache & Messaging: Redis

Client: React.js

Containerization: Docker

Prerequisites

Docker and Docker Compose

Java 17 (if using Spring Boot backend)

PostgreSQL (with TimescaleDB enabled)

Redis
