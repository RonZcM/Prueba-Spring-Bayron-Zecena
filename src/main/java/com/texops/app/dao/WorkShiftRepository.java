package com.texops.app.dao;

import com.texops.app.models.WorkShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository

public interface WorkShiftRepository extends  JpaRepository<WorkShift, Long> {

    //  -R        verificacion si un empleado tiene turnos asignados
    boolean existsByEmployeeId(Long employeeId);

    //  -R        lista de turnos de un empleado especifico
    List<WorkShift> findByEmployeeId(Long employeeId);

    //  -R        consulta para evitar el solapamiento de turnos de un empleado
    @Query("SELECT COUNT(w) > 0 FROM WorkShift w " +
            "WHERE w.employee.id = :employeeId " +
            "AND w.startTime < CAST(:endTime AS LocalTime) " +
            "AND w.endTime > CAST(:startTime AS LocalTime) " +
            "AND (:shiftId IS NULL OR w.id != :shiftId)")
    boolean hasOverlappingShifts(
            @Param("employeeId") Long employeeId,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("shiftId") Long shiftId
    );

}
