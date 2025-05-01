//MyFrame.java
/*
It provides a simple GUI frame to user to login and compiles and run the Admin.java if login is successful
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MyFrame extends JFrame 
{
    private JTextField usernameField;
    private JPasswordField passwordField;

    public MyFrame() 
    {
        // Setting up JFrame properties
        this.setTitle("Login Page");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(400, 200);

        // Creating JPanel for organizing components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        // Creating username and password labels and fields
        JLabel usernameLabel = new JLabel("Username : ");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password : ");
        passwordField = new JPasswordField();

        // Creating login button
        JButton loginButton = new JButton("Login");

        // ActionListener for the login button
        loginButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                // Perform login validation here
                if (username.equals("admin") && password.equals("password")) 
                {
                    openCommandPromptAndCompileAdmin(username, password);
                } 
                else 
                {
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adding components to the panel
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        // Adding panel to the frame and setting frame visibility
        this.add(panel);
        this.setVisible(true);
    }

    // Method to open command prompt and compile Admin.java
    private void openCommandPromptAndCompileAdmin(String username, String password) 
    {
        try 
        {
            // Compile Admin.java using javac with MySQL Connector and iText
            ProcessBuilder compileProcessBuilder = new ProcessBuilder("javac", "-cp", ".;mysql-connector-java-8.0.28.jar;itextpdf-5.5.9.jar", "Admin.java");
            compileProcessBuilder.directory(new File("D:\\Projects\\AOOP\\Elc-Managment-System-Java"));
            Process compileProcess = compileProcessBuilder.start();
            compileProcess.waitFor();

            // Check if compilation was successful
            if (compileProcess.exitValue() == 0) 
            {
                System.out.println("Admin.java compiled successfully.");
                // Launch Admin with username and password as arguments
                launchAdmin(username, password);
            } 
            else 
            {
                System.out.println("Compilation of Admin.java failed.");
            }
        } 
        catch (IOException | InterruptedException ex) 
        {
            ex.printStackTrace();
            // Display error message if compilation fails
            JOptionPane.showMessageDialog(null, "Error occurred while compiling Admin.java", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to launch Admin.java with username and password
    private void launchAdmin(String username, String password) 
    {
        try 
        {
            // Construct the command to run Admin.java with username and password as arguments
            String command = "java -cp .;mysql-connector-java-8.0.28.jar;itextpdf-5.5.9.jar Admin " + username + " " + password;

            // Open command prompt and execute the command
            ProcessBuilder cmdProcessBuilder = new ProcessBuilder("cmd.exe", "/c", "start", "cmd.exe", "/K", command);
            cmdProcessBuilder.directory(new File("D:\\Projects\\AOOP\\Elc-Managment-System-Java"));
            cmdProcessBuilder.start();
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace();
            // Display error message if launching Admin fails
            JOptionPane.showMessageDialog(null, "Error occurred while launching Admin", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
