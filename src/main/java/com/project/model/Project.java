package com.project.model;

import java.time.LocalDateTime;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private String description;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    @ElementCollection
    @CollectionTable(name = "social_links", joinColumns = @JoinColumn(name = "project_id"))
    private Map<String, String> socialLinks;
    @Enumerated(EnumType.STRING)
    private ProjectStatus.Status projectStatus;

    public Project() {
        this.projectStatus = ProjectStatus.Status.RECRUITMENT;
    }
}
