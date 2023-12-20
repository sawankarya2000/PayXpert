package com.payxpert.dao;

import com.payxpert.entity.FinancialRecord;
import com.payxpert.exception.EmployeeNotFoundException;
import com.payxpert.exception.FinancialRecordException;

import java.time.LocalDate;
import java.util.List;

public interface IFinancialRecordService {
    void addFinancialRecord(int employeeId, String description, double amount, String recordType) throws EmployeeNotFoundException, FinancialRecordException;

    FinancialRecord getFinancialRecordById(int recordId);

    List<FinancialRecord> getFinancialRecordsForEmployee(int employeeId);

    List<FinancialRecord> getFinancialRecordsForDate(LocalDate recordDate);
}
