package com.example.smartcampusapi.resources;

import com.example.smartcampusapi.exception.SensorUnavailableException;
import com.example.smartcampusapi.model.Sensor;
import com.example.smartcampusapi.model.SensorReading;
import com.example.smartcampusapi.store.CampusDataStore;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

public class SensorReadingResource {

    private final String sensorId;
    private final CampusDataStore store = CampusDataStore.getInstance();

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReadings() {
        List<SensorReading> list = store.getReadings(sensorId);
        return Response.ok(list).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(SensorReading reading) {
        Sensor sensor = store.getSensor(sensorId);
        if (sensor == null) return Response.status(404).build();
        if ("MAINTENANCE".equals(sensor.getStatus())) {
            throw new SensorUnavailableException("Sensor is in maintenance mode");
        }

        reading.setId(UUID.randomUUID().toString());
        reading.setTimestamp(System.currentTimeMillis());
        store.addReading(sensorId, reading);
        sensor.setCurrentValue(reading.getValue()); // update current value
        store.updateSensor(sensor);

        return Response.status(201).entity(reading).build();
    }
}