package com.emre.gelirgider.mapper;

import com.emre.gelirgider.dto.CategoryDTO;
import com.emre.gelirgider.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }
        
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setIcon(category.getIcon());
        dto.setColor(category.getColor());
        dto.setType(category.getType());
        return dto;
    }

    public Category toEntity(CategoryDTO dto, com.emre.gelirgider.model.User user) {
        if (dto == null) {
            return null;
        }
        
        Category category = new Category();
        category.setId(dto.getId());
        category.setUser(user);
        category.setName(dto.getName());
        category.setIcon(dto.getIcon());
        category.setColor(dto.getColor());
        category.setType(dto.getType());
        return category;
    }
}
