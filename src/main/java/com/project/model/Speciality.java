package com.project.model;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Speciality {
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
