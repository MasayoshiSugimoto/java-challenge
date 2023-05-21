package jp.co.axa.apidemo.controllers;

import io.swagger.annotations.ApiOperation;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import jp.co.axa.apidemo.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private static final Logger logger = new Logger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;


    @ApiOperation("Retrieve all employees.")
    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        logger.info("getEmployees()");
        List<Employee> employees = employeeService.retrieveEmployees();
        logger.trace("getEmployees() => {}", employees);  // Trace level because of data size.
        return employees;
    }

    @ApiOperation("Retrieve an employee from it's employee Id.")
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable(name="employeeId") Long employeeId) {
        logger.info("getEmployee({})", employeeId);
        Employee employee = employeeService.getEmployee(employeeId).orElse(null);
        logger.info("getEmployee({}) => {}", employeeId, employee);
        return employee;
    }

    @ApiOperation("Save a new employee.")
    @PostMapping("/employees")
    public void saveEmployee(@RequestBody Employee employee) {
        logger.info("saveEmployee({})", employee);
        employeeService.saveEmployee(employee);
        logger.info("Employee {} saved successfully.", employee.getName());
    }

    @ApiOperation("Delete an employee from his employee Id.")
    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(name="employeeId") Long employeeId) {
        logger.info("deleteEmployee({})", employeeId);
        if (employeeService.deleteEmployee(employeeId)) {
            logger.info("Employee {} deleted.", employeeId);
        } else {
            logger.info("Employee {} not found.", employeeId);
        }
    }

    @ApiOperation("Update an employee. Both `employeeId` and `employee.id` must be equal.")
    @PutMapping("/employees/{employeeId}")
    public void updateEmployee(@RequestBody Employee employee,
                               @PathVariable(name="employeeId") Long employeeId) {
        logger.info("updateEmployee({}, {})", employee, employeeId);
        if (employee.getId() != employeeId) {
            logger.warn("`employeeId` and `employee.id` must be same.");
            throw new IllegalArgumentException("`employeeId` and `employee.id` must be same.");
        }

        if (employeeService.updateEmployee(employee)) {
            logger.info("Employee {} updated.", employeeId);
        } else {
            logger.info("Failed to update employee {}.", employeeId);
        }
    }

}
