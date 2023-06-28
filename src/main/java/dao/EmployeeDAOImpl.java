package dao;

import model.City;
import model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    private static final String USER = "postgres";
    private static final String PASSWORD = "last1379";
    private static final String URL = "jdbc:postgresql://localhost:5432/skypro";

    public static Connection connection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addEmployee(Employee employee) {
        try (PreparedStatement preparedStatement = connection().prepareStatement("INSERT INTO employee(first_name,last_name,gender,age,city_id) VALUES ( ?, ?, ?, ?, ?)")) {
            employee = new Employee(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getGender(), employee.getAge(), employee.getCity());
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getGender());
            preparedStatement.setInt(4, employee.getAge());
            preparedStatement.setInt(5, employee.getCity().getCityId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee findById1(Integer id) {
        Employee employee = new Employee();
        try (PreparedStatement preparedStatement = connection().prepareStatement("SELECT * FROM employee INNER JOIN city ON city.city_id = employee.city_id WHERE id = (?)")) {
            preparedStatement.setInt(1, id);
            preparedStatement.setMaxRows(1);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return employee.printEmployee(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> fullFindByEmployee() {
        List<Employee> employeeList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection().prepareStatement("SELECT * FROM employee INNER JOIN city ON city.city_id = employee.city_id")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employeeList.add(Employee.printEmployee(resultSet));
            }
            return employeeList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void toChange(Employee employee) {
        try (PreparedStatement preparedStatement = connection().prepareStatement("UPDATE employee SET first_name = (?),last_name= (?),gender= (?),age= (?),city_id= (?)   WHERE id = (?)")) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getGender());
            preparedStatement.setInt(4, employee.getAge());
            preparedStatement.setInt(5, employee.getCity().getCityId());
            preparedStatement.setInt(6, employee.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (PreparedStatement preparedStatement = connection().prepareStatement("DELETE  FROM employee WHERE id = (?)")) {
            Employee employee = new Employee();
            preparedStatement.setInt(1, id);
            preparedStatement.setMaxRows(1);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
