package org.ggyool.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    @Test
    public void creation(){
        User user = User.builder()
                .email("test@example.com")
                .name("test")
                .level(100L)
                .build();

        assertThat(user.getName()).isEqualTo("test");
        assertThat(user.isAdmin()).isEqualTo(true);
        assertThat(user.isActive()).isEqualTo(true);

        user.deactivate();
        assertThat(user.isActive()).isEqualTo(false);

    }


}