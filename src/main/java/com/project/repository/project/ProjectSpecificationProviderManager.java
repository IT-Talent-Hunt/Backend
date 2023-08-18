package com.project.repository.project;

import com.project.model.Project;
import com.project.repository.SpecificationProvider;
import com.project.repository.SpecificationProviderManager;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProjectSpecificationProviderManager implements SpecificationProviderManager<Project> {
    private final List<SpecificationProvider<Project>> projectSpecificationProviders;

    @Override
    public SpecificationProvider<Project> getSpecificationProvider(String key) {
        return projectSpecificationProviders.stream()
                .filter(project -> project.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find correct "
                        + "specification provider for key " + key));
    }
}
