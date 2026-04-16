package com.example.smartcampusapi;

import com.example.smartcampusapi.exception.mapper.*;
import com.example.smartcampusapi.filter.LoggingFilter;
import com.example.smartcampusapi.resources.*;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api/v1")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();

        // Resources
        classes.add(DiscoveryResource.class);
        classes.add(RoomResource.class);
        classes.add(SensorResource.class);
        classes.add(SensorReadingResource.class);

        // Exception Mappers
        classes.add(RoomNotEmptyExceptionMapper.class);
        classes.add(LinkedResourceNotFoundExceptionMapper.class);
        classes.add(SensorUnavailableExceptionMapper.class);
        classes.add(GenericExceptionMapper.class);

        // Filter for logging (required for 70+ marks)
        classes.add(LoggingFilter.class);

        return classes;
    }
}