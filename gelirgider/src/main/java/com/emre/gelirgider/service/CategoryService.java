package com.emre.gelirgider.service;

import com.emre.gelirgider.context.UserContext;
import com.emre.gelirgider.dto.CategoryDTO;
import com.emre.gelirgider.mapper.CategoryMapper;
import com.emre.gelirgider.model.Category;
import com.emre.gelirgider.model.TransactionType;
import com.emre.gelirgider.model.User;
import com.emre.gelirgider.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private User currentUser() {
        return UserContext.getUser();
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findByUser(currentUser()).stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> getCategoriesByType(TransactionType type) {
        return categoryRepository.findByUserAndType(currentUser(), type).stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findByUserAndId(currentUser(), id)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı: " + id));
        return categoryMapper.toDTO(category);
    }

    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO, currentUser());
        Category saved = categoryRepository.save(category);
        return categoryMapper.toDTO(saved);
    }

    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findByUserAndId(currentUser(), id)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı: " + id));
        
        category.setName(categoryDTO.getName());
        category.setIcon(categoryDTO.getIcon());
        category.setColor(categoryDTO.getColor());
        category.setType(categoryDTO.getType());
        
        Category updated = categoryRepository.save(category);
        return categoryMapper.toDTO(updated);
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsByUserAndId(currentUser(), id)) {
            throw new RuntimeException("Kategori bulunamadı: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
