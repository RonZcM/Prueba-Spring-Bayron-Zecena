package com.texops.app.config;

import com.texops.app.models.Employee;
import com.texops.app.models.WorkShift;
import com.texops.app.services.EmployeeService;
import com.texops.app.services.WorkShiftService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;

@Configuration
public class DataLoader {

    // -R        carga de datos iniciales requeridos por la prueba
    @Bean
    CommandLineRunner initDatabase(EmployeeService employeeService, WorkShiftService workShiftService) {
        return args -> {

            // -R        solo insertamos si no hay empleados para evitar errores por duplicados
            if (employeeService.getAllEmployees().isEmpty()) {

                // -R        creacion de empleados
                Employee emp1 = new Employee();
                emp1.setCode("E102");
                emp1.setName("Carlos Perez");
                emp1.setActive(true);

                Employee emp2 = new Employee();
                emp2.setCode("E103");
                emp2.setName("Maria Gomez");
                emp2.setActive(true);

                employeeService.createEmployee(emp1);
                employeeService.createEmployee(emp2);

                // -R        creacion de turnos sin solapamiento
                WorkShift shift1 = new WorkShift();
                shift1.setShiftName("Mañana");
                shift1.setStartTime(LocalTime.of(8, 0));
                shift1.setEndTime(LocalTime.of(12, 0));
                shift1.setEmployee(emp1);

                WorkShift shift2 = new WorkShift();
                shift2.setShiftName("Tarde");
                shift2.setStartTime(LocalTime.of(13, 0));
                shift2.setEndTime(LocalTime.of(17, 0));
                shift2.setEmployee(emp1);

                workShiftService.createWorkShift(shift1);
                workShiftService.createWorkShift(shift2);

                System.out.println("Base de datos inicializada con éxito.");
            } else {
                System.out.println("Datos de prueba ya existentes, omitiendo inicialización.");
            }
        };
    }
}