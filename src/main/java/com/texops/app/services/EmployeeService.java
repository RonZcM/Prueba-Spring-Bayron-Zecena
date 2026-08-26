package com.texops.app.services;


import com.texops.app.dao.EmployeeRepository;
import com.texops.app.dao.WorkShiftRepository;
import com.texops.app.models.Employee;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {

    //  -R        inyeccion de repositorios
    private final EmployeeRepository employeeRepository;
    private final WorkShiftRepository workShiftRepository;

    public EmployeeService(EmployeeRepository employeeRepository, WorkShiftRepository workShiftRepository) {
        this.employeeRepository = employeeRepository;
        this.workShiftRepository = workShiftRepository;
    }

    //  -R        listar todos los empleados
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    //  -R        crear empleado validando codigo unico para evitar repetidos
    public Employee createEmployee(Employee employee) {
        if (employeeRepository.existsByCode(employee.getCode())) {
            throw new IllegalArgumentException("Error: El código de empleado ya está en uso.");
        }
        return employeeRepository.save(employee);
    }

    //  -R        eliminar empleado validando que no tenga turnos asociados
    public void deleteEmployee(Long id) {
        if (workShiftRepository.existsByEmployeeId(id)) {
            throw new IllegalStateException("Rechazo: No se puede eliminar el empleado porque tiene turnos asociados.");
        }
        employeeRepository.deleteById(id);
    }

    // -R        actualizar un empleado existente
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        return employeeRepository.findById(id).map(employee -> {
            // -R        solo actualizamos si el codigo nuevo es diferente y no existe ya
            if (!employee.getCode().equals(employeeDetails.getCode()) &&
                    employeeRepository.existsByCode(employeeDetails.getCode())) {
                throw new IllegalArgumentException("Error: El nuevo código ya está en uso.");
            }
            employee.setCode(employeeDetails.getCode());
            employee.setName(employeeDetails.getName());
            employee.setActive(employeeDetails.getActive());
            return employeeRepository.save(employee);
        }).orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado."));
    }

    // -R        cambiar el estado activo o inactivo de un empleado
    public Employee toggleEmployeeStatus(Long id) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setActive(!employee.getActive());
            return employeeRepository.save(employee);
        }).orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado."));
    }

    // -R        obtener un empleado por su id
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Error: El empleado con ID " + id + " no existe."));
    }

}
