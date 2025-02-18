package com.example.bookhive.service.dto;

public class CustomerDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Long roleId;
    private Double totalPurchasesAmount;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getTotalPurchasesAmount() {
        return totalPurchasesAmount;
    }

    public void setTotalPurchasesAmount(Double totalPurchasesAmount) {
        this.totalPurchasesAmount = totalPurchasesAmount;
    }

    public Long getRoleId() {
        return roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleId(Long roleId) {

        this.roleId = roleId;
    }
}
