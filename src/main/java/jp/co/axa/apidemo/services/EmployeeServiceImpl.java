package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> retrieveEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    public Optional<Employee> getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public void saveEmployee(Employee employee){
        employeeRepository.save(employee);
    }

    public boolean deleteEmployee(Long employeeId){
        try {
            employeeRepository.deleteById(employeeId);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;  // Suppress error on deletion of unknown employee.
        }
    }

    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
}