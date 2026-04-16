package com.example.smartcampusapi.exception.mapper;

import com.example.smartcampusapi.exception.SensorUnavailableException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException> {

    @Override
    public Response toResponse(SensorUnavailableException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "SensorUnavailable");
        error.put("message", ex.getMessage());

        return Response.status(403)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}