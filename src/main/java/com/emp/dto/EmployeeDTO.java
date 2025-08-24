package com.emp.dto;

import lombok.*;

//import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private String id;

//    @NotBlank(message = "Name is required")
    private String name;

//    @Email(message = "Email should be valid")
//    @NotBlank(message = "Email is required")
    private String email;

//    @NotBlank(message = "Password is required")
//    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

//    @NotBlank(message = "Department is required")
    private String department;

//    @NotNull(message = "Salary is required")
//    @Positive(message = "Salary must be a positive number")
    private Double salary;

//    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String mobile;

//    @NotBlank(message = "Address is required")
    private String city;
    private String country;
    private String state;

//    @Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other")
    private String gender;

//    @Pattern(regexp = "Active|Inactive", message = "Status must be Active or Inactive")
    private String status;
}

