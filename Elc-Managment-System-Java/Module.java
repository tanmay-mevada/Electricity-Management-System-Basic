//Module.java
/*
The method clearScreen builds a process and executes it to clear the screen of the terminal.
*/

public class Module 
{
    // Method to clear the screen of the terminal
    public static void clearScreen() 
    {
        try 
        {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // Executes the command to clear the screen
        } 
        catch (Exception ex) 
        {
            System.out.println("Failed to clear screen: " + ex.getMessage()); // Prints error message if unable to clear screen
        }
    }
}