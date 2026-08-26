package com.texops.app.services;

import com.texops.app.dao.WorkShiftRepository;
import com.texops.app.models.WorkShift;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WorkShiftService {

    //  -R        inyeccion de repositorio de turnos
    private final WorkShiftRepository workShiftRepository;

    public WorkShiftService(WorkShiftRepository workShiftRepository) {
        this.workShiftRepository = workShiftRepository;
    }

    //  -R        listar turnos por empleado
    public List<WorkShift> getShiftsByEmployee(Long employeeId) {
        return workShiftRepository.findByEmployeeId(employeeId);
    }

    //  -R        crear turno aplicando validaciones de tiempo
    public WorkShift createWorkShift(WorkShift workShift) {

        //  -R        validar orden logico de las horas
        if (!workShift.getEndTime().isAfter(workShift.getStartTime())) {
            throw new IllegalArgumentException("La hora de finalización debe ser mayor a la hora de inicio.");
        }

        //  -R        validar que no haya solapamiento
        boolean overlaps = workShiftRepository.hasOverlappingShifts(
                workShift.getEmployee().getId(),
                workShift.getStartTime(),
                workShift.getEndTime(),
                null
        );

        if (overlaps) {
            throw new IllegalStateException("Rechazo: El turno se solapa con uno previamente asignado a este empleado.");
        }

        return workShiftRepository.save(workShift);
    }

    // -R        actualizar un turno existente
    public WorkShift updateWorkShift(Long id, WorkShift shiftDetails) {
        return workShiftRepository.findById(id).map(existingShift -> {
            if (!shiftDetails.getEndTime().isAfter(shiftDetails.getStartTime())) {
                throw new IllegalArgumentException("La hora de finalización debe ser mayor a la hora de inicio.");
            }

            // -R        aqui pasamos el id del turno para excluirlo de la validacion
            boolean overlaps = workShiftRepository.hasOverlappingShifts(
                    existingShift.getEmployee().getId(),
                    shiftDetails.getStartTime(),
                    shiftDetails.getEndTime(),
                    id
            );

            if (overlaps) {
                throw new IllegalStateException("Rechazo: El horario modificado se solapa con otro turno.");
            }

            existingShift.setShiftName(shiftDetails.getShiftName());
            existingShift.setStartTime(shiftDetails.getStartTime());
            existingShift.setEndTime(shiftDetails.getEndTime());

            return workShiftRepository.save(existingShift);
        }).orElseThrow(() -> new IllegalArgumentException("Turno no encontrado."));
    }

    // -R        eliminar un turno
    public void deleteWorkShift(Long id) {
        if (!workShiftRepository.existsById(id)) {
            throw new IllegalArgumentException("Turno no encontrado.");
        }
        workShiftRepository.deleteById(id);
    }


}
