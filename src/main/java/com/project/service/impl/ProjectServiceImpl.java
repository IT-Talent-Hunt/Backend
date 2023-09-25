package com.project.service.impl;

import com.project.dto.request.ProjectSearchParameters;
import com.project.model.LikedCart;
import com.project.model.Project;
import com.project.model.Request;
import com.project.repository.LikedCartRepository;
import com.project.repository.RequestRepository;
import com.project.repository.project.ProjectRepository;
import com.project.repository.project.ProjectSpecificationBuilder;
import com.project.service.ProjectService;
import com.project.service.RequestService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final LikedCartRepository likedCartRepository;
    private final RequestRepository requestRepository;
    private final RequestService requestService;
    private final ProjectSpecificationBuilder projectSpecificationBuilder;

    @Override
    public Project getById(Long id) {
        return projectRepository.getById(id);
    }

    @Override
    public List<Project> findAll(PageRequest pageRequest) {
        return projectRepository.findAll(pageRequest).toList();
    }

    @Override
    public Page<Project> search(ProjectSearchParameters params,
                                PageRequest pageRequest) {
        Specification<Project> projectSpecification =
                projectSpecificationBuilder.build(params);
        return projectRepository.findAll(projectSpecification, pageRequest);
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        List<Request> allByProjectId = requestRepository.findAllByProjectId(id);
        allByProjectId.forEach(a -> {
            a.setProject(null);
            requestService.update(a);
        });
        if (projectOptional.isPresent()) {
            Project project = projectOptional.orElseThrow(
                    () -> new NoSuchElementException("Couldn't find project by id: " + id));
            List<LikedCart> likedCarts = likedCartRepository
                    .findByProjectsContaining(project);
            for (LikedCart likedCart : likedCarts) {
                likedCart.getProjects().remove(project);
            }
            likedCartRepository.saveAll(likedCarts);
            projectRepository.deleteById(id);
        }
    }

    @Override
    public List<Project> findAllByIdIn(List<Long> ids) {
        return projectRepository.findAllByIdIn(ids);
    }

    @Override
    public List<Project> findAllByUserIdAndProjectStatus(Long userId, Project.Status status) {
        return projectRepository.findAllProjectsByUserIdAndProjectStatus(userId, status);
    }

    @Override
    public List<Project> findAllOwnedByOwnerId(Long id) {
        return projectRepository.findAllByOwnerId(id);
    }

    @Override
    public Project changeStatus(Project project, String status) {
        project.setStatus(Project.Status.valueOf(status));
        return save(project);
    }
}
