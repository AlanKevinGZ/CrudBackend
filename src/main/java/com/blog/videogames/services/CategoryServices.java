package com.blog.videogames.services;

import com.blog.videogames.DTO.CategoryDTO;
import com.blog.videogames.DTO.VideoGameDTO;
import com.blog.videogames.models.Categorys;
import com.blog.videogames.models.VideoGames;
import com.blog.videogames.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServices {

    @Autowired
    private ICategoryRepository categoryRepository;

    public List<CategoryDTO> getCategory() {
        List<Categorys> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CategoryDTO convertToDTO(Categorys category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

}
