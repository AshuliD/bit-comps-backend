package com.bit.backend.services.impl; // Updated package

import com.bit.backend.dtos.EmployeeDTO; // Updated import
import com.bit.backend.entities.Employee; // Updated import
import com.bit.backend.exceptions.ResourceNotFoundException; // Updated import
import com.bit.backend.mappers.EmployeeMapper; // Updated import
import com.bit.backend.repositories.EmployeeRepository; // Updated import
// Note: EmployeeService interface is not directly used in this test, only EmployeeServiceImpl
// import com.bit.backend.services.EmployeeService; 

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList; // Added for anyList()
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString; // Keep if still used, remove if not
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService; // This will be the class from the new package

    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setName("John Doe");
        employeeDTO.setAge(30);
        employeeDTO.setBirthDate(LocalDate.of(1990, 1, 1));
        employeeDTO.setSalary(new BigDecimal("50000.00"));
        employeeDTO.setPhoneNumber("123-456-7890");

        employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");
        employee.setAge(30);
        employee.setBirthDate(LocalDate.of(1990, 1, 1));
        employee.setSalary(new BigDecimal("50000.00"));
        employee.setPhoneNumber("123-456-7890");
    }

    @Test
    void createEmployee_success() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(employeeMapper.toEntity(any(EmployeeDTO.class))).thenReturn(employee);
        when(employeeMapper.toDto(any(Employee.class))).thenReturn(employeeDTO);

        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);

        assertNotNull(createdEmployee);
        assertEquals(employeeDTO.getName(), createdEmployee.getName());
        assertEquals(employeeDTO.getAge(), createdEmployee.getAge());
        verify(employeeRepository, times(1)).save(any(Employee.class));
        verify(employeeMapper, times(1)).toEntity(any(EmployeeDTO.class));
        verify(employeeMapper, times(1)).toDto(any(Employee.class));
    }

    @Test
    void getEmployeeById_success() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        when(employeeMapper.toDto(any(Employee.class))).thenReturn(employeeDTO);

        EmployeeDTO foundEmployee = employeeService.getEmployeeById(1L);

        assertNotNull(foundEmployee);
        assertEquals(employeeDTO.getId(), foundEmployee.getId());
        assertEquals(employeeDTO.getName(), foundEmployee.getName());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeMapper, times(1)).toDto(any(Employee.class));
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
        when(employeeMapper.toDtoList(anyList())).thenReturn(List.of(employeeDTO));

        List<EmployeeDTO> employees = employeeService.getAllEmployees();

        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals(employeeDTO.getName(), employees.get(0).getName());
        verify(employeeRepository, times(1)).findAll();
        verify(employeeMapper, times(1)).toDtoList(anyList());
    }

    @Test
    void getAllEmployees_empty() {
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());
        when(employeeMapper.toDtoList(anyList())).thenReturn(Collections.emptyList());

        List<EmployeeDTO> employees = employeeService.getAllEmployees();

        assertNotNull(employees);
        assertTrue(employees.isEmpty());
        verify(employeeRepository, times(1)).findAll();
        verify(employeeMapper, times(1)).toDtoList(anyList());
    }

    @Test
    void updateEmployee_success() {
        EmployeeDTO updatedDetailsDTO = new EmployeeDTO();
        updatedDetailsDTO.setId(1L);
        updatedDetailsDTO.setName("Johnathan Doer");
        updatedDetailsDTO.setAge(32);
        updatedDetailsDTO.setBirthDate(LocalDate.of(1988, 5, 5));
        updatedDetailsDTO.setSalary(new BigDecimal("60000.00"));
        updatedDetailsDTO.setPhoneNumber("987-654-3210");

        Employee updatedEmployeeEntity = new Employee();
        updatedEmployeeEntity.setId(1L);
        updatedEmployeeEntity.setName(updatedDetailsDTO.getName());
        updatedEmployeeEntity.setAge(updatedDetailsDTO.getAge());
        updatedEmployeeEntity.setBirthDate(updatedDetailsDTO.getBirthDate());
        updatedEmployeeEntity.setSalary(updatedDetailsDTO.getSalary());
        updatedEmployeeEntity.setPhoneNumber(updatedDetailsDTO.getPhoneNumber());

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployeeEntity);
        when(employeeMapper.toDto(updatedEmployeeEntity)).thenReturn(updatedDetailsDTO);

        EmployeeDTO resultDTO = employeeService.updateEmployee(1L, updatedDetailsDTO);

        assertNotNull(resultDTO);
        assertEquals(updatedDetailsDTO.getName(), resultDTO.getName());
        assertEquals(updatedDetailsDTO.getAge(), resultDTO.getAge());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
        verify(employeeMapper, times(1)).toDto(updatedEmployeeEntity);
    }

    @Test
    void updateEmployee_general_success() { 
        EmployeeDTO updatedDetailsDTO = new EmployeeDTO();
        updatedDetailsDTO.setId(1L);
        updatedDetailsDTO.setName("Johnathan Doer Updated");
        updatedDetailsDTO.setAge(33);
        updatedDetailsDTO.setBirthDate(LocalDate.of(1989, 6, 6));
        updatedDetailsDTO.setSalary(new BigDecimal("65000.00"));
        updatedDetailsDTO.setPhoneNumber("000-111-2222");

        Employee updatedEmployeeEntity = new Employee();
        updatedEmployeeEntity.setId(1L);
        updatedEmployeeEntity.setName(updatedDetailsDTO.getName());
        updatedEmployeeEntity.setAge(updatedDetailsDTO.getAge());
        updatedEmployeeEntity.setBirthDate(updatedDetailsDTO.getBirthDate());
        updatedEmployeeEntity.setSalary(updatedDetailsDTO.getSalary());
        updatedEmployeeEntity.setPhoneNumber(updatedDetailsDTO.getPhoneNumber());

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployeeEntity);
        when(employeeMapper.toDto(updatedEmployeeEntity)).thenReturn(updatedDetailsDTO);

        EmployeeDTO resultDTO = employeeService.updateEmployee(1L, updatedDetailsDTO);

        assertNotNull(resultDTO);
        assertEquals(updatedDetailsDTO.getName(), resultDTO.getName());
        assertEquals(updatedDetailsDTO.getAge(), resultDTO.getAge());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
        verify(employeeMapper, times(1)).toDto(updatedEmployeeEntity);
    }

    @Test
    void updateEmployee_notFound() {
        EmployeeDTO updatedDetailsDTO = new EmployeeDTO(); 
        updatedDetailsDTO.setName("Any Name");

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.updateEmployee(1L, updatedDetailsDTO);
        });

        assertEquals("Employee not found with id : '1'", exception.getMessage());
        verify(employeeRepository, times(1)).findById(1L);

        verify(employeeRepository, never()).save(any(Employee.class));
        verify(employeeMapper, never()).toDto(any(Employee.class));
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
