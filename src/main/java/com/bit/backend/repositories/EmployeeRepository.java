package com.bit.backend.repositories; // Updated package

import com.bit.backend.entities.Employee; // Updated import
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import java.util.Optional; // Optional might still be used by JpaRepository methods like findById

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // findByEmail method and its Javadoc removed
}
