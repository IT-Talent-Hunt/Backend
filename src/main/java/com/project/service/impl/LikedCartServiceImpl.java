package com.project.service.impl;

import com.project.model.LikedCart;
import com.project.model.Project;
import com.project.repository.LikedCartRepository;
import com.project.service.LikedCartService;
import com.project.service.ProjectService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class LikedCartServiceImpl implements LikedCartService {
    private final LikedCartRepository likedCartRepository;
    private final ProjectService projectService;

    public LikedCartServiceImpl(LikedCartRepository likedCartRepository,
                                ProjectService projectService) {
        this.likedCartRepository = likedCartRepository;
        this.projectService = projectService;
    }

    @Override
    public LikedCart getById(Long id) {
        return likedCartRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Can`t find liked cart by id" + id));
    }

    @Override
    public List<LikedCart> getAll() {
        return likedCartRepository.findAll();
    }

    @Override
    public LikedCart addProject(Long likedCartId, Long projectId) {
        Project project = projectService.getById(projectId);
        LikedCart likedCart = getById(likedCartId);
        likedCart.getProjects().add(project);
        save(likedCart);
        return likedCart;
    }

    @Override
    public LikedCart save(LikedCart likedCart) {
        return likedCartRepository.save(likedCart);
    }

    @Override
    public void deleteById(Long id) {
        likedCartRepository.deleteById(id);
    }
}
