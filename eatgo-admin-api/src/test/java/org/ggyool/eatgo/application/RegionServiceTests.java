package org.ggyool.eatgo.application;

import org.ggyool.eatgo.domain.Region;
import org.ggyool.eatgo.domain.RegionRepository;
import org.ggyool.eatgo.domain.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class RegionServiceTests {

    private RegionService regionService;

    @Mock
    private RegionRepository regionRepository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        regionService = new RegionService(regionRepository);
    }

    @Test
    public void getRegions(){
        List<Region> mockRegions = new ArrayList<>();
        mockRegions.add(Region.builder().name("seoul").build());

        given(regionRepository.findAll()).willReturn(mockRegions);

        List<Region> regions = regionService.getRegions();
        assertThat(regions.get(0).getName()).isEqualTo("seoul");
    }

    @Test
    public void addRegion(){
        given(regionRepository.save(any())).will(invocation->{
            Region region = invocation.getArgument(0);
            return Region.builder()
                    .name(region.getName())
                    .build();
        });
        Region region = regionService.addRegion("seoul");
        verify(regionRepository).save(any());
        assertThat(region.getName()).isEqualTo("seoul");
    }
}