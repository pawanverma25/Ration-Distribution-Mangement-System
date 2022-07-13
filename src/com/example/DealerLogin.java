package com.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Exception;
import java.sql.*;


class DealerLogin extends JFrame implements ActionListener
{
    //declare var
    final JFrame loginFrame;
    JButton submitButton;
    JLabel dealerUsernameLabel, passLabel, errorLabel;
    JTextField dealerUsernameField, passwordField;

    DealerLogin(){

        //get dimensions of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = (int)screenSize.getHeight();
        int screenWidth = (int)screenSize.getWidth();
        int frameWidth = screenWidth-400;
        int frameHeight = screenHeight-400;

        //initializing fonts
        Font  f  = new Font(Font.DIALOG,  Font.BOLD, 18);
        Font  fError  = new Font(Font.DIALOG,  Font.BOLD, 40);
        Font  fInput  = new Font(Font.DIALOG_INPUT,  Font.BOLD, 18);

        submitButton = new JButton("Submit");                                   //initialize submit button
        submitButton.setFont(f);
        submitButton.setBounds(frameWidth/2 - 75,frameHeight/2 + 60,100,30);
        submitButton.addActionListener(this);

        dealerUsernameField = new JTextField();                                           //initialize dealerIDField
        dealerUsernameField.setFont(fInput);
        dealerUsernameField.setBounds(frameWidth/2 + 20,frameHeight/2 - 80,250,30);


        dealerUsernameLabel = new JLabel("Dealer Username:");
        dealerUsernameLabel.setFont(f);
        dealerUsernameLabel.setBounds(frameWidth/2 - 220,frameHeight/2 - 80,200,30);

        passwordField = new JPasswordField();
        passwordField.setFont(fInput);
        passwordField.setBounds(frameWidth/2 + 20,frameHeight/2 - 20,200,30);

        passLabel = new JLabel("Password:");
        passLabel.setFont(f);
        passLabel.setBounds(frameWidth/2 - 220,frameHeight/2 - 20,200,30);

        errorLabel = new JLabel("Try Again!!");
        errorLabel.setFont(fError);
        errorLabel.setBounds(frameWidth/2-112,50,225,50);

        loginFrame = new JFrame("Dealer Login");
        loginFrame.setBounds(200,200,screenWidth-400,screenHeight-400);
        loginFrame.add(submitButton);
        loginFrame.add(dealerUsernameField);
        loginFrame.add(dealerUsernameLabel);
        loginFrame.add(passwordField);
        loginFrame.add(passLabel);
        loginFrame.setLayout(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] dealerUsername = dealerUsernameField.getText().split("#",2);
        String password = passwordField.getText();
        String dealerName = dealerUsername[0];
        String dealerID = "#"+dealerUsername[1];

        if(dealerID.contains("'") || dealerID.contains(";") || dealerName.contains("'") || dealerName.contains(";")) {
            errorLabel.setText("Don't Inject SQL!!");
            loginFrame.add(errorLabel);
            loginFrame.repaint();
        }

        String query = "select * from dealer_info where dealer_ID='" + dealerID + "' and name='" + dealerName + "' ;";


        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/ration_supply","root","1230");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            if(!rs.next()) {
                loginFrame.setTitle("!!!Wrong ID, This ID doesn't exist. Try Again!!!");
                errorLabel.setText("Invalid ID!!");
                loginFrame.add(errorLabel);
                loginFrame.repaint();
            }else {
                if(password.equals(rs.getString(3))){
                    new RationDistributionManagementPage();
                    loginFrame.dispose();
                } else {
                    loginFrame.setTitle("!!!Wrong PASSWORD, password doesn't match. Try Again!!");
                    loginFrame.add(errorLabel);
                    loginFrame.repaint();
                }
            }
            con.close();
        } catch(Exception exp){
            loginFrame.setTitle(exp.getMessage());
        }

    }
}

class Login
{
    public static void main(String... arg)
    {
        try
        {
            new DealerLogin();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
