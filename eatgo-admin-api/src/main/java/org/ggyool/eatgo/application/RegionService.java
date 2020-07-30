package org.ggyool.eatgo.application;

import org.ggyool.eatgo.domain.Region;
import org.ggyool.eatgo.domain.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {

    private RegionRepository regionRepository;

    @Autowired
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Region> getRegions() {
        return regionRepository.findAll();
    }

    public Region addRegion(String name) {
        return regionRepository.save(Region.builder().name(name).build());
    }
}
