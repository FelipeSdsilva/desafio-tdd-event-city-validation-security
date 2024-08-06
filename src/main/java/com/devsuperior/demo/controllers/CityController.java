package com.devsuperior.demo.controllers;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.services.CityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping(value = "/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<CityDTO>> getAllCities() {
        return ResponseEntity.ok(cityService.findAllCities());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CityDTO> getCityPerId(@PathVariable Long id) {
        return ResponseEntity.ok(cityService.findCityPerId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CityDTO> postNewCity(@Valid @RequestBody CityDTO cityDTO) {
        CityDTO city = cityService.insertNewCity(cityDTO);
        URI uri = fromCurrentRequest().path("/{id}").buildAndExpand(city.getId()).toUri();
        return ResponseEntity.created(uri).body(city);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CityDTO> putCityPerId(@PathVariable Long id, @RequestBody CityDTO cityDTO) {
        return ResponseEntity.ok(cityService.updateCityPerId(id, cityDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCityPerId(@PathVariable Long id) {
        cityService.deleteCityPerId(id);
        return ResponseEntity.noContent().build();
    }
}
