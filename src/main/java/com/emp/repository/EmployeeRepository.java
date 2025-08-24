package com.emp.repository;

import com.emp.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<Employee,String> {
    boolean existsByEmail(String email);
    Optional<Employee> findByEmail(String email);
}
