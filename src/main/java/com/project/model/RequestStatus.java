package com.project.model;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class RequestStatus {
    private Status status;

    public enum Status {
        ACCEPTED,
        PENDING,
        REJECTED
    }
}
