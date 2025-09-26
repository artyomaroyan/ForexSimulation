# Makefile for Java Microservices with Docker Compose
.PHONY: help build up down restart logs clean test package dev prod status health

# Default target
.DEFAULT_GOAL := help

# Variables
COMPOSE_FILE := docker-compose.yml
SERVICES := customer-service order-service currency-service
DBS := db-customer db-order db-currency

# Colors for output
GREEN := \033[32m
YELLOW := \033[33m
RED := \033[31m
BLUE := \033[34m
NC := \033[0m # No Color

## Help
help: ## Show this help message
	@echo "$(BLUE)Available commands:$(NC)"
	@awk 'BEGIN {FS = ":.*?## "} /^[a-zA-Z_-]+:.*?## / {printf "  $(GREEN)%-15s$(NC) %s\n", $$1, $$2}' $(MAKEFILE_LIST)

## Development Commands
dev: ## Start all services in development mode
	@echo "$(YELLOW)Starting services in development mode...$(NC)"
	docker-compose up --build

up: ## Start all services in background
	@echo "$(YELLOW)Starting all services...$(NC)"
	docker-compose up -d --build
	@make status

down: ## Stop all services and remove containers
	@echo "$(YELLOW)Stopping all services...$(NC)"
	docker-compose down

stop: ## Stop all services without removing containers
	@echo "$(YELLOW)Stopping all services...$(NC)"
	docker-compose stop

start: ## Start existing services
	@echo "$(YELLOW)Starting existing services...$(NC)"
	docker-compose start

restart: ## Restart all services
	@echo "$(YELLOW)Restarting all services...$(NC)"
	docker-compose restart

## Build Commands
build: ## Build all services
	@echo "$(YELLOW)Building all services...$(NC)"
	docker-compose build

build-no-cache: ## Build all services without cache
	@echo "$(YELLOW)Building all services without cache...$(NC)"
	docker-compose build --no-cache

rebuild: ## Stop, rebuild, and start all services
	@make down
	@make build-no-cache
	@make up

## Individual Service Commands
up-customer: ## Start only customer service and its dependencies
	docker-compose up -d db-customer customer-service
