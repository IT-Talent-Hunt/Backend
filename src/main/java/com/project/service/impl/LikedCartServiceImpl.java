package com.project.service.impl;

import com.project.model.LikedCart;
import com.project.repository.LikedCartRepository;
import com.project.service.LikedCartService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LikedCartServiceImpl implements LikedCartService {
    private final LikedCartRepository likedCartRepository;

    public LikedCartServiceImpl(LikedCartRepository likedCartRepository) {
        this.likedCartRepository = likedCartRepository;
    }

    @Override
    public LikedCart getById(Long id) {
        return likedCartRepository.getById(id);
    }

    @Override
    public List<LikedCart> getAll() {
        return likedCartRepository.findAll();
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
