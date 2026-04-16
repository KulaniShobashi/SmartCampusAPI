package com.example.smartcampusapi.resources;

import com.example.smartcampusapi.exception.LinkedResourceNotFoundException;
import com.example.smartcampusapi.model.Sensor;
import com.example.smartcampusapi.store.CampusDataStore;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/sensors")
public class SensorResource {

    private final CampusDataStore store = CampusDataStore.getInstance();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensor(Sensor sensor) {
        if (store.getRoom(sensor.getRoomId()) == null) {
            throw new LinkedResourceNotFoundException("Room " + sensor.getRoomId() + " does not exist");
        }
        store.addSensor(sensor);
        // Add sensorId to room
        store.getRoom(sensor.getRoomId()).addSensorId(sensor.getId());
        return Response.status(201).entity(sensor).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSensors(@QueryParam("type") String type) {
        var sensors = store.getAllSensors().values();
        if (type != null && !type.isBlank()) {
            sensors = sensors.stream()
                    .filter(s -> s.getType().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
        }
        return Response.ok(sensors).build();
    }

    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadings(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }
}