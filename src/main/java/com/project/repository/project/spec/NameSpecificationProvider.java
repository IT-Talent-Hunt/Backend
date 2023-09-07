package com.project.repository.project.spec;

import com.project.model.Project;
import com.project.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class NameSpecificationProvider implements SpecificationProvider<Project> {
    private static final String key = "name";

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Specification<Project> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get(key)),
                "%" + params[0].toLowerCase() + "%"
        );
    }
}
