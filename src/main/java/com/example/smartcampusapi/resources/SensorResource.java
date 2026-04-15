/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kulanitennakoon
 */
package com.example.smartcampusapi.resources;

import com.example.smartcampus.exception.LinkedResourceNotFoundException;
import com.example.smartcampus.model.Sensor;
import com.example.smartcampus.store.CampusDataStore;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("sensors")
public class SensorResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSensors(@QueryParam("type") String type) {
        List<Sensor> list = new ArrayList<>(CampusDataStore.getSensors().values());
        if (type == null || type.isEmpty()) {
            return Response.ok(list).build();
        }
        List<Sensor> filtered = list.stream()
                .filter(s -> type.equalsIgnoreCase(s.getType()))
                .collect(Collectors.toList());
        return Response.ok(filtered).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensor(Sensor sensor) {
        if (!CampusDataStore.getRooms().containsKey(sensor.getRoomId())) {
            throw new LinkedResourceNotFoundException("Room with id " + sensor.getRoomId() + " does not exist.");
        }
        if (sensor.getId() == null || sensor.getId().isEmpty()) {
            sensor.setId("SENS-" + System.currentTimeMillis());
        }
        if (sensor.getStatus() == null) sensor.setStatus("ACTIVE");
        CampusDataStore.addSensor(sensor);
        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }

    @Path("{sensorId}/readings")
    public SensorReadingResource getReadingsResource(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }
}
