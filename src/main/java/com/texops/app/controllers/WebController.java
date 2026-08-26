package com.texops.app.controllers;

import com.texops.app.models.Employee;
import com.texops.app.services.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {

    // -R        inyeccion de servicio mediante constructor
    private final EmployeeService employeeService;

    public WebController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // -R        ruta principal que carga la vista de empleados
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "index";
    }

    // -R        vista para crear o editar empleado
    @GetMapping("/empleados/formulario")
    public String employeeForm() {
        return "employee-form";
    }

    // -R        vista para gestionar los turnos de un empleado
    @GetMapping("/empleados/{id}/turnos")
    public String employeeShifts(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employeeId", id);
        model.addAttribute("employeeName", employee.getName());
        return "shifts";
    }
}