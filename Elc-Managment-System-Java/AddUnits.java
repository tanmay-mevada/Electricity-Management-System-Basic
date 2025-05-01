//AddUnits.java
/*
The addUnits() method is used to add Consumed units in the database 
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class AddUnits 
{
    int userID;
    float units;

    void addUnits()
    {   
        @SuppressWarnings("resource")
        Scanner intScanner = new Scanner(System.in);
        @SuppressWarnings("resource")
        Scanner floatScanner = new Scanner(System.in);
        
        while (true) 
        {
            System.out.print ("Enter User Id : ");
            userID = intScanner.nextInt();
            System.out.print ("Enter consumed units : ");
            units  = floatScanner.nextFloat();

            System.out.print ("Confirm, Submit the data?(1/0) : ");
                int temp = intScanner.nextInt();

            if (temp==1)
                break;
        }

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elc", "root", "");

            String insertQuery = "INSERT INTO units (userID, units) VALUES (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, userID);
            preparedStatement.setFloat(2, units);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) 
            {
                Module.clearScreen();
                System.out.println("Units added successfully.");
                try
                {
                    Thread.sleep(3500);
                }
                catch(InterruptedException ie)
                {
                    System.out.println(ie);
                }
            } 
            else 
            {
                Module.clearScreen();
                System.out.println("Failed to add units.");
                try
                {
                    Thread.sleep(3500);
                }
                catch(InterruptedException ie)
                {
                    System.out.println(ie);
                }
            }
        }
        catch (Exception e)
        {
            Module.clearScreen();
            System.out.println ("Exception : "+e);
            try
            {
                Thread.sleep(3500);
            }
            catch(InterruptedException ie)
            {
                System.out.println(ie);
            }
        }
    }
}
