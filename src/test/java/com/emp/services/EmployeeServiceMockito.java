package com.emp.services;

import com.emp.dto.JwtRequest;
import com.emp.model.Employee;
import com.emp.repository.EmployeeRepository;
import com.emp.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

//@SpringBootTest
public class EmployeeServiceMockito {

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

//    @Autowired
    @InjectMocks //automate using mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
//    @MockitoBean
    private EmployeeRepository employeeRepository;

    @Test
    public void loadEmployeeByUserNameTest() {
        // Mock Employee entity returned from repository
        Employee mockEmployee = Employee.builder()
                .email("test@gmail.com")
                .password("435fedgeweged") // hashed password in real scenario
                .name("Test User")
                .build();

        when(employeeRepository.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(mockEmployee)); // return Optional<Employee>

        // Call service
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("test@gmail.com");

        // Verify
        assertNotNull(userDetails);
        assertEquals("test@gmail.com", userDetails.getUsername());
        assertEquals("435fedgeweged", userDetails.getPassword());
    }
}
