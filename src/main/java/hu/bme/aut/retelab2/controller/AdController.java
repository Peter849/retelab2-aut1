package hu.bme.aut.retelab2.controller;

import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.repository.AdRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/advertisement")
public class AdController {
    @Autowired
    private AdRepository advertisementRepository;

    @GetMapping("/searchByPrice")
    public List<Ad> searchByPrice(@RequestParam(required = false) Integer minimumPrice, @RequestParam(required = false) Integer maximumPrice) {
        if(minimumPrice == null)
        {
            minimumPrice = 0;
        }
        if(maximumPrice == null)
        {
            maximumPrice = 10000000;
        }

        List<Ad> results = advertisementRepository.findByPrice(minimumPrice, maximumPrice);

        for(Ad ad: results)
        {
            ad.setSecretCode(null);
        }

        return results;
    }

    @GetMapping("{tag}")
    public List<Ad> searchByTag(@PathVariable String tag) {
        return advertisementRepository.findBytag(tag);
    }

    @PostMapping
    public Ad create(@RequestBody Ad advertisement) {
        advertisement.setId(null);
        return advertisementRepository.save(advertisement);
    }

    @PutMapping
    public ResponseEntity<Ad> update(@RequestBody Ad advertisement) {
        advertisement = advertisementRepository.checkCodeAndSave(advertisement);
        if (advertisement == null)
        {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(advertisement);
    }
}
