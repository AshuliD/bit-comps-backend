package com.bit.backend.entities; // Updated package

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// import jakarta.persistence.UniqueConstraint; // No longer needed if email is the only constraint

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employees") // Removed uniqueConstraints for email
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String name;

    // Removed email field
    // @Column(name = "email", nullable = false, unique = true)
    // private String email;

    private Integer age;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private BigDecimal salary; // Default column name "salary"

    @Column(name = "phone_number")
    private String phoneNumber;


    // No-arg constructor (required by JPA)
    public Employee() {
    }

    // All-args constructor
    public Employee(String name, Integer age, LocalDate birthDate, BigDecimal salary, String phoneNumber) {
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
