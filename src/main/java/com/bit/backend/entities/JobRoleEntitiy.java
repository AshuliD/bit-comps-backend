package com.bit.backend.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="job_role_one")
public class JobRoleEntitiy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Column(name = "role")
    private String role;

    public JobRoleEntitiy() {
    }

    public JobRoleEntitiy(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
