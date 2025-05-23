package com.bit.backend.services; // Updated package

import com.bit.backend.dtos.EmployeeDTO; // Updated import
import java.util.List;

public interface EmployeeService {

    /**
     * Creates a new employee.
     *
     * @param employeeDTO the DTO containing the details of the employee to create
     * @return the DTO of the created employee
     */
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    /**
     * Retrieves an employee by their ID.
     *
     * @param id the ID of the employee to retrieve
     * @return the DTO of the found employee
     * @throws com.bit.backend.exceptions.ResourceNotFoundException if no employee is found with the given ID
     */
    EmployeeDTO getEmployeeById(Long id);

    /**
     * Retrieves all employees.
     *
     * @return a list of DTOs of all employees
     */
    List<EmployeeDTO> getAllEmployees();

    /**
     * Updates an existing employee.
     *
     * @param id          the ID of the employee to update
     * @param employeeDTO the DTO containing the updated details of the employee
     * @return the DTO of the updated employee
     * @throws com.bit.backend.exceptions.ResourceNotFoundException if no employee is found with the given ID
     */
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);

    /**
     * Deletes an employee by their ID.
     *
     * @param id the ID of the employee to delete
     * @throws com.bit.backend.exceptions.ResourceNotFoundException if no employee is found with the given ID
     */
    void deleteEmployee(Long id);
}
