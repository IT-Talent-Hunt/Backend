package com.project.repository.project;

import com.project.dto.ProjectSearchParameters;
import com.project.model.Project;
import com.project.repository.SpecificationBuilder;
import com.project.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProjectSpecificationBuilder implements SpecificationBuilder<Project> {
    private final SpecificationProviderManager<Project> specificationProviderManager;

    @Override
    public Specification<Project> build(ProjectSearchParameters searchParameters) {
        Specification<Project> spec = Specification.where(null);
        if (searchParameters.status() != null && searchParameters.status().length > 0) {
            spec = spec.and(specificationProviderManager.getSpecificationProvider("status")
                    .getSpecification(searchParameters.status()));
        }
        if (searchParameters.specialities() != null && searchParameters.specialities().length > 0) {
            spec = spec.and(specificationProviderManager.getSpecificationProvider("specialities")
                    .getSpecification(searchParameters.specialities()));
        }
        if (searchParameters.teamSize() != null && searchParameters.teamSize().length > 0) {
            spec = spec.and(specificationProviderManager.getSpecificationProvider("teamSize")
                    .getSpecification(searchParameters.teamSize()));
        }
        return spec;
    }

}
