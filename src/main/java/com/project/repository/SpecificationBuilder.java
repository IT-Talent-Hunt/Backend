package com.project.repository;

import com.project.dto.ProjectSearchParameters;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(ProjectSearchParameters searchParameters);

}
