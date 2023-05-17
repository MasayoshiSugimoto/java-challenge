package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> retrieveEmployees();

    Optional<Employee> getEmployee(Long employeeId);

    void saveEmployee(Employee employee);

    boolean deleteEmployee(Long employeeId);

    void updateEmployee(Employee employee);
}