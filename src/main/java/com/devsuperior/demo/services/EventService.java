package com.devsuperior.demo.services;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.entities.Event;
import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.repositories.EventRepository;
import com.devsuperior.demo.services.exceptions.DatabaseException;
import com.devsuperior.demo.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CityRepository cityRepository;

    @Transactional(readOnly = true)
    public Page<EventDTO> findAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable).map(EventDTO::new);
    }

    @Transactional(readOnly = true)
    public EventDTO findEventPerId(Long id) {
        return new EventDTO(eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found!")));
    }

    @Transactional
    public EventDTO insertNewEvent(EventDTO eventDTO) {
        Event event = new Event();
        convertDtoInEntity(event, eventDTO);
        event = eventRepository.save(event);
        return new EventDTO(event);
    }

    @Transactional
    public EventDTO updateEventPerId(Long id, EventDTO eventDTO) {
        try {
            Event event = eventRepository.getReferenceById(id);
            convertDtoInEntity(event, eventDTO);
            event = eventRepository.save(event);
            return new EventDTO(event);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found!");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteEventPerId(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found!");
        }
        try {
            eventRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrate violation!");
        }

    }

    private void convertDtoInEntity(Event event, EventDTO eventDTO) {
        event.setName(eventDTO.getName());
        event.setDate(eventDTO.getDate());
        event.setUrl(eventDTO.getUrl());

        City city = cityRepository.getReferenceById(eventDTO.getCityId());
        event.setCity(city);
    }

}
