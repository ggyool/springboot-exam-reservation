package org.ggyool.eatgo.interfaces;

import com.sun.jndi.toolkit.url.Uri;
import com.sun.media.sound.DLSInstrument;
import org.ggyool.eatgo.application.RegionService;
import org.ggyool.eatgo.domain.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping("/regions")
    public List<Region> list(){
        return regionService.getRegions();
    }

    @PostMapping("/regions")
    public ResponseEntity<?> create(@RequestBody Region resource) throws URISyntaxException {
        Region region = regionService.addRegion(resource.getName());
        String url = "/regions/" + region.getId();
        URI location = new URI(url);
        return ResponseEntity.created(location).body("{}");
    }


}
