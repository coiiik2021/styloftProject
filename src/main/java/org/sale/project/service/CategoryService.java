package org.sale.project.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Category;
import org.sale.project.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {

    CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(String id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public void deleteById(String id) {
        categoryRepository.deleteById(id);
    }

    public void saveCategory(Category category) {
        if(category.getId() != null || findCategoryByName(category.getName()) == null) {
            categoryRepository.save(category);
        }
    }

    public Category findCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }
}
