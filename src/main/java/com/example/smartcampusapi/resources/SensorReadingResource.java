/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.smartcampusapi.resources;

/**
 *
 * @author kulanitennakoon
 */
package com.example.smartcampus.resource;

import com.example.smartcampus.exception.SensorUnavailableException;
import com.example.smartcampus.model.Sensor;
import com.example.smartcampus.model.SensorReading;
import com.example.smartcampus.store.CampusDataStore;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

public class SensorReadingResource {
    private final String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistory() {
        List<SensorReading> history = CampusDataStore.getReadings(sensorId);
        return Response.ok(history).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addReading(SensorReading reading) {
        Sensor sensor = CampusDataStore.getSensor(sensorId);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if ("MAINTENANCE".equals(sensor.getStatus())) {
            throw new SensorUnavailableException("Sensor " + sensorId + " is in maintenance and cannot accept new readings.");
        }
        if (reading.getId() == null) reading.setId(UUID.randomUUID().toString());
        if (reading.getTimestamp() == 0) reading.setTimestamp(System.currentTimeMillis());

        CampusDataStore.addReading(sensorId, reading);
        CampusDataStore.updateSensorValue(sensorId, reading.getValue());
        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}