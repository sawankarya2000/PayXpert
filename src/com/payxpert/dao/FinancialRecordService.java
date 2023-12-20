package com.payxpert.dao;

import com.payxpert.entity.FinancialRecord;
import com.payxpert.exception.DatabaseConnectionException;
import com.payxpert.exception.EmployeeNotFoundException;
import com.payxpert.exception.FinancialRecordException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FinancialRecordService implements IFinancialRecordService {

    @Override
    public void addFinancialRecord(int employeeId, String description, double amount, String recordType) {
        try {
            if (!DatabaseContext.employeeExists(employeeId)) {
                throw new EmployeeNotFoundException("Employee not found with ID: " + employeeId);
            }

            // Create a new financial record
            DatabaseContext.addFinancialRecord(employeeId, description, amount, recordType);

        } catch(EmployeeNotFoundException | DatabaseConnectionException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public FinancialRecord getFinancialRecordById(int recordId) {

        try{
            return DatabaseContext.getFinancialRecordById(recordId);
        } catch (DatabaseConnectionException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<FinancialRecord> getFinancialRecordsForEmployee(int employeeId) {
        // Return a list of financial records for the specified employee
        try {
            return DatabaseContext.getFinancialRecordsForEmployee(employeeId);
        } catch (DatabaseConnectionException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<FinancialRecord> getFinancialRecordsForDate(LocalDate recordDate) {
        // Return a list of financial records for the specified date
        try {
            return DatabaseContext.getFinancialRecordsForDate(recordDate);
        } catch (DatabaseConnectionException e) {
            System.out.println(e.getMessage());
        }
    }
}
