package com.emre.konumnot.service;

import com.emre.konumnot.dto.LocationNoteRequest;
import com.emre.konumnot.dto.LocationNoteResponse;
import com.emre.konumnot.model.LocationNote;
import com.emre.konumnot.model.User;
import com.emre.konumnot.repository.LocationNoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationNoteService {

    private final LocationNoteRepository locationNoteRepository;
    private final UserService userService;
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    @Transactional
    public LocationNoteResponse createNote(LocationNoteRequest request, User user) {
        log.info("Yeni not oluşturuluyor: {} - Konum: ({}, {}), user: {}",
                 request.getTitle(), request.getLatitude(), request.getLongitude(), user.getId());

        LocationNote note = new LocationNote();
        note.setUser(user);
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        note.setLocation(createPoint(request.getLatitude(), request.getLongitude()));

        LocationNote savedNote = locationNoteRepository.save(note);
        log.info("Not başarıyla kaydedildi. ID: {}", savedNote.getId());

        return toResponse(savedNote);
    }

    @Transactional(readOnly = true)
    public List<LocationNoteResponse> getNotesNearLocation(User user, double latitude, double longitude, double radiusInMeters) {
        log.info("Konum ({}, {}) için {} metre yarıçapında notlar aranıyor, user: {}",
                 latitude, longitude, radiusInMeters, user.getId());

        List<LocationNote> notes = locationNoteRepository.findNotesNearLocation(user.getId(), latitude, longitude, radiusInMeters);

        log.info("{} adet not bulundu", notes.size());
        return notes.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LocationNoteResponse> getAllNotes(User user) {
        log.info("Kullanıcının tüm notları getiriliyor: {}", user.getId());
        List<LocationNote> notes = locationNoteRepository.findAllByUserId(user.getId());
        log.info("{} adet not bulundu", notes.size());

        return notes.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LocationNoteResponse getNoteById(Long id, User user) {
        log.info("ID ile not getiriliyor: {}, user: {}", id, user.getId());
        LocationNote note = locationNoteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Not bulunamadı: {}", id);
                    return new RuntimeException("Not bulunamadı: " + id);
                });
        if (note.getUser() == null || !note.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Not bulunamadı: " + id);
        }
        return toResponse(note);
    }

    @Transactional
    public void deleteNote(Long id, User user) {
        log.info("Not siliniyor: {}, user: {}", id, user.getId());
        LocationNote note = locationNoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not bulunamadı: " + id));
        if (note.getUser() == null || !note.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Not bulunamadı: " + id);
        }
        locationNoteRepository.deleteById(id);
        log.info("Not başarıyla silindi: {}", id);
    }

    private Point createPoint(double latitude, double longitude) {
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }

    private LocationNoteResponse toResponse(LocationNote note) {
        LocationNoteResponse response = new LocationNoteResponse();
        response.setId(note.getId());
        response.setTitle(note.getTitle());
        response.setContent(note.getContent());
        response.setLatitude(note.getLocation().getY());
        response.setLongitude(note.getLocation().getX());
        response.setCreatedAt(note.getCreatedAt());
        response.setUpdatedAt(note.getUpdatedAt());
        return response;
    }
}
