package com.payxpert.dao;

import com.payxpert.entity.Employee;
import com.payxpert.exception.DatabaseConnectionException;
import com.payxpert.exception.EmployeeNotFoundException;

import java.util.List;

public interface IEmployeeService {
    Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException;

    List<Employee> getAllEmployees() throws DatabaseConnectionException;

    void addEmployee(Employee employeeData) throws DatabaseConnectionException;

    void updateEmployee(Employee employee) throws DatabaseConnectionException;

    void removeEmployee(int employeeId) throws EmployeeNotFoundException;
}
