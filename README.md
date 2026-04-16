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
```

---


## 3. Answers to Lecturer Questions

### Part 1: Service Architecture & Setup

Q: Explain the default lifecycle of a JAX-RS Resource class. Is it a singleton or per-request?

A: In JAX-RS, Resource classes (like RoomResource) are per-request by default. A new instance is created for every incoming HTTP request and destroyed after the response is sent. Because of this, I used a Singleton Pattern for the CampusDataStore class. This ensures that even though the resources are recreated, the data is stored in a single, persistent location. I used ConcurrentHashMap to ensure thread safety and prevent data loss or race conditions when multiple requests access the data at once.

Q: Why is "Hypermedia" (HATEOAS) considered a hallmark of advanced RESTful design?

A: Providing links (as seen in my DiscoveryResource) makes the API self-navigating. Instead of the developer needing to hard-code every URL, they can discover endpoints dynamically. This makes the API more flexible; if the URL structure changes, the client application doesn't break as long as it follows the discovery links.

### Part 2: Room Management

Q: What are the implications of returning only IDs versus full room objects?

A: Returning only IDs saves network bandwidth and makes the response smaller. However, returning full objects (as my code does) is better for the client because it avoids the "N+1 problem"—the client doesn't have to make many extra requests to get the details for every ID it received.

Q: Is the DELETE operation idempotent in your implementation?

A: Yes. If a client deletes a room, it is removed (Status 204). If they send the same request again, the room is already gone (Status 404). Because the final state of the server (the room being absent) is the same regardless of how many times the request is sent, it is idempotent.

### Part 3: Sensor Operations & Linking

Q: Explain the technical consequences if a client sends data in a different format (like XML).

A: Because I used the @Consumes(MediaType.APPLICATION_JSON) annotation, JAX-RS will automatically reject any request that is not JSON. The client will receive an HTTP 415 Unsupported Media Type error. This protects the logic from trying to parse incompatible data formats.

Q: Why is the query parameter approach superior for filtering collections?

A: Using @QueryParam (e.g., ?type=CO2) is standard for filtering because the "Resource" is still the sensor collection. Using a path parameter (e.g., /type/CO2) incorrectly implies that "CO2" is a specific sub-resource rather than just a filter criteria. Query parameters allow for easier combining of filters (like ?type=CO2&status=ACTIVE).

### Part 4: Deep Nesting with Sub-Resources

Q: Discuss the architectural benefits of the Sub-Resource Locator pattern.

A: It promotes Separation of Concerns. Instead of having one massive "RoomManager" class, I delegated reading logic to SensorReadingResource. This makes the code much easier to maintain and read. It also creates a logical hierarchy that matches the real world (Campus -> Room -> Sensor -> Reading).

### Part 5: Error Handling & Logging

Q: Why is HTTP 422 more accurate than 404 for missing references in JSON?

A: An HTTP 404 means the "URL" wasn't found. An HTTP 422 (Unprocessable Entity) means the URL was correct and the JSON was valid, but the data inside (the Room ID) was logically incorrect. It provides better "semantic" feedback to the developer.

Q: What are the cybersecurity risks of exposing internal Java stack traces?

A: Stack traces reveal the internal structure of the code, including class names, library versions (like Jersey version), and sometimes file paths on the server. An attacker can use this info to find specific "known vulnerabilities" (CVEs) for those library versions to hack the system. My GenericExceptionMapper prevents this by hiding the trace.

Q: Why use JAX-RS filters for logging instead of manual Logger statements?

A: Filters are a "cross-cutting concern." By using a filter, I ensure that 100% of all requests are logged in one central place. If I did it manually, I might forget a method, or I would have to update 20 different files just to change the log format.
