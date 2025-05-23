package com.bit.backend.services.impl; // Updated package

import com.bit.backend.dtos.EmployeeDTO; // Updated import
import com.bit.backend.entities.Employee; // Updated import
import com.bit.backend.exceptions.ResourceNotFoundException; // Updated import
import com.bit.backend.mappers.EmployeeMapper; // Updated import
import com.bit.backend.repositories.EmployeeRepository; // Updated import
import com.bit.backend.services.EmployeeService; // Updated import

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
// import java.util.stream.Collectors; // No longer needed as toDtoList handles it

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    @Transactional
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        // Removed email uniqueness check

        Employee employee = employeeMapper.toEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDto(savedEmployee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        return employeeMapper.toDto(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employeeMapper.toDtoList(employees);
    }

    @Override
    @Transactional
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));

        // Removed email conflict check and email field update

        // Update fields from DTO - ensure null checks if partial updates are allowed
        // or rely on mapper if it's configured for partial updates (e.g., @Mapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE))
        // For a full update as implied by PUT, we set all mutable fields from the DTO.
        if (employeeDTO.getName() != null) {
            existingEmployee.setName(employeeDTO.getName());
        }
        if (employeeDTO.getAge() != null) {
            existingEmployee.setAge(employeeDTO.getAge());
        }
        if (employeeDTO.getBirthDate() != null) {
            existingEmployee.setBirthDate(employeeDTO.getBirthDate());
        }
        if (employeeDTO.getSalary() != null) {
            existingEmployee.setSalary(employeeDTO.getSalary());
        }
        if (employeeDTO.getPhoneNumber() != null) {
            existingEmployee.setPhoneNumber(employeeDTO.getPhoneNumber());
        }
        // Email field is no longer part of the entity or DTO for update
        // ID should not be updated from DTO

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return employeeMapper.toDto(updatedEmployee);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        employeeRepository.delete(employee);
    }
}
