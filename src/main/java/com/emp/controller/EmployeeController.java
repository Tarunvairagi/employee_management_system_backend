package com.emp.controller;

import com.emp.dto.EmployeeDTO;
import com.emp.dto.JwtRequest;
import com.emp.dto.JwtResponse;
import com.emp.dto.RefreshTokenDto;
import com.emp.model.RefreshToken;
import com.emp.service.CustomUserDetailsService;
import com.emp.service.EmployeeService;
import com.emp.service.JWTService;
import com.emp.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JWTService jwtService;
    @Autowired
    private final CustomUserDetailsService userDetailsService;
    @Autowired
    private final EmployeeService employeeService;
    @Autowired
    private RefreshTokenService refreshTokenService;

    //http://localhost:8080/api/employees/registration
    @PostMapping("/registration")
    public ResponseEntity<EmployeeDTO> employeeRegistration(
//            @Valid
            @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO savedEmployee = employeeService.registerEmployee(employeeDTO);
        return ResponseEntity.ok(savedEmployee);
    }

    //http://localhost:8080/api/employees/login
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).build();
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(userDetails.getUsername());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());
        return ResponseEntity.ok(new JwtResponse("JWT Token",token,refreshToken.getToken()));
    }

    //http://localhost:8080/api/employees/refreshToken
    @PostMapping("/refreshToken")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenDto token) {
        return refreshTokenService.findByToken(token.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getEmployeeDetails)
                .map(employeeDetails -> {
                    String accessToken = jwtService.generateToken(employeeDetails.getEmail());
                    return ResponseEntity.ok(new JwtResponse("JWT Token", accessToken, token.getToken()));
                }).orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }

    //http://localhost:8080/api/employees/getAllEmployees
    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployeesDetails(){
        try{
            List<EmployeeDTO> allEmplDetails = employeeService.getAllEmplDetails();
            return ResponseEntity.ok(allEmplDetails);
        }catch (Exception e){
            return ResponseEntity.status(404).build();
//            throw new RuntimeException("Employee object is not found!");
        }
    }

    //delete the object
    //http://localhost:8080/api/employees/deleteEmployeeDetail/{empId}
    @DeleteMapping("/deleteEmployeeDetail/{empId}")
    public ResponseEntity<String> deleteEmployeeDetail(
            @PathVariable String empId
    ){
        try{
            if(empId != null){
                String deleteStatus = employeeService.deleteEmployeeDetailById(empId);
                return ResponseEntity.ok(deleteStatus);
            }
            return ResponseEntity.status(404).body("Employee not found with given ID");
        }catch (Exception e){
            return ResponseEntity.status(404).body("Employee not found with given ID");

        }
    }

    //update the filed using patch
    //http://localhost:8080/api/employees/employeeUpdate/{id}
    @PatchMapping("/employeeUpdate/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable String id,
            @RequestBody Map<String, Object> updates) {
        try{
            EmployeeDTO updatedEmployee = employeeService.updateEmployeeFields(id, updates);
            return ResponseEntity.ok(updatedEmployee);
        }catch (Exception e){
            throw new RuntimeException("Employee not found with given ID");
        }
    }

    //http://localhost:8080/api/employees/message
    @GetMapping("/message")
    public String getSecretMessage() {
        return "🎉 Welcome! This is a secured message only for authenticated users.";
    }
}
