package com.payxpert.dao.service;

import com.payxpert.entity.Tax;
import com.payxpert.exception.EmployeeNotFoundException;
import com.payxpert.exception.TaxCalculationException;

import java.util.List;

public interface ITaxService {
    void calculateTax(int employeeId, int taxYear) throws EmployeeNotFoundException, TaxCalculationException;

    Tax getTaxById(int taxId);

    List<Tax> getTaxesForEmployee(int employeeId);

    List<Tax> getTaxesForYear(int taxYear);
}
