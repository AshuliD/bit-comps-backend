package com.example.bitcomps.employee.repository;

import com.example.bitcomps.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Finds an employee by their email address.
     *
     * @param email the email address of the employee to find
     * @return an Optional containing the employee if found, or an empty Optional otherwise
     */
    Optional<Employee> findByEmail(String email);
}
