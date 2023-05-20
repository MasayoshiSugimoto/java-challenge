package jp.co.axa.apidemo.controllers;

import io.swagger.annotations.ApiOperation;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;


    @ApiOperation("Retrieve all employees.")
    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        List<Employee> employees = employeeService.retrieveEmployees();
        return employees;
    }

    @ApiOperation("Retrieve an employee from it's employee Id.")
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable(name="employeeId") Long employeeId) {
        return employeeService.getEmployee(employeeId).orElse(null);
    }

    @ApiOperation("Save a new employee.")
    @PostMapping("/employees")
    public void saveEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        System.out.println("Employee Saved Successfully");
    }

    @ApiOperation("Delete an employee from his employee Id.")
    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(name="employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        System.out.println("Employee Deleted Successfully");
    }

    @ApiOperation("Update an employee. Both `employeeId` and `employee.id` must be equal.")
    @PutMapping("/employees/{employeeId}")
    public void updateEmployee(@RequestBody Employee employee,
                               @PathVariable(name="employeeId") Long employeeId) {
        if (employee.getId() != employeeId) {
            throw new IllegalArgumentException("`employeeId` and `employee.id` must be same.");
        }

        Optional<Employee> maybeEmployee = employeeService.getEmployee(employeeId);
        if (maybeEmployee.isPresent()) {
            employeeService.updateEmployee(employee);
        }
    }

}
