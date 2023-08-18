package com.project.model;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode
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
    private String description;
    @CreatedDate
    private LocalDate registrationDate;
    @ElementCollection
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    private List<Role> roles;
    @Enumerated(EnumType.STRING)
    private Speciality speciality;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SocialLink> socialLinks;
    private String skills;

    public enum Provider {
        LOCAL,
        GOOGLE
    }

    public enum Speciality {
        UI_UX_DESIGNER("UI/UX designer"),
        FRONTEND_DEVELOPER("Front-end developer"),
        FULLSTACK_DEVELOPER("Full-stack developer"),
        BACKEND_DEVELOPER("Back-end developer"),
        DEVOPS("DevOps"),
        PROJECT_MANAGER("Project manager"),
        QA_ENGINEER("QA Engineer"),
        MENTOR("Mentor");

        private final String value;

        Speciality(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Speciality fromValue(String value) {
            for (Speciality speciality : Speciality.values()) {
                if (speciality.value.equalsIgnoreCase(value)) {
                    return speciality;
                }
            }
            throw new IllegalArgumentException("Invalid SpecialityName value: " + value);
        }
    }
}
