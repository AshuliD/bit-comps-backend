package com.bit.backend.mappers; // Updated package

import com.bit.backend.dtos.EmployeeDTO; // Updated import
import com.bit.backend.entities.Employee; // Updated import
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    /**
     * Maps an EmployeeDTO to an Employee entity.
     *
     * @param employeeDTO the EmployeeDTO to map
     * @return the mapped Employee entity
     */
    Employee toEntity(EmployeeDTO employeeDTO);

    /**
     * Maps an Employee entity to an EmployeeDTO.
     *
     * @param employee the Employee entity to map
     * @return the mapped EmployeeDTO
     */
    EmployeeDTO toDto(Employee employee);

    /**
     * Maps a list of Employee entities to a list of EmployeeDTOs.
     *
     * @param employees the list of Employee entities to map
     * @return the list of mapped EmployeeDTOs
     */
    List<EmployeeDTO> toDtoList(List<Employee> employees);
}
