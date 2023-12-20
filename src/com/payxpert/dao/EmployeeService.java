package com.payxpert.dao;

import com.payxpert.entity.Employee;
import com.payxpert.exception.DatabaseConnectionException;
import com.payxpert.exception.EmployeeNotFoundException;

import java.util.List;

public class EmployeeService implements IEmployeeService{
    public Employee getEmployeeById(int employeeId) {
            return DatabaseContext.getEmployeeFromDatabase(employeeId);
    }

    public List<Employee> getAllEmployees() throws DatabaseConnectionException {
        return DatabaseContext.getAllEmployees();
    }

    public void addEmployee(Employee employee) {
        try {
            if (DatabaseContext.addEmployee(employee) > 0) {
                System.out.println("Employee added successfully!");
            } else {
                System.out.println("Failed to add employee.");
            }
        } catch (DatabaseConnectionException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateEmployee(Employee employee) throws DatabaseConnectionException {
        int rowsAffected = DatabaseContext.updateEmployee(employee);
        if (rowsAffected > 0) {
            System.out.println("Employee updated successfully!");
        } else {
            System.out.println("Failed to update employee. Employee ID not found.");
        }
    }

    public void removeEmployee(int employeeId){
        DatabaseContext.removeEmployee(employeeId);
    }
}
