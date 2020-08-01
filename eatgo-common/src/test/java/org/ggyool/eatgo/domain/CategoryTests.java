package org.ggyool.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CategoryTests {

    @Test
    public void creation(){
        Category category = Category.builder().name("korean").build();
        assertThat(category.getName()).isEqualTo("korean");
    }

}