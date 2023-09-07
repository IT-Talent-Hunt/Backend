package com.project.repository.project.spec;

import com.project.model.Project;
import com.project.model.Team;
import com.project.repository.SpecificationProvider;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TeamSizeSpecificationProvider implements SpecificationProvider<Project> {
    private static final String key = "teamSize";

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Specification<Project> getSpecification(String[] params) {
        int requiredSpecialitiesCount = Integer.parseInt(params[0]);
        return (root, query, criteriaBuilder) -> {
            Join<Project, Team> teamJoin = root.join("team", JoinType.INNER);
            Expression<Integer> specialitiesCount = criteriaBuilder.size(teamJoin.get("users"));
            return criteriaBuilder.equal(specialitiesCount, requiredSpecialitiesCount);
        };
    }
}
