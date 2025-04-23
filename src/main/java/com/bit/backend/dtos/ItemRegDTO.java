package com.bit.backend.dtos;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class ItemRegDTO {
    private Long id;
    private String name;
    private String code;
    private String brand;
    private String material;
    private String category;

    public ItemRegDTO(String category, String material, String brand, String code, String name, Long id) {
        this.category = category;
        this.material = material;
        this.brand = brand;
        this.code = code;
        this.name = name;
        this.id = id;
    }

    public ItemRegDTO() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
