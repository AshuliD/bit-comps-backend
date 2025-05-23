package com.example.bitcomps.employee.controller;

import com.example.bitcomps.employee.dto.EmployeeDTO;
import com.example.bitcomps.employee.exception.ResourceNotFoundException;
import com.example.bitcomps.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private EmployeeDTO employeeDTO;
    private EmployeeDTO invalidEmployeeDTO;

    @BeforeEach
    void setUp() {
        employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setEmail("john.doe@example.com");
        employeeDTO.setPosition("Developer");

        invalidEmployeeDTO = new EmployeeDTO();
        invalidEmployeeDTO.setFirstName(""); // Invalid
        invalidEmployeeDTO.setLastName("Doe");
        invalidEmployeeDTO.setEmail("john.doe@example.com");
        invalidEmployeeDTO.setPosition("Developer");
    }

    @Test
    void createEmployee_validInput_returnsCreated() throws Exception {
        given(employeeService.createEmployee(any(EmployeeDTO.class))).willReturn(employeeDTO);

        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTO)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employeeDTO.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employeeDTO.getLastName())))
                .andExpect(jsonPath("$.email", is(employeeDTO.getEmail())));
    }

    @Test
    void createEmployee_invalidInput_returnsBadRequest() throws Exception {
        // No need to mock employeeService.createEmployee because validation should fail before service is called.
        // Spring Boot's @Valid annotation on the @RequestBody parameter in the controller
        // will trigger validation. If validation fails, a MethodArgumentNotValidException is thrown,
        // which is automatically handled by Spring to return a 400 Bad Request.

        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidEmployeeDTO)));

        response.andExpect(status().isBadRequest())
                .andDo(print()); // Useful for debugging the exact validation errors
    }

    @Test
    void getEmployeeById_exists_returnsOk() throws Exception {
        given(employeeService.getEmployeeById(1L)).willReturn(employeeDTO);

        ResultActions response = mockMvc.perform(get("/api/employees/{id}", 1L));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(employeeDTO.getFirstName())))
                .andExpect(jsonPath("$.email", is(employeeDTO.getEmail())));
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
        listOfEmployees.add(new EmployeeDTO(2L, "Jane", "Doe", "jane.doe@example.com", "Manager"));

        given(employeeService.getAllEmployees()).willReturn(listOfEmployees);

        ResultActions response = mockMvc.perform(get("/api/employees"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(listOfEmployees.size())))
                .andExpect(jsonPath("$[0].email", is(employeeDTO.getEmail())));
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
        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO(1L, "Johnathan", "Doer", "john.doer@example.com", "Senior Dev");
        given(employeeService.updateEmployee(anyLong(), any(EmployeeDTO.class))).willReturn(updatedEmployeeDTO);

        ResultActions response = mockMvc.perform(put("/api/employees/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployeeDTO)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(updatedEmployeeDTO.getFirstName())))
                .andExpect(jsonPath("$.email", is(updatedEmployeeDTO.getEmail())));
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
                .content(objectMapper.writeValueAsString(employeeDTO))); // Valid DTO, but ID won't be found by service

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
