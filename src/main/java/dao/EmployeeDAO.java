package dao;

import model.Employee;

import java.util.List;

public interface EmployeeDAO {
    void addEmployee(Employee employee);

    Employee findById1(Integer id);

    List<Employee> fullFindByEmployee();

    void toChange(Employee employee);

    void deleteById(Integer id);
}
