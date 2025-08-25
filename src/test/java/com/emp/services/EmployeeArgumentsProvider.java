package com.emp.services;


import com.emp.dto.JwtRequest;
import com.emp.model.Employee;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class EmployeeArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(JwtRequest.builder().email("test@gmail.com").password("test").build()),
                Arguments.of(JwtRequest.builder().email("john@gmail.com").password("john").build())
        );
    }
}
