package com.project.model;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@ToString
@Entity
@Table(name = "projects")
@EntityListeners(AuditingEntityListener.class)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "creation_date")
    @CreatedDate
    private LocalDateTime creationDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;
    @Column(length = 500)
    private String description;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private SocialLink socialLink;
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        RECRUITMENT("Recruitment"),
        IN_PROGRESS("In progress"),
        FINISHED("Finished");
        private final String value;

        Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Status fromValue(String value) {
            for (Status status : Status.values()) {
                if (status.value.equalsIgnoreCase(value)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Invalid project status value: " + value);
        }
    }
}
