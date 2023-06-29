package com.project.service;

import com.project.model.LikedCart;
import com.project.model.Project;
import com.project.model.User;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface LikedCartService {
    LikedCart getById(Long id);

    LikedCart getByUserId(Long id);

    LikedCart addProject(Project project, User user) throws Exception;

    LikedCart deleteProject(Project project, User user) throws Exception;

    List<LikedCart> findAll(PageRequest pageRequest);

    LikedCart save(LikedCart likedCart);

    void deleteById(Long id);

}
