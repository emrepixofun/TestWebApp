package com.emre.konumnot.controller;

import com.emre.konumnot.dto.LocationNoteRequest;
import com.emre.konumnot.dto.LocationNoteResponse;
import com.emre.konumnot.model.User;
import com.emre.konumnot.service.LocationNoteService;
import com.emre.konumnot.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/konumnot/api/notes")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class LocationNoteController {

    private final LocationNoteService locationNoteService;
    private final UserService userService;

    private static final String USER_ID_HEADER = "X-User-UUID";

    private User resolveUser(@RequestHeader(USER_ID_HEADER) String userUuid) {
        if (userUuid == null || userUuid.isBlank()) {
            throw new IllegalArgumentException("X-User-Id header zorunludur");
        }
        try {
            UUID uuid = UUID.fromString(userUuid.trim());
            return userService.getOrCreateByUuid(uuid);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Geçersiz X-User-Id (UUID formatında olmalı): " + userUuid);
        }
    }

    @PostMapping
    public ResponseEntity<LocationNoteResponse> createNote(
            @RequestHeader(USER_ID_HEADER) String userUuid,
            @Valid @RequestBody LocationNoteRequest request) {
        User user = resolveUser(userUuid);
        log.info("POST /konumnot/api/notes - Yeni not oluşturma isteği alındı, user: {}", user.getId());
        LocationNoteResponse response = locationNoteService.createNote(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<LocationNoteResponse>> getNotesNearby(
            @RequestHeader(USER_ID_HEADER) String userUuid,
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "1000") double radius) {
        User user = resolveUser(userUuid);
        log.info("GET /konumnot/api/notes/nearby - Yakındaki notlar: ({}, {}), radius: {}, user: {}",
                 latitude, longitude, radius, user.getId());
        List<LocationNoteResponse> notes = locationNoteService.getNotesNearLocation(user, latitude, longitude, radius);
        return ResponseEntity.ok(notes);
    }

    @GetMapping
    public ResponseEntity<List<LocationNoteResponse>> getAllNotes(
            @RequestHeader(USER_ID_HEADER) String userUuid) {
        User user = resolveUser(userUuid);
        log.info("GET /konumnot/api/notes - Tüm notlar getiriliyor, user: {}", user.getId());
        List<LocationNoteResponse> notes = locationNoteService.getAllNotes(user);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationNoteResponse> getNoteById(
            @RequestHeader(USER_ID_HEADER) String userUuid,
            @PathVariable Long id) {
        User user = resolveUser(userUuid);
        log.info("GET /konumnot/api/notes/{} - Not detayı getiriliyor", id);
        LocationNoteResponse note = locationNoteService.getNoteById(id, user);
        return ResponseEntity.ok(note);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(
            @RequestHeader(USER_ID_HEADER) String userUuid,
            @PathVariable Long id) {
        User user = resolveUser(userUuid);
        log.info("DELETE /konumnot/api/notes/{} - Not siliniyor", id);
        locationNoteService.deleteNote(id, user);
        return ResponseEntity.noContent().build();
    }
}
