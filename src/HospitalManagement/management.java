package HospitalManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Scanner;

public class management {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String name = "root";
    private static final String password = "Adityasac$029";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, name, password);
            patients patient = new patients(connection, scanner);
            doctors doctor = new doctors(connection);

            while (true) {
                System.out.println("Hospital Management System");
                System.out.println("1. ADD PATIENT");
                System.out.println("2. VIEW PATIENT");
                System.out.println("3. VIEW DOCTORS");
                System.out.println("4. BOOK APPOINTMENT");
                System.out.println("5. EXIT");
                System.out.println("ENTER YOUR CHOICE");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        patient.addPatient();
                        break;
                    case 2:
                        patient.viewPatient();
                        break;
                    case 3:
                        doctor.viewDoctor();
                        break;
                    case 4:
                        bookAppointment(patient, doctor, connection, scanner);
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid Choice");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    public static void bookAppointment(patients patient, doctors doctor, Connection connection, Scanner scanner) {
        System.out.println("Enter PATIENT ID");
        int patientId = scanner.nextInt();
        System.out.println("Enter DOCTOR ID");
        int doctorId = scanner.nextInt();
        System.out.println("Enter DATE (YYYY-MM-DD)");
        String date = scanner.next();

        if (patient.getPatiendid(patientId) && doctor.getDoctorId(doctorId)) {
            if (checkAvailability(doctorId, date, connection)) {
                try {
                    String appointQuery = "INSERT INTO appointments (patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
                    PreparedStatement pstmt = connection.prepareStatement(appointQuery);
                    pstmt.setInt(1, patientId);
                    pstmt.setInt(2, doctorId);
                    pstmt.setString(3, date);
                    int affectedRows = pstmt.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("Appointment booked for Patient ID " + patientId + " with Doctor ID " + doctorId + " on " + date);
                    } else {
                        System.out.println("Failed to Book Appointment");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Doctor not available on this date");
            }
        } else {
            System.out.println("Invalid Patient ID or Doctor ID");
        }
    }

    public static boolean checkAvailability(int doctorId, String date, Connection connection) {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, doctorId);
            pstmt.setString(2, date);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
