# Smart Campus Sensor & Room Management API

**Module:** 5COSC022W - Client-Server Architectures  
**Student:** Kulani Tennakoon  
**Date:** 24 th April 2026  

**GitHub Repository:** (https://github.com/KulaniShobashi/SmartCampusAPI.git)

---

## 1. Project Overview

This is a fully functional RESTful API built with **JAX-RS (Jakarta RESTful Web Services)** for the "Smart Campus" initiative. The API manages Rooms, Sensors, and historical Sensor Readings with proper resource nesting, business logic validation, custom error handling, and request/response logging.

**Base URL:** `http://localhost:8080/api/v1`

---

## 2. How to Build and Run

```bash
cd Smart_Campus_API
mvn clean package

## 3. Answers to Lecturer Questions
# Part 1: Service Architecture & Setup
Q: Explain the default lifecycle of a JAX-RS Resource class. Is it a singleton or per-request?
A: In JAX-RS, Resource classes (like RoomResource) are per-request by default. A new instance is created for every incoming HTTP request and destroyed after the response is sent. Because of this, I used a Singleton Pattern for the CampusDataStore class. This ensures that even though the resources are recreated, the data is stored in a single, persistent location. I used ConcurrentHashMap to ensure thread safety and prevent data loss or race conditions when multiple requests access the data at once.
Q: Why is "Hypermedia" (HATEOAS) considered a hallmark of advanced RESTful design?
A: Providing links (as seen in my DiscoveryResource) makes the API self-navigating. Instead of the developer needing to hard-code every URL, they can discover endpoints dynamically. This makes the API more flexible; if the URL structure changes, the client application doesn't break as long as it follows the discovery links.
