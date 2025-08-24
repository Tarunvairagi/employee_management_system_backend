package com.emp.service;

import com.emp.dto.EmployeeDTO;
import com.emp.exceptions.EmployeeAlreadyExistsException;
import com.emp.model.Employee;
import com.emp.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
