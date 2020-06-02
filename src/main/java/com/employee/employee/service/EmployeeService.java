package com.employee.employee.service;

import com.employee.employee.model.Employee;
import com.employee.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
  private EmployeeRepository employeeRepository;

  @Autowired
  public EmployeeService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  public List<Employee> getAllEmployees() {
    return employeeRepository.findAll();
  }

  public void createEmployee(Employee employee) {
    employeeRepository.save(employee);
  }

  public Employee getEmployeeById(Long id) {
    Optional<Employee> optionalEmployee = employeeRepository.findById(id);
    Employee employee = null;
    if (optionalEmployee.isPresent()) {
      employee = optionalEmployee.get();
    } else {
      throw new RuntimeException("Employee's Id is Not Valid => " + id);
    }
    return employee;
  }

  public void deleteEmployee(Long id) {
    employeeRepository.deleteById(id);
  }

  public Page<Employee> findAllWithPagination(int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
    return employeeRepository.findAll(pageable);
  }
}
