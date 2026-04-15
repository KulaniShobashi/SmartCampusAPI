/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author kulanitennakoon
 */
// RoomNotEmptyException.java
package com.example.smartcampus.exception;
public class RoomNotEmptyException extends RuntimeException {
    public RoomNotEmptyException(String message) { super(message); }
}

// LinkedResourceNotFoundException.java
package com.example.smartcampus.exception;
public class LinkedResourceNotFoundException extends RuntimeException {
    public LinkedResourceNotFoundException(String message) { super(message); }
}

// SensorUnavailableException.java
package com.example.smartcampus.exception;
public class SensorUnavailableException extends RuntimeException {
    public SensorUnavailableException(String message) { super(message); }
}
