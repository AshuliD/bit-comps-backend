package com.example.bitcomps.employee.service;

import com.example.bitcomps.employee.dto.EmployeeDTO;
import com.example.bitcomps.employee.entity.Employee;
import com.example.bitcomps.employee.exception.ResourceNotFoundException;
import com.example.bitcomps.employee.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import for @Transactional

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional // Add Transactional for create operation
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        // Check if email already exists
        Optional<Employee> existingEmployee = employeeRepository.findByEmail(employeeDTO.getEmail());
        if (existingEmployee.isPresent()) {
            // Consider throwing a specific exception for duplicate email
            // For now, let's assume this is handled by database constraint or a custom validation
            // Or throw a custom exception e.g., EmailAlreadyExistsException
            throw new IllegalArgumentException("Employee with email " + employeeDTO.getEmail() + " already exists.");
        }

        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }

    @Override
    @Transactional(readOnly = true) // Add Transactional for read operations
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    @Transactional(readOnly = true) // Add Transactional for read operations
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional // Add Transactional for update operation
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));

        // Check if the updated email is being used by another employee
        if (employeeDTO.getEmail() != null && !employeeDTO.getEmail().equals(existingEmployee.getEmail())) {
            Optional<Employee> employeeWithNewEmail = employeeRepository.findByEmail(employeeDTO.getEmail());
            if (employeeWithNewEmail.isPresent()) {
                 throw new IllegalArgumentException("Email " + employeeDTO.getEmail() + " is already in use by another employee.");
            }
            existingEmployee.setEmail(employeeDTO.getEmail());
        }


        // Update fields
        if (employeeDTO.getFirstName() != null) {
            existingEmployee.setFirstName(employeeDTO.getFirstName());
        }
        if (employeeDTO.getLastName() != null) {
            existingEmployee.setLastName(employeeDTO.getLastName());
        }
        if (employeeDTO.getPosition() != null) {
            existingEmployee.setPosition(employeeDTO.getPosition());
        }
        // Note: ID should not be updated from DTO

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return modelMapper.map(updatedEmployee, EmployeeDTO.class);
    }

    @Override
    @Transactional // Add Transactional for delete operation
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        employeeRepository.delete(employee);
    }
}
