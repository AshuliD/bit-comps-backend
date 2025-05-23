package com.bit.backend.controllers; // Updated package

import com.bit.backend.dtos.EmployeeDTO; // Updated import
import com.bit.backend.exceptions.ResourceNotFoundException; // Updated import
import com.bit.backend.services.EmployeeService; // Updated import
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class) // This will now point to the relocated controller
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService; // Interface, points to new package

    @Autowired
    private ObjectMapper objectMapper;

    private EmployeeDTO employeeDTO;
    private EmployeeDTO invalidEmployeeDTO;

    @BeforeEach
    void setUp() {
        employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setName("John Doe");
        // employeeDTO.setEmail("john.doe@example.com"); // Removed email
        employeeDTO.setAge(30);
        employeeDTO.setBirthDate(LocalDate.of(1990, 1, 1));
        employeeDTO.setSalary(new BigDecimal("50000.00"));
        employeeDTO.setPhoneNumber("123-456-7890");

        invalidEmployeeDTO = new EmployeeDTO();
        invalidEmployeeDTO.setName(""); // Invalid: name is empty
        // invalidEmployeeDTO.setEmail("john.doe@example.com"); // Removed email
        invalidEmployeeDTO.setAge(30); // Valid age
        invalidEmployeeDTO.setBirthDate(LocalDate.of(1990, 1, 1)); // Valid birthDate
        invalidEmployeeDTO.setSalary(new BigDecimal("50000.00")); // Valid salary
        invalidEmployeeDTO.setPhoneNumber("123-456-7890"); // Valid phone number
    }

    @Test
    void createEmployee_validInput_returnsCreated() throws Exception {
        given(employeeService.createEmployee(any(EmployeeDTO.class))).willReturn(employeeDTO);

        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTO)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(employeeDTO.getName())))
                // .andExpect(jsonPath("$.email", is(employeeDTO.getEmail()))) // Removed email assertion
                .andExpect(jsonPath("$.age", is(employeeDTO.getAge())))
                .andExpect(jsonPath("$.birthDate", is(employeeDTO.getBirthDate().format(DateTimeFormatter.ISO_DATE))))
                .andExpect(jsonPath("$.salary", is(employeeDTO.getSalary().doubleValue()))) // Compare as double for BigDecimal
                .andExpect(jsonPath("$.phoneNumber", is(employeeDTO.getPhoneNumber())));
    }

    @Test
    void createEmployee_invalidInput_returnsBadRequest() throws Exception {
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidEmployeeDTO)));

        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void getEmployeeById_exists_returnsOk() throws Exception {
        given(employeeService.getEmployeeById(1L)).willReturn(employeeDTO);

        ResultActions response = mockMvc.perform(get("/api/employees/{id}", 1L));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(employeeDTO.getName())))
                // .andExpect(jsonPath("$.email", is(employeeDTO.getEmail()))) // Removed email assertion
                .andExpect(jsonPath("$.age", is(employeeDTO.getAge())));
    }

    @Test
    void getEmployeeById_notFound_returnsNotFound() throws Exception {
        given(employeeService.getEmployeeById(1L)).willThrow(new ResourceNotFoundException("Employee", "id", 1L));

        ResultActions response = mockMvc.perform(get("/api/employees/{id}", 1L));

        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void getAllEmployees_returnsOk() throws Exception {
        List<EmployeeDTO> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(employeeDTO);
        
        EmployeeDTO employeeDTO2 = new EmployeeDTO();
        employeeDTO2.setId(2L);
        employeeDTO2.setName("Jane Doe");
        // employeeDTO2.setEmail("jane.doe@example.com"); // Removed email
        employeeDTO2.setAge(28);
        employeeDTO2.setBirthDate(LocalDate.of(1992, 2, 2));
        employeeDTO2.setSalary(new BigDecimal("60000.00"));
        employeeDTO2.setPhoneNumber("098-765-4321");
        listOfEmployees.add(employeeDTO2);

        given(employeeService.getAllEmployees()).willReturn(listOfEmployees);

        ResultActions response = mockMvc.perform(get("/api/employees"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(listOfEmployees.size())))
                // .andExpect(jsonPath("$[0].email", is(employeeDTO.getEmail()))) // Removed email assertion
                .andExpect(jsonPath("$[0].name", is(employeeDTO.getName())))
                .andExpect(jsonPath("$[1].name", is(employeeDTO2.getName())));
    }

    @Test
    void getAllEmployees_empty_returnsOk() throws Exception {
        given(employeeService.getAllEmployees()).willReturn(Collections.emptyList());

        ResultActions response = mockMvc.perform(get("/api/employees"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(0)));
    }


    @Test
    void updateEmployee_validInput_returnsOk() throws Exception {
        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO();
        updatedEmployeeDTO.setId(1L);
        updatedEmployeeDTO.setName("Johnathan Doer");
        // updatedEmployeeDTO.setEmail("john.doer@example.com"); // Removed email
        updatedEmployeeDTO.setAge(32);
        updatedEmployeeDTO.setBirthDate(LocalDate.of(1988, 5, 5));
        updatedEmployeeDTO.setSalary(new BigDecimal("65000.00"));
        updatedEmployeeDTO.setPhoneNumber("111-222-3333");

        given(employeeService.updateEmployee(anyLong(), any(EmployeeDTO.class))).willReturn(updatedEmployeeDTO);

        ResultActions response = mockMvc.perform(put("/api/employees/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployeeDTO)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updatedEmployeeDTO.getName())))
                // .andExpect(jsonPath("$.email", is(updatedEmployeeDTO.getEmail()))) // Removed email assertion
                .andExpect(jsonPath("$.age", is(updatedEmployeeDTO.getAge())));
    }

    @Test
    void updateEmployee_invalidInput_returnsBadRequest() throws Exception {
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidEmployeeDTO)));

        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void updateEmployee_notFound_returnsNotFound() throws Exception {
        given(employeeService.updateEmployee(anyLong(), any(EmployeeDTO.class)))
                .willThrow(new ResourceNotFoundException("Employee", "id", 1L));

        ResultActions response = mockMvc.perform(put("/api/employees/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTO)));

        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void deleteEmployee_exists_returnsOk() throws Exception {
        doNothing().when(employeeService).deleteEmployee(1L);

        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", 1L));

        response.andExpect(status().isOk())
                .andExpect(content().string("Employee deleted successfully. ID: 1"));
    }

    @Test
    void deleteEmployee_notFound_returnsNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Employee", "id", 1L)).when(employeeService).deleteEmployee(1L);

        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", 1L));

        response.andExpect(status().isNotFound())
                .andDo(print());
    }
}
