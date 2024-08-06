package com.devsuperior.demo.repositories;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query(name = "listCitiesOrdPerName")
    List<CityDTO> listCitiesOrdPerName();
}
