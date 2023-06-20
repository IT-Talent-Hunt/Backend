package com.project.model;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ProjectStatus {
    private Status status;

    public enum Status {
        RECRUITMENT,
        IN_PROGRESS,
        COMPLETED
    }
}
