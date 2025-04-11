package com.carsmoviesinventory.app.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.carsmoviesinventory.app.Entities.ChistesYFrasesEntity;
import com.carsmoviesinventory.app.Repositories.ChistesYFrasesRepository;


import java.util.*;
@Service

public class ChistesYFrasesService {

    private final ChistesYFrasesRepository chistesYFrasesRepository;
    @Autowired
    public ChistesYFrasesService(ChistesYFrasesRepository chistesYFrasesRepository) {

        this.chistesYFrasesRepository = chistesYFrasesRepository;
    }

    public ResponseEntity<?> getAllchistesYFrases(Pageable pageable) {
        Page<ChistesYFrasesEntity> ChistesYFrases = chistesYFrasesRepository.findAll(pageable);
        return getResponseEntity(ChistesYFrases);
    }

    public ResponseEntity<?> getChistesYFrasesById(UUID id) {
        Optional<ChistesYFrasesEntity> ChistesYFrases = chistesYFrasesRepository.findById(id);
        if (ChistesYFrases.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("Status", String.format("Chiste o frase %s no encontrado", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(Collections.singletonMap("Chiste", ChistesYFrases.get()));
    }

    public ResponseEntity<?> getChistesYFrasesByChiste(String chiste, Pageable pageable) {
        Page<ChistesYFrasesEntity> Chiste = chistesYFrasesRepository.findAllByChisteOFraseContaining(chiste, pageable);
        return getResponseEntity(Chiste);
    }

    private ResponseEntity<?> getResponseEntity(Page<ChistesYFrasesEntity> chistesYFrases)
    {
        Map<String, Object> response = new HashMap<>();
        response.put("TotalElements", chistesYFrases.getTotalElements());
        response.put("TotalPages", chistesYFrases.getTotalPages());
        response.put("CurrentPage", chistesYFrases.getNumber());
        response.put("NumberOfElements", chistesYFrases.getNumberOfElements());
        response.put("Chistes", chistesYFrases.getContent());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> addChiste(ChistesYFrasesEntity chistesYFrasesToAdd) {
        Page<ChistesYFrasesEntity> chiste = chistesYFrasesRepository.findAllByChisteOFraseContaining(
                chistesYFrasesToAdd.getChisteOFrase(),
                Pageable.unpaged());
        if (chiste.getTotalElements() > 0) {
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Este chiste ya existe.", chiste.getTotalElements())), HttpStatus.CONFLICT);
        } else {
            ChistesYFrasesEntity savedchiste = chistesYFrasesRepository.save(chistesYFrasesToAdd);
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Chiste añadido con ID %s", savedchiste.getId())), HttpStatus.CREATED);
        }

    }

    public ResponseEntity<?> updateChiste(UUID id, ChistesYFrasesEntity chistesYFrasesToUpdate) {
        Optional<ChistesYFrasesEntity> chiste = chistesYFrasesRepository.findById(id);
        if (chiste.isEmpty()) {
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("Chiste con Id %s no encontrado", id)), HttpStatus.NOT_FOUND);
        }
        ChistesYFrasesEntity existingChiste = chiste.get();

        existingChiste.setChisteOFrase(chistesYFrasesToUpdate.getChisteOFrase());

        chistesYFrasesRepository.save(existingChiste);
        return ResponseEntity.ok(Collections.singletonMap("Status", String.format("Chiste actualizado con Id %s", existingChiste.getId())));
    }

    public ResponseEntity<?> deleteChiste(UUID id) {
        Optional<ChistesYFrasesEntity> movie = chistesYFrasesRepository.findById(id);
        if (movie.isEmpty()) {
            return new ResponseEntity<>(Collections.singletonMap("Status", String.format("La pelicula con ID %s no existe", id)), HttpStatus.NOT_FOUND);
        }
        chistesYFrasesRepository.deleteById(id);
        return ResponseEntity.ok(Collections.singletonMap("Status", String.format("Chiste eliminado con ID %s", id)));
    }
}
