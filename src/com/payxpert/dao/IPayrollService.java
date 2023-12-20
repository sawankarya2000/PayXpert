package com.payxpert.dao;

import com.payxpert.entity.Payroll;
import com.payxpert.exception.EmployeeNotFoundException;
import com.payxpert.exception.PayrollGenerationException;

import java.time.LocalDate;
import java.util.List;

public interface IPayrollService {
    void generatePayroll(int employeeId, LocalDate startDate, LocalDate endDate) throws EmployeeNotFoundException, PayrollGenerationException;

    Payroll getPayrollById(int payrollId);

    List<Payroll> getPayrollsForEmployee(int employeeId);

    List<Payroll> getPayrollsForPeriod(LocalDate startDate, LocalDate endDate);
}
