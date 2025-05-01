//BillGenerator.java
/*
It fetches the data from the database and generate bill for the ID
*/

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class BillGenerator {
    // Method to generate bill
    public void billGenerator() 
    {
        try 
        {
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter User ID to generate the bill : ");
            int userID = scanner.nextInt();

            // Establish database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elc", "root", "");

            // Prepare statements for retrieving user and unit data
            PreparedStatement userPreparedStatement = connection.prepareStatement("SELECT * FROM user WHERE userID = ?");
            PreparedStatement unitPreparedStatement = connection.prepareStatement("SELECT * FROM units WHERE userID = ?");
            userPreparedStatement.setInt(1, userID);
            unitPreparedStatement.setInt(1, userID);

            // Execute queries to retrieve user and unit data
            ResultSet userResultSet = userPreparedStatement.executeQuery();
            ResultSet unitResultSet = unitPreparedStatement.executeQuery();

            // Check if user data exists
            if (userResultSet.next()) 
            {
                // Extract user data
                int uid = userResultSet.getInt("userID");
                String uname = userResultSet.getString("name");
                int houseNo = userResultSet.getInt("houseNo");

                // Check if unit data exists
                if (unitResultSet.next()) 
                {
                    float unit = unitResultSet.getFloat("units");

                    // Calculate bill details
                    double unitsCharge = unit * Prices.pricePerUnit;
                    double total = unitsCharge + Prices.meterRent + Prices.elcDutyChargePerc;

                    // Get last day of current month
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String lastDayStr = sdf.format(calendar.getTime());

                    // Create PDF document
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream("bill" + userID + ".pdf"));
                    document.open();

                    // Add title
                    Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD);
                    Paragraph title = new Paragraph("Electricity Bill", titleFont);
                    title.setAlignment(Element.ALIGN_CENTER);
                    document.add(title);
                    //document.add(Chunk.NEWLINE);

                    // Add date to upper right corner
                    Paragraph date = new Paragraph(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
                    date.setAlignment(Element.ALIGN_RIGHT);
                    document.add(date);
                    document.add(Chunk.NEWLINE);

                    // Add personal details
                    PdfPTable personalDetailsTable = new PdfPTable(2);
                    personalDetailsTable.setWidthPercentage(100);
                    PdfPCell cell;
                    cell = new PdfPCell(new Phrase("Personal Details"));
                    cell.setColspan(2);
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    personalDetailsTable.addCell(cell);
                    personalDetailsTable.addCell("User ID:");
                    personalDetailsTable.addCell(Integer.toString(uid));
                    personalDetailsTable.addCell("Name:");
                    personalDetailsTable.addCell(uname);
                    personalDetailsTable.addCell("House No:");
                    personalDetailsTable.addCell(Integer.toString(houseNo));
                    document.add(personalDetailsTable);
                    document.add(Chunk.NEWLINE);

                    // Add units consumed
                    PdfPTable unitsTable = new PdfPTable(1);
                    unitsTable.setWidthPercentage(100);
                    cell = new PdfPCell(new Phrase("Units Consumed"));
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    unitsTable.addCell(cell);
                    unitsTable.addCell(Float.toString(unit));
                    document.add(unitsTable);
                    document.add(Chunk.NEWLINE);

                    // Add bill details
                    PdfPTable billDetailsTable = new PdfPTable(2);
                    billDetailsTable.setWidthPercentage(100);
                    cell = new PdfPCell(new Phrase("Bill Details"));
                    cell.setColspan(2);
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    billDetailsTable.addCell(cell);
                    billDetailsTable.addCell("Units Charge:");
                    billDetailsTable.addCell(Double.toString(unitsCharge));
                    billDetailsTable.addCell("Meter Rent:");
                    billDetailsTable.addCell(Double.toString(Prices.meterRent));
                    billDetailsTable.addCell("Electricity Duty Charge:");
                    billDetailsTable.addCell(Double.toString(Prices.elcDutyChargePerc));

                    document.add(billDetailsTable);

                    // Add blank space
                    document.add(Chunk.NEWLINE);

                    // Add total amount payable and last day to pay
                    PdfPTable totalTable = new PdfPTable(2);
                    totalTable.setWidthPercentage(100);
                    cell = new PdfPCell(new Phrase("Total Amount Payable"));
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    totalTable.addCell(cell);
                    cell = new PdfPCell(new Phrase("Last Day to Pay"));
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    totalTable.addCell(cell);

                    cell = new PdfPCell(new Phrase(Double.toString(total)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    totalTable.addCell(cell);

                    cell = new PdfPCell(new Phrase(lastDayStr));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    totalTable.addCell(cell);

                    document.add(totalTable);

                    // Close PDF document
                    document.close();
                    Module.clearScreen();
                    System.out.println("Bill generated successfully as bill" + userID + ".pdf");
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
                    System.out.println("Unit data not found for user ID: " + userID);
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
            else 
            {
                Module.clearScreen();
                System.out.println("User not found for user ID: " + userID);
                try
                {
                    Thread.sleep(3500);
                }
                catch(InterruptedException ie)
                {
                    System.out.println(ie);
                }            
            }

            // Close result sets, statements, and connection
            userResultSet.close();
            unitResultSet.close();
            userPreparedStatement.close();
            unitPreparedStatement.close();
            connection.close();
        } 
        catch (Exception e) 
        {
            Module.clearScreen();
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
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
