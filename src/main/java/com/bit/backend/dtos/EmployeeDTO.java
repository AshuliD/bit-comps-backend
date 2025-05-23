package com.bit.backend.dtos; // Updated package

// import jakarta.validation.constraints.Email; // Removed as email field is removed
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeDTO {

    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    // Removed email field and its annotations
    // @Email(message = "Email should be valid")
    // @NotEmpty(message = "Email cannot be empty")
    // private String email;

    @NotNull(message = "Age cannot be null")
    @Min(value = 18, message = "Age must be at least 18")
    private Integer age;

    @NotNull(message = "Birth date cannot be null")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotNull(message = "Salary cannot be null")
    @Positive(message = "Salary must be positive")
    private BigDecimal salary;

    @NotEmpty(message = "Phone number cannot be empty")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number format is invalid")
    private String phoneNumber;


    // No-arg constructor
    public EmployeeDTO() {
    }

    // All-args constructor
    public EmployeeDTO(Long id, String name, Integer age, LocalDate birthDate, BigDecimal salary, String phoneNumber) {
        this.id = id;
        this.name = name;
        // this.email = email; // Removed email assignment
        this.age = age;
        this.birthDate = birthDate;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Removed getEmail() and setEmail() methods

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
