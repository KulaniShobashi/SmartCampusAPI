package com.example.smartcampusapi.store;

import com.example.smartcampusapi.model.Room;
import com.example.smartcampusapi.model.Sensor;
import com.example.smartcampusapi.model.SensorReading;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CampusDataStore {
    private static CampusDataStore instance;
    private final Map<String, Room> rooms = new ConcurrentHashMap<>();
    private final Map<String, Sensor> sensors = new ConcurrentHashMap<>();
    private final Map<String, List<SensorReading>> readings = new ConcurrentHashMap<>();

    private CampusDataStore() {}

    public static synchronized CampusDataStore getInstance() {
        if (instance == null) instance = new CampusDataStore();
        return instance;
    }

    // Room methods
    public Map<String, Room> getAllRooms() { return rooms; }
    public Room getRoom(String id) { return rooms.get(id); }
    public void addRoom(Room room) { rooms.put(room.getId(), room); }
    public void removeRoom(String id) { rooms.remove(id); }

    // Sensor methods
    public Map<String, Sensor> getAllSensors() { return sensors; }
    public Sensor getSensor(String id) { return sensors.get(id); }
    public void addSensor(Sensor sensor) { sensors.put(sensor.getId(), sensor); }
    public void updateSensor(Sensor sensor) { sensors.put(sensor.getId(), sensor); }

    // Readings
    public List<SensorReading> getReadings(String sensorId) {
        return readings.computeIfAbsent(sensorId, k -> new ArrayList<>());
    }
    public void addReading(String sensorId, SensorReading reading) {
        getReadings(sensorId).add(reading);
    }
}