/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kulanitennakoon
 */
package com.example.smartcampusapi.exception.mapper;

import com.example.smartcampus.exception.RoomNotEmptyException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {
    @Override
    public Response toResponse(RoomNotEmptyException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "RoomNotEmpty");
        error.put("message", ex.getMessage());
        return Response.status(409).entity(error).type(MediaType.APPLICATION_JSON).build();
    }
}
