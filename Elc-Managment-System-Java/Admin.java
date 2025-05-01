//Admin.java
/*
The Admin class provides an interface for administrators to manage various aspects of the system, including:
- Editing users
- Managing prices
- Adding units
- Printing bills
*/

import java.util.*;
import java.time.LocalTime;

public class Admin 
{
    public static void main(String[] args) 
    {
        // Check if username and password are provided as command line arguments
        if (args.length >= 2)
        {
            String username = args[0];
            String password = args[1];

            // Validate username and password
            if (!(username.equals("admin") && password.equals("password"))) 
            {
                System.out.println("Invalid <username> or <password>");
                return;
            }

            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(System.in);

            try 
            {
                // Main menu loop
                while (true) 
                {
                    LocalTime currentTime = LocalTime.now();

                    // Greet Admin based on time of day
                    if (currentTime.isBefore(LocalTime.NOON)) {
                        System.out.println("Good Morning, Admin!!");
                    } else if (currentTime.isBefore(LocalTime.of(17, 0))) {
                        System.out.println("Good Afternoon, Admin!!");
                    } else if (currentTime.isBefore(LocalTime.of(20, 0))) {
                        System.out.println("Good Evening, Admin!!");
                    } else {
                        System.out.println("Good Night, Admin!!");
                    }

                    // Display menu options
                    try 
                    {
                        Thread.sleep(1000);
                        System.out.println("1. Manage users..");
                        Thread.sleep(100);
                        System.out.println("2. Manage Prices..");
                        Thread.sleep(100);
                        System.out.println("3. Add units..");
                        Thread.sleep(100);
                        System.out.println("4. Print Bill..");
                        Thread.sleep(100);
                        System.out.println("5. Exit..");
                        Thread.sleep(100);
                    } 
                    catch (InterruptedException ie) 
                    {
                        System.out.println(ie);
                    }

                    // Get user choice
                    System.out.print("Enter your choice : ");
                    int choice = 0;
                    choice = scanner.nextInt();
                    Module.clearScreen();

                    // Handle user choice
                    switch (choice) 
                    {
                        case 1:
                            // Manage users
                            Users user = new Users();

                            try 
                            {
                                Thread.sleep(100);
                                System.out.println("--> MANAGE USERS <--");
                                Thread.sleep(100);
                                System.out.println("1. Add new user..");
                                Thread.sleep(100);
                                System.out.println("2. Delete user..");
                                Thread.sleep(100);
                                System.out.println("3. Edit existing user..");
                                Thread.sleep(100);
                                System.out.print("Enter your choice : ");
                            } 
                            catch (InterruptedException ie) 
                            {
                                System.out.println(ie);
                            }
                            int temp = 0;
                            temp = scanner.nextInt();

                            switch (temp) {
                                case 1:
                                    user.addNewUser();
                                    break;
                                case 2:
                                    user.deleteUser();
                                    break;
                                case 3:
                                    user.editUser();
                                    break;
                                default:
                                    Module.clearScreen();
                                    System.out.println("Invalid choice..");
                                    try 
                                    {
                                        Thread.sleep(3500);
                                    } 
                                    catch (InterruptedException ie) 
                                    {
                                        System.out.println(ie);
                                    }
                                    Module.clearScreen();
                                    break;
                            }
                            Module.clearScreen();
                            break;

                        case 2:
                            // Manage prices
                            Prices price = new Prices();

                            System.out.println("-->MANAGE PRICES<--");

                            price.setPrices();

                            Module.clearScreen();
                            break;

                        case 3:
                            // Add units
                            AddUnits addUnits = new AddUnits();

                            System.out.println("-->ADD UNITS<--");

                            addUnits.addUnits();

                            Module.clearScreen();
                            break;

                        case 4:
                            // Print bill
                            BillGenerator billGenerator = new BillGenerator();

                            System.out.println("-->PRINT BILL<--");

                            billGenerator.billGenerator();

                            Module.clearScreen();
                            break;

                        case 5:
                            // Exit
                            System.out.println("Exiting the program..");
                            try 
                            {
                                Thread.sleep(3500);
                            } 
                            catch (InterruptedException ie) 
                            {
                                System.out.println(ie);
                            }
                            Module.clearScreen();
                            System.exit(0);

                        default:
                            System.out.println("Invalid choice..");
                            try 
                            {
                                Thread.sleep(3500);
                            } 
                            catch (InterruptedException ie) 
                            {
                                System.out.println(ie);
                            }
                            Module.clearScreen();
                            break;
                    }
                }
            } 
            catch (InputMismatchException e) 
            {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Clear invalid input
            } 
            catch (NoSuchElementException e) 
            {
                System.out.println("Input not found. Please enter valid input.");
                scanner.next(); // Clear invalid input
            } 
            catch (Exception e) 
            {
                System.out.println("An error occurred: " + e.getMessage());
            } 
            finally 
            {
                System.out.println("Program exited.");
            }
        } 
        else 
        {
            System.out.println("Usage: java Admin <username> <password>");
        }
    }
}