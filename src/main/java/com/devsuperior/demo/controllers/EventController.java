package com.devsuperior.demo.controllers;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.services.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping(value = "/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<Page<EventDTO>> getAllEvents(Pageable pageable) {
        return ResponseEntity.ok(eventService.findAllEvents(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EventDTO> getEventPerId(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.findEventPerId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENT')")
    public ResponseEntity<EventDTO> postNewEvent(@Valid @RequestBody EventDTO eventDTO) {
        EventDTO event = eventService.insertNewEvent(eventDTO);
        URI uri = fromCurrentRequest().path("/{id}").buildAndExpand(event.getId()).toUri();
        return ResponseEntity.created(uri).body(event);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EventDTO> putEventPerId(@PathVariable Long id, @RequestBody EventDTO eventDTO) {
        return ResponseEntity.ok(eventService.updateEventPerId(id, eventDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteEventPerId(@PathVariable Long id) {
        eventService.deleteEventPerId(id);
        return ResponseEntity.noContent().build();
    }
}
