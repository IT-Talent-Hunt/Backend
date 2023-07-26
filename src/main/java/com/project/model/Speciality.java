package com.project.model;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Speciality {
    @Enumerated(EnumType.STRING)
    private SpecialityName specialityName;

    public Speciality(SpecialityName specialityName) {
        this.specialityName = specialityName;
    }

    public Speciality() {
    }

    public enum SpecialityName {
        QA(""),
        BACKEND(""),
        FRONTEND(""),
        FULLSTACK(""),
        DEVOPS(""),
        PROJECT_MANAGER(""),
        DESIGNER("");

        SpecialityName(String qa) {
        }
    }
}
