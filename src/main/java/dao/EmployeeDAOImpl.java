package dao;


import model.Employee;
import org.hibernate.Session;

import org.hibernate.Transaction;
import util.HibernateSessionFactoryUtil;

import java.sql.*;

import java.util.List;

//Добавьте зависимость hibernate-core  версии 5.6.14.Final  в файл pom.xml.
// Замените в классе Employee  поле city  с типа City  на тип int .
// Приведите класс Employee  ко всем критериям Entity. Используйте все необходимые аннотации.
//Создайте конфигурационный файл persistence.xml в каталоге src/main/resources/META-INF.
//Реализуйте класс со статическим методом чтения конфигурационного файла.
//Скорректируйте в интерфейсе EmployeeDAO  методы удаления и изменения, они должны принимать объект типа Employee.
// Измените методы класса EmployeeDAOImpl  так, чтобы они реализовывались через Hibernate. Измените класс Application  так,
// чтобы он реализовывался через Hibernate.
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
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();

        }
    }

    @Override
    public Employee findById1(Integer id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.get(Employee.class, id);
        }
    }

    @Override
    public List<Employee> fullFindByEmployee() {

        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employee").list();
        }
    }

    @Override
    public void toChange(Employee employee) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(employee);
            transaction.commit();
        }
    }

    @Override
    public void deleteById(Employee employee) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(employee);
            transaction.commit();

        }

    }
}
