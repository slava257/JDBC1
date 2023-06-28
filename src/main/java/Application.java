import dao.EmployeeDAOImpl;
import model.City;
import model.Employee;


import java.sql.*;


public class Application {
    //Задание 1
    //Создать maven-проект с архетипом org.apache.maven.archetypes:maven-archetype-webapp.
    //Добавить зависимость PostgreSQL с сайта mvnrepository и плагин maven-compiler-plugin в pom.xml-файл (пример файла pom.xml ниже).
    //Создать класс
    //Application
    // и настроить в нем подключение к созданной ранее базе данных skypro.
    //Получить и вывести в консоль полные данные об одном из работников(имя, фамилия, пол, город) по id.

    //Задание 2
    //Создать классы
    //Employee и City
    // с полями, аналогично созданным таблицам.
    //Создать интерфейс EmployeeDAO.
    //Создать в интерфейсе методы:
    //Создание (добавление) сущности Employee в таблицу.
    //Получение конкретного объекта Employee по id.
    //Получение списка всех объектов Employee из базы.
    //Изменение конкретного объекта Employee в базе по id.
    //Удаление конкретного объекта Employee из базы по id.
    //Реализовать сервис
    //EmployeeDAO
    // и каждый его метод в отдельном классе.
    //Проверить корректность работы всех методов в классе
    //Application.
    public static String findById(Integer id) {
        try (PreparedStatement preparedStatement = EmployeeDAOImpl.connection().prepareStatement("SELECT  last_name,gender,first_name,city_name FROM employee INNER JOIN city ON city.city_id = employee.city_id WHERE id = (?)")) {
            preparedStatement.setInt(1, id);
            preparedStatement.setMaxRows(1);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String firstName = "Имя: " + resultSet.getString("first_name");
            String lastName = "Фамилия: " + resultSet.getString("last_name");
            String gender = "Пол: " + resultSet.getString("gender");
            String cityName = "Город: " + resultSet.getString("city_name");
            return firstName + "\n" + lastName + "\n" + gender + "\n" + cityName + "\n";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();
        System.out.println(employeeDAO.fullFindByEmployee());
        System.out.println(findById(10));
        System.out.println(employeeDAO.findById1(10));
        employeeDAO.addEmployee(new Employee(null, "Anna12", "Oganesyan", "girl", 50, new City(4, null)));
        employeeDAO.toChange(new Employee(29, "slava", "safronov", "man", 45, new City(5, null)));
        employeeDAO.deleteById(15);
        System.out.println(employeeDAO.fullFindByEmployee());

    }
}