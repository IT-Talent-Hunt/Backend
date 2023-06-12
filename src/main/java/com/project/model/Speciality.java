package com.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Speciality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private SpecialityName specialityName;

    enum SpecialityName {
        QA,
        BACKEND,
        FRONTEND,
        FULLSTACK,
        DEVOPS,
        PROJECT_MANAGER,
        DESIGNER
    }
}
