package HospitalManagement;
import java.util.Scanner;

// Remove the unused import statement

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class patient {
    private Connection connection;
    private Scanner scanner;
    
    public patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }
    public void addPatient()
    {
System.out.println("ENTER PATIENT NAME");
String name= scanner.next();
System.out.println("Enter Patient Age");
int age=scanner.nextInt();
System.out.println("Enter Patient Gender");
String gender=scanner.next();
try {
    String query="INSERT INTO patient(name,age,gender) VALUES (?,? ,?)";
    PreparedStatement preparedstatement=connection.prepareStatement(query);
    preparedstatement.setString(1, name);
    preparedstatement.setString(2, gender);
    preparedstatement.setInt(3,age);
    int affectedrow=preparedstatement.executeUpdate();
    if(affectedrow>0)
    {
        System.out.println("Patient Added Successfully");
    }
    else
    {
        System.out.println("Failed to add Patient");
    }
    
} catch (Exception e) {
    e.printStackTrace();
}
    }

public void viewPatient()
{
    String query="select * from patients";
    try{
        PreparedStatement preparedstatement=connection.prepareStatement(query);
        ResultSet resultSet=preparedstatement.executeQuery();//table-print
            System.out.println("Patients");
            System.out.println("+--------+--------------+------+-----+---------+----------------+");
    }
    catch(SQLException e)
    {
        e.printStackTrace();
    }
}

    
        


}