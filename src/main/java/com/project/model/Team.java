package com.project.model;

import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private List<User> users;
    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "specialities_teams", joinColumns = @JoinColumn(name = "team_id"))
    private List<User.Speciality> requiredSpecialities;

    @OneToMany
    @JoinColumn(name = "team.id")
    private List<Request> requests;

    public int getMaxMembers() {
        return users.size() + requiredSpecialities.size();
    }
}
