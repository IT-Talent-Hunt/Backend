package com.project.repository.project;

import com.project.dto.request.ProjectSearchParameters;
import com.project.model.Project;
import com.project.repository.SpecificationBuilder;
import com.project.repository.SpecificationProviderManager;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProjectSpecificationBuilder implements SpecificationBuilder<Project> {
    private final SpecificationProviderManager<Project> specificationProviderManager;

    @Override
    public Specification<Project> build(ProjectSearchParameters searchParameters) {
        Map<String, String[]> parameterMap = new HashMap<>();
        addParameterToMup(parameterMap, "name", searchParameters.name());
        addParameterToMup(parameterMap, "specialities", searchParameters.specialities());
        addParameterToMup(parameterMap, "status", searchParameters.status());
        addParameterToMup(parameterMap, "teamSize", searchParameters.teamSize());
        return parameterMap.entrySet().stream()
                .filter(k -> k.getValue() != null && k.getValue().length > 0)
                .map(k -> specificationProviderManager.getSpecificationProvider(k.getKey())
                        .getSpecification(k.getValue()))
                .reduce(Specification.where(null), Specification::and);
    }

    private void addParameterToMup(Map<String, String[]> paramMap, String key, String[] values) {
        if (values != null) {
            paramMap.put(key, values);
        }
    }
}
