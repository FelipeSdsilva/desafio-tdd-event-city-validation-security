package com.devsuperior.demo.services;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.services.exceptions.DatabaseException;
import com.devsuperior.demo.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;


    @Transactional(readOnly = true)
    public List<CityDTO> findAllCities() {
        return cityRepository.listCitiesOrdPerName();
    }

    @Transactional(readOnly = true)
    public CityDTO findCityPerId(Long id) {
        return new CityDTO(cityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found!")));
    }

    @Transactional
    public CityDTO insertNewCity(CityDTO cityDTO) {
        City city = new City();
        city.setName(cityDTO.getName());
        city = cityRepository.save(city);
        return new CityDTO(city);
    }

    @Transactional
    public CityDTO updateCityPerId(Long id, CityDTO cityDTO) {
        try {
            City city = cityRepository.getReferenceById(id);
            city.setName(cityDTO.getName());
            city = cityRepository.save(city);
            return new CityDTO(city);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found!");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteCityPerId(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found!");
        }
        try {
            cityRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrate violation!");
        }
    }

}
