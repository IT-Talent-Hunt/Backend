package com.project.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Provider provider;
    private String profileImage;
    @CreatedDate
    private LocalDate registrationDate;
    @ElementCollection
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    private List<Role> roles;
    @ElementCollection
    @CollectionTable(name = "specialities", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Speciality> specialities;

    public enum Provider {
        LOCAL,
        GOOGLE
    }
}
