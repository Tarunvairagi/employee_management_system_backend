package com.emp.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "refresh_tokens")
public class RefreshToken {

    @Id
    private String id;   // In Mongo, IDs are usually String (ObjectId)

    private String token;
    private Instant expiryDate;

    @DBRef
    private Employee employeeDetails;  // Reference to another collection

    // getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Employee getEmployeeDetails() {
        return employeeDetails;
    }

    public void setEmployeeDetails(Employee employeeDetails) {
        this.employeeDetails = employeeDetails;
    }
}

