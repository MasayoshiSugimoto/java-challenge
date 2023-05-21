package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = new Logger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> retrieveEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public void saveEmployee(Employee employee){
        logger.info("saveEmployee({})", employee);
        employeeRepository.save(employee);
        logger.info("Employee {} saved.", employee.getId());
    }

    public boolean deleteEmployee(Long employeeId){
        logger.info("deleteEmployee({})", employeeId);
        try {
            employeeRepository.deleteById(employeeId);
            logger.info("Employee {} deleted successfully.", employeeId);
            return true;
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Employee {} no found.", employeeId);
            return false;  // Suppress error on deletion of unknown employee.
        }
    }

    public void updateEmployee(Employee employee) {
        logger.info("updateEmployee({})", employee);
        employeeRepository.save(employee);
        logger.info("Employee {} updated.", employee.getId());
    }
}