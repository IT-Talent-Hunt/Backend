package com.project.model;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Role {
    private RoleName roleName;

    public enum RoleName {
        USER,
        ADMIN
    }
}
