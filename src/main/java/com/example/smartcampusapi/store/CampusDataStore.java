/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.smartcampus.store;

import com.example.smartcampus.model.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CampusDataStore {
    private static final ConcurrentHashMap<String, Room> rooms = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Sensor> sensors = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, List<SensorReading>> readings = new ConcurrentHashMap<>();

    static {
        // Seed data
        Room r1 = new Room("LIB-301", "Library Quiet Study", 50);
        Room r2 = new Room("CS-101", "Computer Lab", 30);
        rooms.put(r1.getId(), r1);
        rooms.put(r2.getId(), r2);

        Sensor s1 = new Sensor("TEMP-001", "Temperature", "ACTIVE", "LIB-301", 22.5);
        Sensor s2 = new Sensor("CO2-001", "CO2", "ACTIVE", "CS-101", 400.0);
        sensors.put(s1.getId(), s1);
        sensors.put(s2.getId(), s2);

        r1.getSensorIds().add(s1.getId());
        r2.getSensorIds().add(s2.getId());

        readings.put(s1.getId(), Collections.synchronizedList(new ArrayList<>()));
        readings.put(s2.getId(), Collections.synchronizedList(new ArrayList<>()));
    }

    public static Map<String, Room> getRooms() { return rooms; }
    public static Room getRoom(String id) { return rooms.get(id); }
    public static void addRoom(Room room) { rooms.put(room.getId(), room); }
    public static void deleteRoom(String id) { rooms.remove(id); }

    public static Map<String, Sensor> getSensors() { return sensors; }
    public static Sensor getSensor(String id) { return sensors.get(id); }
    public static boolean sensorExists(String id) { return sensors.containsKey(id); }
    public static void addSensor(Sensor sensor) {
        sensors.put(sensor.getId(), sensor);
        Room room = getRoom(sensor.getRoomId());
        if (room != null) room.getSensorIds().add(sensor.getId());
    }

    public static boolean hasActiveSensors(String roomId) {
        Room room = rooms.get(roomId);
        return room != null && !room.getSensorIds().isEmpty();
    }

    public static List<SensorReading> getReadings(String sensorId) {
        return readings.computeIfAbsent(sensorId, k -> Collections.synchronizedList(new ArrayList<>()));
    }
    public static void addReading(String sensorId, SensorReading reading) {
        getReadings(sensorId).add(reading);
    }
    public static void updateSensorValue(String sensorId, double value) {
        Sensor s = sensors.get(sensorId);
        if (s != null) s.setCurrentValue(value);
    }
}