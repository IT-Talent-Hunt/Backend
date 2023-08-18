package com.project.repository.project.spec;

import com.project.model.Project;
import com.project.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class StatusSpecificationProvider implements SpecificationProvider<Project> {

    @Override
    public String getKey() {
        return "status";
    }

    @Override
    public Specification<Project> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> root.get("status")
                .in(Arrays.stream(params).map(Project.Status::valueOf).toArray());
    }
}
