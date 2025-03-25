package be.mentalhealth.springboot_backend.DTO;

import be.mentalhealth.springboot_backend.enums.RoleEnum;

public class UserViewDTO {
    private Long id;
    public RoleEnum roleEnum;
    private String name;

    public UserViewDTO(Long id, RoleEnum roleEnum, String name) {
        this.id = id;
        this.roleEnum = roleEnum;
        this.name = name;
    }

    public UserViewDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleEnum getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}