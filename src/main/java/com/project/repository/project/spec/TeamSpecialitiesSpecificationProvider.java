package com.project.repository.project.spec;

import com.project.model.Project;
import com.project.model.Team;
import com.project.model.User;
import com.project.repository.SpecificationProvider;
import java.util.Arrays;
import java.util.List;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TeamSpecialitiesSpecificationProvider implements SpecificationProvider<Project> {
    private static final String key = "specialities";

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Specification<Project> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> {
            Join<Project, Team> teamJoin = root.join("team", JoinType.INNER);
            Expression<List<User.Speciality>> specialitiesExpression
                    = teamJoin.get("requiredSpecialities");
            Predicate[] predicates = Arrays.stream(params)
                    .map(User.Speciality::valueOf)
                    .map(speciality -> criteriaBuilder.isMember(speciality, specialitiesExpression))
                    .toArray(Predicate[]::new);

            return criteriaBuilder.or(predicates);
        };
    }
}
