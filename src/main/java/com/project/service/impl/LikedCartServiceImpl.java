package com.project.service.impl;

import com.project.model.LikedCart;
import com.project.model.Project;
import com.project.model.User;
import com.project.repository.LikedCartRepository;
import com.project.service.LikedCartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikedCartServiceImpl implements LikedCartService {
    private final LikedCartRepository likedCartRepository;

    @Override
    public LikedCart getById(Long id) {
        return likedCartRepository.getById(id);
    }

    @Override
    public Page<Project> findProjectByUserId(Long userId, PageRequest pageRequest) {
        return likedCartRepository.findProjectsByUserId(userId, pageRequest);
    }

    @Override
    public LikedCart getByUserId(Long userId) {
        return likedCartRepository.getByUserId(userId);
    }

    @Override
    public LikedCart addProject(Project project, User user) throws Exception {
        LikedCart likedCartByUser = likedCartRepository.getByUserId(user.getId());
        if (likedCartByUser.getProjects().contains(project)) {
            throw new Exception("Project: " + project.getName() + " already in the cart");
        } else {
            List<Project> projects = likedCartByUser.getProjects();
            projects.add(project);
            likedCartByUser.setProjects(projects);
            return likedCartRepository.save(likedCartByUser);
        }
    }

    @Override
    public LikedCart deleteProject(Project project, User user) throws Exception {
        LikedCart likedCartByUser = likedCartRepository.getByUserId(user.getId());
        if (!likedCartByUser.getProjects().contains(project)) {
            throw new Exception("Project: " + project.getName() + " is not in the cart");
        } else {
            List<Project> projects = likedCartByUser.getProjects();
            projects.remove(project);
            likedCartByUser.setProjects(projects);
            return likedCartRepository.save(likedCartByUser);
        }
    }

    @Override
    public List<LikedCart> findAll(PageRequest pageRequest) {
        return likedCartRepository.findAll(pageRequest).toList();
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
