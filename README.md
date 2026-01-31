# Dynamic OG Image Generator

This project is a Dynamic Open Graph (OG) Image Generator built using Java Spring Boot.

## Overview
When a link is shared on social media platforms such as LinkedIn, Facebook, Twitter, or WhatsApp, OG meta tags are used to generate a preview.  
This project dynamically generates OG images at runtime instead of using static images.

## Features
- Dynamic OG image generation
- Supports LinkedIn, Facebook, Twitter, and WhatsApp
- Uses Java AWT for image rendering
- Mobile-safe center-aligned text
- Dockerized application
- Deployed on Render cloud

## Technologies Used
- Java
- Spring Boot
- Java AWT
- REST API
- Docker
- Render Cloud

## Live Demo
https://og-image-generator-7kgr.onrender.com/share

## API Endpoints
- `/share` – Returns HTML page with OG and Twitter meta tags
- `/og` – Generates and returns a dynamic PNG image

## How It Works
1. Social media reads the `/share` endpoint.
2. Meta tags inside `/share` point to the `/og` endpoint.
3. `/og` dynamically generates an image using Java AWT.
4. The generated image is displayed as the social media preview.

## Deployment
The application is containerized using Docker and deployed on the Render cloud platform.

