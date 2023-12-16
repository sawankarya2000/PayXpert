package com.payxpert.dao.service;

import com.payxpert.entity.Employee;
import com.payxpert.exception.EmployeeNotFoundException;

import java.util.List;

public interface IEmployeeService {
    Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException;

    List<Employee> getAllEmployees();

    void addEmployee(Employee employeeData);

    void updateEmployee(Employee employeeData);

    void removeEmployee(int employeeId) throws EmployeeNotFoundException;
}
