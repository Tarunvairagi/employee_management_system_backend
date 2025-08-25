package com.emp.services;

import com.emp.controller.EmployeeController;
import com.emp.dto.JwtRequest;
import com.emp.model.Employee;
import com.emp.repository.EmployeeRepository;
import com.emp.service.CustomUserDetailsService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //without this Annotation I was given that error : because "this.employeeRepository" is null
public class EmployeeServiceTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private EmployeeController employeeController;



    //Mockito with JUnit 5 (Jupiter)
    @Disabled //I don't need to run this test case
    @Test
    public void testAdd(){
        assertEquals(4,2+2);
    }

    @Test // this is 1 Individual test hole method
    public void testFindByEmployeeName(){
        //Check Employee is not null
//        assertNotNull(employeeRepository.findByEmail("test1@gmail.com")); //pass
//        assertTrue(5 > 4);

        Optional<Employee> employee = employeeRepository.findByEmail("test1@gmail.com");
//        assertTrue(!employee.get().getName().isEmpty()); //fail
        assertNotNull(employee); //fail
//        System.out.println(byEmail.get());
    }

    //Test driven development -> to check different input sending to the same method and check is it working or not
    //dynamic sending multiple values
//    @CsvSource
//    @CsvFileSource -> taking the source like a and b
//    @CsvFileSource

    @ParameterizedTest
    @CsvSource({
            //"a,b,result"
            "3,2,5", //test1
            "4,2,6", //test2
            "1,2,5", //test2
    })
    public void testValues(int a, int b, int result){
        assertEquals(result,a + b);
    }

    @ParameterizedTest
//    @EnumSource
    @ValueSource(strings = {
            "jane.smith@example.com",
            "test1@gmail.com",
            "test@gmail.com",
            "test2@gmail.com",
    })
    public void testEmployeeDetails(String email){
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        assertNotNull(employee,"User details : " + email);
    }


    @ParameterizedTest
//    @EnumSource
    @ArgumentsSource(EmployeeArgumentsProvider.class)
    public void testEmployee(JwtRequest employee){
        ResponseEntity<?> response = employeeController.login(employee);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

//    @After()
//    @Before()
//    @AfterAll
//    @BeforeAll
}
