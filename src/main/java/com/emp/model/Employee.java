package com.emp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    private String id;

    private String name;
    private String email;
    private String password;
    private String department;
    private Double salary;
    private String mobile;
    private String city;
    private String country;
    private String state;
    private String gender;
    private String status;
}
