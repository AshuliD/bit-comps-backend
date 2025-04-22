package com.bit.backend.dtos;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class FormDemoDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private LocalDate birthDate;
    private String gender;
    private String role;



    public FormDemoDto() {
    }

    public FormDemoDto(Long id, String role, String firstName, String lastName, String email, int age, LocalDate birthDate, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.age = age;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
