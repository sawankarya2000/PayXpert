package com.payxpert.dao;

import com.payxpert.entity.Employee;
import com.payxpert.entity.FinancialRecord;
import com.payxpert.entity.Gender;
import com.payxpert.exception.DatabaseConnectionException;
import com.payxpert.exception.FinancialRecordException;
import com.payxpert.util.DBUtil;
import com.payxpert.exception.EmployeeNotFoundException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseContext {

    private static Employee createEmployee(ResultSet resultSet) throws SQLException {
        // Create and return employee object
        int employeeID = resultSet.getInt("employeeID");
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        LocalDate dateOfBirth = resultSet.getDate("dateOfBirth").toLocalDate();
        Gender gender = Gender.valueOf(resultSet.getString("gender"));
        String email = resultSet.getString("email");
        String phoneNumber = resultSet.getString("phoneNumber");
        String address = resultSet.getString("address");
        String position = resultSet.getString("position");
        LocalDate joiningDate = resultSet.getDate("joiningDate").toLocalDate();
        LocalDate terminationDate = resultSet.getDate("terminationDate").toLocalDate();
        return new Employee(employeeID, firstName, lastName, dateOfBirth, gender, email, phoneNumber, address, position, joiningDate, terminationDate);
    }

    public static Employee getEmployeeFromDatabase(int employeeId){
        try(Connection conn = DBUtil.getDBConn()) {
            // Check if the employee with the given ID exists
            if (!employeeExists(conn, employeeId)) {
                throw new EmployeeNotFoundException("Employee not found with ID: " + employeeId);
            }

            String getEmployeeQuery = "SELECT * FROM employees WHERE employeeID = ?";

            PreparedStatement ps = conn.prepareStatement(getEmployeeQuery);
            ps.setInt(1, employeeId);

            ResultSet resultSet = ps.executeQuery();
            Employee employee = null;

            if(resultSet.next()) {
                employee = createEmployee(resultSet);
            }
            return employee;
        } catch(EmployeeNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Employee> getAllEmployees() throws DatabaseConnectionException {
        try(Connection conn = DBUtil.getDBConn()) {
            String getAllEmployeeQuery = "SELECT * FROM employees";

            Statement statement = conn.createStatement();
            //Execute the query
            ResultSet resultSet = statement.executeQuery(getAllEmployeeQuery);

            List<Employee> employees = new ArrayList<>();

            while(resultSet.next()) {
                employees.add(createEmployee(resultSet));
            }
            return employees;
        } catch(SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    public static int addEmployee(Employee employee) throws DatabaseConnectionException {
        try (Connection conn = DBUtil.getDBConn()) {
            // Create the SQL query
            String sql = "INSERT INTO employees (firstName, lastName, dateOfBirth, gender, email, phoneNumber, address, position, joiningDate, terminationDate) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                // Set the values for the parameters
                preparedStatement.setString(1, employee.getFirstName());
                preparedStatement.setString(2, employee.getLastName());
                preparedStatement.setDate(3, java.sql.Date.valueOf(employee.getDateOfBirth()));
                preparedStatement.setString(4, employee.getGender().toString());
                preparedStatement.setString(5, employee.getEmail());
                preparedStatement.setString(6, employee.getPhoneNumber());
                preparedStatement.setString(7, employee.getAddress());
                preparedStatement.setString(8, employee.getPosition());
                preparedStatement.setDate(9, Date.valueOf(employee.getJoiningDate()));
                preparedStatement.setDate(10, Date.valueOf(employee.getTerminationDate()));

                // Execute the query
                return preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    public static int updateEmployee(Employee employee) throws DatabaseConnectionException {
        try (Connection conn = DBUtil.getDBConn()) {
            // Check if the employee with the given ID exists
            if (!employeeExists(conn, employee.getEmployeeID())) {
                throw new EmployeeNotFoundException();
            }

            // Create the SQL query with parameters
            String sql = "UPDATE employees SET firstName=?, lastName=?, dateOfBirth=?, gender=?, email=?, phoneNumber=?, address=?, position=?, joiningDate=?, terminationDate=? WHERE employeeID=?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                // Set the values for the parameters
                preparedStatement.setString(1, employee.getFirstName());
                preparedStatement.setString(2, employee.getLastName());
                preparedStatement.setDate(3, java.sql.Date.valueOf(employee.getDateOfBirth()));
                preparedStatement.setString(4, employee.getGender().toString());
                preparedStatement.setString(5, employee.getEmail());
                preparedStatement.setString(6, employee.getPhoneNumber());
                preparedStatement.setString(7, employee.getAddress());
                preparedStatement.setString(8, employee.getPosition());
                preparedStatement.setDate(9, java.sql.Date.valueOf(employee.getJoiningDate()));
                preparedStatement.setDate(10, java.sql.Date.valueOf(employee.getTerminationDate()));
                preparedStatement.setInt(11, employee.getEmployeeID());

                // Execute the query
                return preparedStatement.executeUpdate();

            }
        } catch (EmployeeNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            throw new DatabaseConnectionException();
        }
        return -1;
    }

    public static void removeEmployee(int employeeId) {
        try (Connection conn = DBUtil.getDBConn()) {
            // Check if the employee with the given ID exists
            if (!employeeExists(conn, employeeId)) {
                throw new EmployeeNotFoundException("Employee not found with ID: " + employeeId);
            }

            // Create the SQL query
            String sql = "DELETE FROM employees WHERE employeeID=?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

                preparedStatement.setInt(1, employeeId);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Employee removed successfully!");
                } else {
                    System.out.println("Failed to remove employee. Employee ID not found.");
                }
            }
        } catch(EmployeeNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to check if employee Exists or not
    private static boolean employeeExists(Connection conn, int employeeId) throws SQLException {
        String checkQuery = "SELECT COUNT(*) FROM employees WHERE employeeID=?";
        try (PreparedStatement checkStatement = conn.prepareStatement(checkQuery)) {
            checkStatement.setInt(1, employeeId);
            var resultSet = checkStatement.executeQuery();
            return resultSet.next();
        }
    }

    public static boolean employeeExists(int employeeId) throws DatabaseConnectionException {
        String checkQuery = "SELECT COUNT(*) FROM employees WHERE employeeID=?";
        try(Connection conn = DBUtil.getDBConn()) {
            try (PreparedStatement checkStatement = conn.prepareStatement(checkQuery)) {
                checkStatement.setInt(1, employeeId);
                var resultSet = checkStatement.executeQuery();
                return resultSet.next();
            }
        } catch(SQLException e) {
            throw new DatabaseConnectionException();
        }
    }

    public static void addFinancialRecord(int employeeId, String description, double amount, String recordType) throws DatabaseConnectionException {
        // Sql query to insert financial records
        String sql = "INSERT INTO financial_records (employeeId, description, amount, recordType, recordDate) VALUES (?, ?, ?, ?, ?)";
        try(Connection conn = DBUtil.getDBConn()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, employeeId);
                preparedStatement.setString(2, description);
                preparedStatement.setDouble(3, amount);
                preparedStatement.setString(4, recordType);
                preparedStatement.setDate(5, Date.valueOf(LocalDate.now()));

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Financial record added successfully!");
                } else {
                    throw new FinancialRecordException("Failed to add financial record.");
                }
            }
        } catch (FinancialRecordException e) {
            System.out.println(e.getMessage());
        }
        catch (SQLException e) {
            throw new DatabaseConnectionException();
        }
    }

    public static FinancialRecord getFinancialRecordById(int recordId) throws DatabaseConnectionException {
        try (Connection conn = DBUtil.getDBConn()) {
            // SQL query to retrieve data
            String sql = "SELECT * FROM financial_records WHERE recordId = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, recordId);

                // Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        return createFinancialRecord(resultSet);
                    }
            }catch (SQLException e) {
                throw new FinancialRecordException();
            }
        } catch (FinancialRecordException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            throw new DatabaseConnectionException();
        }

        // Return null if any error occurs
        return null;
    }

    public static List<FinancialRecord> getFinancialRecordsForEmployee(int employeeId) throws DatabaseConnectionException {
        List<FinancialRecord> records = new ArrayList<>();

        try (Connection conn = DBUtil.getDBConn()) {
            // Create the SQL query
            String sql = "SELECT * FROM financial_records WHERE employeeId = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, employeeId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Create a FinancialRecord object from each row and add it to the list
                        FinancialRecord financialRecord = createFinancialRecord(resultSet);
                        records.add(financialRecord);
                    }
                }
            } catch (SQLException e) {
                throw new FinancialRecordException();
            }
        } catch(FinancialRecordException e) {
            System.out.println(e.getMessage());
        }catch (SQLException e) {
            throw new DatabaseConnectionException();
        }

        return records;
    }

    public static List<FinancialRecord> getFinancialRecordsForDate(LocalDate recordDate) throws DatabaseConnectionException {
        List<FinancialRecord> records = new ArrayList<>();

        try (Connection conn = DBUtil.getDBConn()) {
            // Create the SQL query
            String sql = "SELECT * FROM financial_records WHERE recordDate = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setDate(1, Date.valueOf(recordDate));

                // Execute the query
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Create a FinancialRecord object from each row and add it to the list
                        FinancialRecord financialRecord = createFinancialRecord(resultSet);
                        records.add(financialRecord);
                    }
                }
            } catch (SQLException e) {
                throw new FinancialRecordException();
            }
        } catch(FinancialRecordException e) {
            System.out.println(e.getMessage());
        }catch (SQLException e) {
            throw new DatabaseConnectionException();
        }

        return records;
    }
    public static FinancialRecord createFinancialRecord(ResultSet resultSet) throws SQLException {
        return new FinancialRecord(
                resultSet.getInt("recordId"),
                resultSet.getInt("employeeId"),
                resultSet.getDate("recordDate").toLocalDate(),
                resultSet.getString("description"),
                resultSet.getDouble("amount"),
                resultSet.getString("recordType")
        );
    }
}
