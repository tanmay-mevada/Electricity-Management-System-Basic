//Users.java
/*
The addNewUser() method is used to add a new user into the database
The deleteUser() method is used to delete user data by ID from the database
The editUser() method is used to edit data for the existing user in the database
*/

import java.util.Scanner;
import java.sql.*;

public class Users 
{
    private int userID;
    private String name;
    private int houseNo;

    // Method to add a new user
    void addNewUser() 
    {
        // Creating Scanner objects for input
        @SuppressWarnings("resource")
        Scanner intScanner = new Scanner(System.in);
        @SuppressWarnings("resource")
        Scanner strScanner = new Scanner(System.in);

        // Input loop to enter user details
        while (true) 
        {
            System.out.print("Enter User ID : ");
            userID = intScanner.nextInt();
            System.out.print("Enter Name : ");
            name = strScanner.nextLine();
            System.out.print("Enter House No. :");
            houseNo = intScanner.nextInt();

            System.out.print("Confirm, Submit the data?(1/0) : ");
            int temp = intScanner.nextInt();

            if (temp == 1)
                break;
        }

        // Try-catch block for handling database operations
        try 
        {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elc", "root", "");

            // Define SQL insert query
            String insertQuery = "INSERT INTO user (userID, name, houseNo) VALUES (?, ?, ?)";

            // Create PreparedStatement to execute the insert query
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, houseNo);

            // Execute the insert query and get the number of affected rows
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if user was successfully added
            if (rowsAffected > 0) 
            {
                Module.clearScreen();
                System.out.println("New user added successfully..");
                try 
                {
                    Thread.sleep(3500);
                } 
                catch (InterruptedException ie) 
                {
                    System.out.println(ie);
                }
            } 
            else 
            {
                Module.clearScreen();
                System.out.println("Failed to add user..");
                try 
                {
                    Thread.sleep(3500);
                }
                catch (InterruptedException ie) 
                {
                    System.out.println(ie);
                }
            }
        } 
        catch (Exception e) 
        {
            Module.clearScreen();
            System.out.println("Exception : " + e);
            try 
            {
                Thread.sleep(3500);
            } 
            catch (InterruptedException ie) 
            {
                System.out.println(ie);
            }
        }
    }

    // Method to delete a user
    void deleteUser() 
    {
        @SuppressWarnings("resource")
        Scanner intScanner = new Scanner(System.in);

        // Input loop to get user ID for deletion
        while (true) 
        {
            System.out.print("Enter User ID : ");
            userID = intScanner.nextInt();

            System.out.print("Confirm, Delete the data?(1/0) : ");
            int temp = intScanner.nextInt();

            if (temp == 1)
                break;
        }

        // Try-catch block for handling database operations
        try 
        {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elc", "root", "");

            // Define SQL delete query
            String deleteUserQuery = "DELETE FROM user WHERE userID = ?";
            String deleteUnitQuery = "DELETE FROM units WHERE userID = ?";

            // Create PreparedStatement to execute the delete query
            PreparedStatement preparedStatement1 = connection.prepareStatement(deleteUserQuery);
            preparedStatement1.setInt(1, userID);
            PreparedStatement preparedStatement2 = connection.prepareStatement(deleteUnitQuery);
            preparedStatement2.setInt(1, userID);

            // Execute the delete query and get the number of affected rows
            int rowsAffected = preparedStatement1.executeUpdate() + preparedStatement2.executeUpdate();

            // Check if user was successfully deleted
            if (rowsAffected > 0) 
            {
                Module.clearScreen();
                System.out.println("User deleted successfully.");
                try 
                {
                    Thread.sleep(3500);
                } 
                catch (InterruptedException ie) 
                {
                    System.out.println(ie);
                }
            } 
            else 
            {
                Module.clearScreen();
                System.out.println("User not found or deletion failed.");
                try 
                {
                    Thread.sleep(3500);
                } 
                catch (InterruptedException ie) 
                {
                    System.out.println(ie);
                }
            }
        } 
        catch (Exception e) 
        {
            Module.clearScreen();
            System.out.println("Exception : " + e);
            try 
            {
                Thread.sleep(3500);
            } 
            catch (InterruptedException ie) 
            {
                System.out.println(ie);
            }
        }
    }

    // Method to edit user details
    void editUser() 
    {
        @SuppressWarnings("resource")
        Scanner intScanner = new Scanner(System.in);
        @SuppressWarnings("resource")
        Scanner strScanner = new Scanner(System.in);
        int oldUserID;

        // Input loop to get user details for editing
        while (true) 
        {
            System.out.print("Enter User ID : ");
            oldUserID = intScanner.nextInt();

            System.out.print("Enter new User ID : ");
            userID = intScanner.nextInt();
            System.out.print("Enter new Name : ");
            name = strScanner.nextLine();
            System.out.print("Enter new House No. : ");
            houseNo = intScanner.nextInt();

            System.out.print("Confirm, Update the data?(1/0) : ");
            int temp = intScanner.nextInt();

            if (temp == 1)
                break;
        }

        // Try-catch block for handling database operations
        try 
        {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elc", "root", "");

            // Construct the UPDATE statement
            String updateQuery = "UPDATE user SET userID = ?, name = ?, houseNo = ? WHERE userID = ?";

            // Create a PreparedStatement to avoid SQL injection
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, oldUserID);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, houseNo);
            preparedStatement.setInt(4, userID);

            // Execute the UPDATE statement
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) 
            {
                Module.clearScreen();
                System.out.println("User data updated successfully.");
                try 
                {
                    Thread.sleep(3500);
                } 
                catch (InterruptedException ie) 
                {
                    System.out.println(ie);
                }
            } 
            else 
            {
                Module.clearScreen();
                System.out.println("User not found or update failed.");
                try 
                {
                    Thread.sleep(3500);
                } 
                catch (InterruptedException ie) 
                {
                    System.out.println(ie);
                }
            }
        } 
        catch (Exception e) 
        {
            Module.clearScreen();
            System.out.println("Exception : " + e);
            try 
            {
                Thread.sleep(3500);
            } 
            catch (InterruptedException ie) 
            {
                System.out.println(ie);
            }
        }
    }
}