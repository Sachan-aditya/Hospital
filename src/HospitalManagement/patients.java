package HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class patients {
    private Connection connection;
    private Scanner scanner;

    public patients(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient() {
        System.out.println("Enter Patient Name:");
        String name = scanner.next();
        System.out.println("Enter Patient Age:");
        int age = scanner.nextInt();
        System.out.println("Enter Patient Gender:");
        String gender = scanner.next();

        String query = "INSERT INTO patients (name, age, gender) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            preparedStatement.executeUpdate();
            System.out.println("Patient added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPatient() {
        String query = "SELECT * FROM patients";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients");
            System.out.println("+----------+--------------+-----+--------+");
            System.out.println("|PATIENT ID|NAME          |AGE  |GENDER  |");
            System.out.println("+----------+--------------+-----+--------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("|%-10d|%-14s|%-5d|%-8s|\n", id, name, age, gender);
            }
            System.out.println("+----------+--------------+-----+--------+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getPatiendid(int id) {
        String query = "SELECT * FROM patients WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
