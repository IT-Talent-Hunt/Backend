package com.project.service;

import com.project.model.LikedCart;
import java.util.List;

public interface LikedCartService {
    LikedCart getById(Long id);

    List<LikedCart> getAll();

    LikedCart save(LikedCart likedCart);

    void deleteById(Long id);

}
