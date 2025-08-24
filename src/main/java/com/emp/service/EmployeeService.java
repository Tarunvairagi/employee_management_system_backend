package com.emp.service;

import com.emp.dto.EmployeeDTO;
import com.emp.exceptions.EmployeeAlreadyExistsException;
import com.emp.model.Employee;
import com.emp.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import  java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public EmployeeDTO registerEmployee(EmployeeDTO dto) {
        log.info("Registering employee: {}", dto.getEmail());

        if (employeeRepository.existsByEmail(dto.getEmail())) {
            throw new EmployeeAlreadyExistsException("Employee already exists with this email");
        }

        Employee employee = modelMapper.map(dto, Employee.class);
        employee.setPassword(passwordEncoder.encode(dto.getPassword()));  // hash password

        Employee saved = employeeRepository.save(employee);

        return modelMapper.map(saved, EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmplDetails() {
        try{
            List<Employee> employeeList = employeeRepository.findAll();
            //write this logic to get all emp object into db and convert to dto
            return employeeList.stream().map(obj -> modelMapper.map(obj,EmployeeDTO.class)).toList();
        }catch (Exception e){
            throw new RuntimeException("Employee object is not found!");
        }
    }

    public boolean isValidObjectId(String id) {
        return ObjectId.isValid(id);
    }

    public String deleteEmployeeDetailById(String empId) {
        try{
            // Validate ID format
            if (!ObjectId.isValid(empId)) {
                throw new IllegalArgumentException("Invalid ID format: " + empId);
            }
            employeeRepository.deleteById(empId);
            return "Employee object is deleted successfully by id : " + empId;
        }catch (Exception e){throw new RuntimeException("Employee object is not found!");}
    }

    public EmployeeDTO updateEmployeeFields(String empId, Map<String, Object> updates) {
        if (!ObjectId.isValid(empId)) {
            throw new IllegalArgumentException("Invalid ID format: " + empId);
        }

        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "name": employee.setName((String) value); break;
                case "department": employee.setDepartment((String) value); break;
                case "salary": employee.setSalary(Double.valueOf(value.toString())); break;
                case "mobile": employee.setMobile((String) value); break;
                case "city": employee.setCity((String) value); break;
                case "country": employee.setCountry((String) value); break;
                case "state": employee.setState((String) value); break;
                case "status": employee.setStatus((String) value); break;
                default: throw new IllegalArgumentException("Field " + key + " is not updatable");
            }
        });

        Employee saved = employeeRepository.save(employee);
        return modelMapper.map(saved, EmployeeDTO.class);
    }

}
