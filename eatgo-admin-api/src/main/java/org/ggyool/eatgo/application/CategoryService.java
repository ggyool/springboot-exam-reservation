package org.ggyool.eatgo.application;

import org.ggyool.eatgo.domain.Category;
import org.ggyool.eatgo.domain.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public Category addCategory(String name) {
        return categoryRepository.save(Category.builder().name(name).build());
    }
}
