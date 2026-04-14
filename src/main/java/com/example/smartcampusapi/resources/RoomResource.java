/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.smartcampus.resource;

import com.example.smartcampus.exception.RoomNotEmptyException;
import com.example.smartcampus.model.Room;
import com.example.smartcampus.store.CampusDataStore;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("rooms")
public class RoomResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRooms() {
        return Response.ok(new ArrayList<>(CampusDataStore.getRooms().values())).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoom(Room room) {
        if (room.getId() == null || room.getId().isEmpty()) {
            room.setId("ROOM-" + System.currentTimeMillis());
        }
        CampusDataStore.addRoom(room);
        return Response.status(Response.Status.CREATED).entity(room).build();
    }

    @GET
    @Path("{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoom(@PathParam("roomId") String roomId) {
        Room room = CampusDataStore.getRoom(roomId);
        if (room == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(room).build();
    }

    @DELETE
    @Path("{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        if (!CampusDataStore.getRooms().containsKey(roomId)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (CampusDataStore.hasActiveSensors(roomId)) {
            throw new RoomNotEmptyException("Room " + roomId + " still has sensors assigned.");
        }
        CampusDataStore.deleteRoom(roomId);
        return Response.noContent().build();
    }
}