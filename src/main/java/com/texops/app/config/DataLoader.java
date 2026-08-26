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

                Employee emp3 = new Employee();
                emp3.setCode("E104");
                emp3.setName("Luis Ramirez");
                emp3.setActive(true);

                Employee emp4 = new Employee();
                emp4.setCode("E105");
                emp4.setName("Ana Valle");
                emp4.setActive(true);

                Employee emp5 = new Employee();
                emp5.setCode("E106");
                emp5.setName("Jorge Santos");
                emp5.setActive(true);

                employeeService.createEmployee(emp1);
                employeeService.createEmployee(emp2);
                employeeService.createEmployee(emp3);
                employeeService.createEmployee(emp4);
                employeeService.createEmployee(emp5);

                // -R        creacion de turnos para el primer empleado con dos turnos
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

                // -R        creacion de turnos para el segundo empleado con dos turnos
                WorkShift shift3 = new WorkShift();
                shift3.setShiftName("Matutino");
                shift3.setStartTime(LocalTime.of(7, 0));
                shift3.setEndTime(LocalTime.of(15, 0));
                shift3.setEmployee(emp2);

                WorkShift shift4 = new WorkShift();
                shift4.setShiftName("Nocturno");
                shift4.setStartTime(LocalTime.of(18, 0));
                shift4.setEndTime(LocalTime.of(22, 0));
                shift4.setEmployee(emp2);

                workShiftService.createWorkShift(shift3);
                workShiftService.createWorkShift(shift4);

                // -R        creacion de turno para el tercer empleado con un turno
                WorkShift shift5 = new WorkShift();
                shift5.setShiftName("Día completo");
                shift5.setStartTime(LocalTime.of(8, 0));
                shift5.setEndTime(LocalTime.of(16, 0));
                shift5.setEmployee(emp3);

                workShiftService.createWorkShift(shift5);

                // -R        creacion de turno para el cuarto empleado con un turno
                WorkShift shift6 = new WorkShift();
                shift6.setShiftName("Medio tiempo");
                shift6.setStartTime(LocalTime.of(13, 0));
                shift6.setEndTime(LocalTime.of(17, 0));
                shift6.setEmployee(emp4);

                workShiftService.createWorkShift(shift6);

                // -R        el quinto empleado se queda sin turnos para pruebas

                System.out.println("Base de datos inicializada con éxito.");
            } else {
                System.out.println("Datos de prueba ya existentes, omitiendo inicialización.");
            }
        };
    }
}