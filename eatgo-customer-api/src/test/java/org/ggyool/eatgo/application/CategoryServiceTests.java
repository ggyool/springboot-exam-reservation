package org.ggyool.eatgo.application;

import org.ggyool.eatgo.domain.Category;
import org.ggyool.eatgo.domain.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class CategoryServiceTests {

    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    public void getCategories(){
        List<Category> mockCategories = new ArrayList<>();
        mockCategories.add(Category.builder().name("korean").build());

        given(categoryRepository.findAll()).willReturn(mockCategories);

        List<Category> categories = categoryService.getCategories();
        assertThat(categories.get(0).getName()).isEqualTo("korean");
    }

    @Test
    public void addCategory(){
        given(categoryRepository.save(any())).will(invocation->{
            Category category = invocation.getArgument(0);
            return Category.builder()
                    .name(category.getName())
                    .build();
        });
        Category category = categoryService.addCategory("korean");
        verify(categoryRepository).save(any());
        assertThat(category.getName()).isEqualTo("korean");
    }

}