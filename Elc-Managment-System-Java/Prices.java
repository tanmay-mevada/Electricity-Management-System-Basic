// Prices.java
/*
This class defines static data for prices, which can be directly accessed by the class name.
The setPrices() method is used to assign values to the data with keyboard input.
*/

import java.util.Scanner;

public class Prices 
{
    // Static variables to hold prices
    static float pricePerUnit;
    static float meterRent;
    static float elcDutyChargePerc;

    // Method to set prices
    void setPrices ()
    {
        // Creating Scanner object for input
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        // Getting input for prices
        System.out.print ("Enter price of a unit : ");
        pricePerUnit = scanner.nextFloat();
        System.out.print ("Enter meter rent : ");
        meterRent = scanner.nextFloat();
        System.out.print("Enter electricity duty charge : ");
        elcDutyChargePerc = scanner.nextFloat();

        // Clearing the screen and displaying success message
        Module.clearScreen();
        System.out.println("Prices set successfully..");
        try
        {
            Thread.sleep(3500);
        }
        catch (InterruptedException ie)
        {
            System.out.println (ie);
        }
    }
}
