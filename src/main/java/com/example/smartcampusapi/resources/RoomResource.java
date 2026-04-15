package com.example.smartcampusapi.resources;

import com.example.smartcampusapi.exception.RoomNotEmptyException;
import com.example.smartcampusapi.model.Room;
import com.example.smartcampusapi.store.CampusDataStore;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/rooms")
public class RoomResource {

    private final CampusDataStore store = CampusDataStore.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRooms() {
        return Response.ok(store.getAllRooms().values()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoom(Room room) {
        if (room.getId() == null || room.getId().isBlank()) {
            return Response.status(400).entity("Room ID is required").build();
        }
        store.addRoom(room);
        return Response.status(201).entity(room).build();
    }

    @GET
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoom(@PathParam("roomId") String roomId) {
        Room room = store.getRoom(roomId);
        if (room == null) return Response.status(404).build();
        return Response.ok(room).build();
    }

    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        Room room = store.getRoom(roomId);
        if (room == null) return Response.status(404).build();

        if (!room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Room " + roomId + " still has sensors assigned");
        }
        store.removeRoom(roomId);
        return Response.noContent().build();
    }
}