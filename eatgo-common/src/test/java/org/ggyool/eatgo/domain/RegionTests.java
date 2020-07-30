package org.ggyool.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RegionTests {

    @Test
    public void creation(){
        Region region = Region.builder().name("서울").build();
        assertThat(region.getName()).isEqualTo("서울");
    }


}