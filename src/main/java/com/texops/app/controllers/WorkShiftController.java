package com.texops.app.controllers;

import com.texops.app.models.WorkShift;
import com.texops.app.services.WorkShiftService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/workshifts")
public class WorkShiftController {

    // -R        inyeccion del servicio
    private final WorkShiftService workShiftService;

    public WorkShiftController(WorkShiftService workShiftService) {
        this.workShiftService = workShiftService;
    }

    // -R        listar los turnos de un empleado
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<WorkShift>> getShiftsByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(workShiftService.getShiftsByEmployee(employeeId));
    }

    // -r        crear un turno validando solapamientos
    @PostMapping
    public ResponseEntity<?> createWorkShift(@Valid @RequestBody WorkShift workShift) {
        try {
            WorkShift newWorkShift = workShiftService.createWorkShift(workShift);
            return new ResponseEntity<>(newWorkShift, HttpStatus.CREATED);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // -R        editar un turno existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkShift(@PathVariable Long id, @Valid @RequestBody WorkShift shiftDetails) {
        try {
            WorkShift updatedShift = workShiftService.updateWorkShift(id, shiftDetails);
            return ResponseEntity.ok(updatedShift);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // -R        eliminar un turno
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkShift(@PathVariable Long id) {
        try {
            workShiftService.deleteWorkShift(id);
            // -R        devolvemos 204 no content que es el estandar para eliminaciones exitosas
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}