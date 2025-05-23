package com.example.bitcomps.employee.service;

import com.example.bitcomps.employee.dto.EmployeeDTO;
import com.example.bitcomps.employee.entity.Employee;
import com.example.bitcomps.employee.exception.ResourceNotFoundException;
import com.example.bitcomps.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy // Use Spy for ModelMapper if you want to test its actual mapping
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        // modelMapper = new ModelMapper(); // Already handled by @Spy and DI
        // employeeService = new EmployeeServiceImpl(employeeRepository, modelMapper); // Handled by @InjectMocks

        employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setEmail("john.doe@example.com");
        employeeDTO.setPosition("Developer");

        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setPosition("Developer");
    }

    @Test
    void createEmployee_success() {
        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        // when(modelMapper.map(any(EmployeeDTO.class), eq(Employee.class))).thenReturn(employee); // Spy handles this
        // when(modelMapper.map(any(Employee.class), eq(EmployeeDTO.class))).thenReturn(employeeDTO); // Spy handles this


        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);

        assertNotNull(createdEmployee);
        assertEquals("john.doe@example.com", createdEmployee.getEmail());
        verify(employeeRepository, times(1)).findByEmail("john.doe@example.com");
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void createEmployee_emailExists() {
        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.of(employee));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.createEmployee(employeeDTO);
        });

        assertEquals("Employee with email " + employeeDTO.getEmail() + " already exists.", exception.getMessage());
        verify(employeeRepository, times(1)).findByEmail("john.doe@example.com");
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void getEmployeeById_success() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        // when(modelMapper.map(any(Employee.class), eq(EmployeeDTO.class))).thenReturn(employeeDTO);

        EmployeeDTO foundEmployee = employeeService.getEmployeeById(1L);

        assertNotNull(foundEmployee);
        assertEquals(1L, foundEmployee.getId());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void getEmployeeById_notFound() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeById(1L);
        });

        assertEquals("Employee not found with id : '1'", exception.getMessage());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void getAllEmployees_success() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        // when(modelMapper.map(any(Employee.class), eq(EmployeeDTO.class))).thenReturn(employeeDTO);

        List<EmployeeDTO> employees = employeeService.getAllEmployees();

        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals("john.doe@example.com", employees.get(0).getEmail());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void getAllEmployees_empty() {
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        List<EmployeeDTO> employees = employeeService.getAllEmployees();

        assertNotNull(employees);
        assertTrue(employees.isEmpty());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void updateEmployee_success() {
        EmployeeDTO updatedDetailsDTO = new EmployeeDTO();
        updatedDetailsDTO.setId(1L); // ID in DTO for update is usually ignored or used for consistency check
        updatedDetailsDTO.setFirstName("Johnathan");
        updatedDetailsDTO.setLastName("Doer");
        updatedDetailsDTO.setEmail("johnathan.doer@example.com"); // New email
        updatedDetailsDTO.setPosition("Senior Developer");

        Employee updatedEmployeeEntity = new Employee();
        updatedEmployeeEntity.setId(1L);
        updatedEmployeeEntity.setFirstName("Johnathan");
        updatedEmployeeEntity.setLastName("Doer");
        updatedEmployeeEntity.setEmail("johnathan.doer@example.com");
        updatedEmployeeEntity.setPosition("Senior Developer");


        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee)); // Return original employee
        when(employeeRepository.findByEmail("johnathan.doer@example.com")).thenReturn(Optional.empty()); // New email is not taken
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployeeEntity); // Return the updated entity from save

        EmployeeDTO resultDTO = employeeService.updateEmployee(1L, updatedDetailsDTO);

        assertNotNull(resultDTO);
        assertEquals("Johnathan", resultDTO.getFirstName());
        assertEquals("johnathan.doer@example.com", resultDTO.getEmail());
        assertEquals("Senior Developer", resultDTO.getPosition());

        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).findByEmail("johnathan.doer@example.com");
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }
    
    @Test
    void updateEmployee_success_sameEmail() {
        EmployeeDTO updatedDetailsDTO = new EmployeeDTO();
        updatedDetailsDTO.setFirstName("Johnathan");
        updatedDetailsDTO.setLastName("Doer");
        updatedDetailsDTO.setEmail("john.doe@example.com"); // Same email as original
        updatedDetailsDTO.setPosition("Senior Developer");

        Employee updatedEmployeeEntity = new Employee(); // This would be the state after mapping DTO to existing entity
        updatedEmployeeEntity.setId(1L);
        updatedEmployeeEntity.setFirstName("Johnathan");
        updatedEmployeeEntity.setLastName("Doer");
        updatedEmployeeEntity.setEmail("john.doe@example.com");
        updatedEmployeeEntity.setPosition("Senior Developer");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee)); // Original employee
        // No need to mock findByEmail if email is not changing
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployeeEntity);

        EmployeeDTO resultDTO = employeeService.updateEmployee(1L, updatedDetailsDTO);

        assertNotNull(resultDTO);
        assertEquals("Johnathan", resultDTO.getFirstName());
        assertEquals("john.doe@example.com", resultDTO.getEmail()); // Email remains the same
        assertEquals("Senior Developer", resultDTO.getPosition());

        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, never()).findByEmail(anyString()); // Should not be called if email is not changed
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }


    @Test
    void updateEmployee_notFound() {
        EmployeeDTO updatedDetailsDTO = new EmployeeDTO();
        updatedDetailsDTO.setEmail("new.email@example.com");

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.updateEmployee(1L, updatedDetailsDTO);
        });

        assertEquals("Employee not found with id : '1'", exception.getMessage());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, never()).findByEmail(anyString());
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void updateEmployee_emailConflict() {
        EmployeeDTO updatedDetailsDTO = new EmployeeDTO();
        updatedDetailsDTO.setEmail("jane.doe@example.com"); // Trying to update to Jane's email

        Employee anotherEmployee = new Employee(); // This is Jane
        anotherEmployee.setId(2L);
        anotherEmployee.setEmail("jane.doe@example.com");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee)); // John Doe exists
        when(employeeRepository.findByEmail("jane.doe@example.com")).thenReturn(Optional.of(anotherEmployee)); // Jane's email is found

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.updateEmployee(1L, updatedDetailsDTO);
        });

        assertEquals("Email " + updatedDetailsDTO.getEmail() + " is already in use by another employee.", exception.getMessage());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).findByEmail("jane.doe@example.com");
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void deleteEmployee_success() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(any(Employee.class));

        assertDoesNotThrow(() -> employeeService.deleteEmployee(1L));

        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    void deleteEmployee_notFound() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.deleteEmployee(1L);
        });

        assertEquals("Employee not found with id : '1'", exception.getMessage());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, never()).delete(any(Employee.class));
    }
}
