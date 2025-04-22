package com.bit.backend.dtos;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class JobRoleDto {
    private Long id;
    private String role;

    public JobRoleDto(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public JobRoleDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
