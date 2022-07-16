package com.example;

/*
  RationDistributionManagementPage.java - auxiliary definitions for consumer verification and ration distribution page
  *
  @author
 *   Pawan Verma, Bk Birla Institute of Engineering and Technology
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.Vector;

//create NewPage class to create a new page on which user will navigate
class RationDistributionManagementPage extends JFrame implements ActionListener
{
    final JFrame mgmtFrame;
    JLabel rationID, cardholderName, errorLabel, dateLabel, lastDate, verificationLabel;
    JTextField rationIDField, cardholderNameField,verificationField;
    JButton findButton, verifyButton,checkoutButton;
    DefaultTableModel model;
    JScrollPane scroll;
    JTable table;
    LocalDate now;
    private final Vector<String> data = new Vector<>();
    int selectedRow = -1;
    String cardNO;

    //constructor
    RationDistributionManagementPage()
    {
        //get dimensions of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = (int)screenSize.getHeight();
        int screenWidth = (int)screenSize.getWidth();
        int frameWidth = screenWidth-300;
        int frameHeight = screenHeight-300;


        //initializing fonts
        Font  f  = new Font(Font.DIALOG,  Font.BOLD, 17);
        Font  fError  = new Font(Font.DIALOG,  Font.BOLD, 40);
        Font  fInput  = new Font(Font.DIALOG_INPUT,  Font.BOLD | Font.ITALIC, 16);

        rationID = new JLabel("Ration Card NO.:");
        rationID.setFont(f);
        rationID.setBounds(30,30,170,30);

        rationIDField = new JTextField();
        rationIDField.setFont(fInput);
        rationIDField.setBounds(220,30,200,30);

        cardholderName = new JLabel("Card Holder Name:");
        cardholderName.setFont(f);
        cardholderName.setBounds(30,80,170,30);

        cardholderNameField = new JTextField();
        cardholderNameField.setFont(fInput);
        cardholderNameField.setBounds(220,80,200,30);

        findButton = new JButton("Find");
        findButton.setFont(f);
        findButton.setBounds(450,82,80,25);
        findButton.addActionListener(this::actionPerformedFind);

        errorLabel = new JLabel("Invalid Input!!");
        errorLabel.setFont(fError);
        errorLabel.setBounds(frameWidth/2-150,frameHeight/2-25,300,50);

        dateLabel = new JLabel("Ration last received :");
        dateLabel.setBounds(frameWidth/2+296,130,200,30);

        lastDate = new JLabel();
        lastDate.setBounds(frameWidth/2+425,130,200,30);

        verificationLabel = new JLabel();
        verificationLabel.setFont(f);
        verificationLabel.setBounds(30,410,400,30);

        verificationField = new JTextField();
        verificationField.setFont(fInput);
        verificationField.setBounds(150,410,150,30);

        verifyButton = new JButton("Verify");
        verifyButton.setFont(f);
        verifyButton.setBounds(330,412,90,25);

        checkoutButton = new JButton("Checkout");
        checkoutButton.setFont(f);
        checkoutButton.setBounds(frameWidth-170,frameHeight-100,120,25);

        verifyButton.addActionListener(this);


        String[] columnNames ={"Ration Card ID","Name","Aadhar Card ID","Age"};

        table = new JTable();
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);

        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


        scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds((frameWidth-998)/2,180,998,200);

        table.setRowSelectionAllowed(true);
        table.setRowHeight(30);
        table.setFont(fInput);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(f);

        ListSelectionModel select= table.getSelectionModel();
        select.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        mgmtFrame = new JFrame("Dealer Login");
        mgmtFrame.setBounds(150,150,screenWidth-300,screenHeight-300);
        mgmtFrame.setTitle("Ration Management Portal");
        mgmtFrame.add(rationID);
        mgmtFrame.add(rationIDField);
        mgmtFrame.add(cardholderName);
        mgmtFrame.add(cardholderNameField);
        mgmtFrame.add(findButton);

        mgmtFrame.setLayout(null);
        mgmtFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mgmtFrame.setVisible(true);

        select.addListSelectionListener(e -> {
            int[] row = table.getSelectedRows();
            for (int k : row) {
                selectedRow = k;
            }
            verificationLabel.setText("Verification ID:");
            mgmtFrame.add(verificationField);
            mgmtFrame.add(verificationLabel);
            mgmtFrame.add(verifyButton);
            mgmtFrame.repaint();
        });

        checkoutButton.addActionListener(e ->{
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ration_supply","root","1230");
                Statement stmt=con.createStatement();
                stmt.executeUpdate("update card_table SET last_received = '"+now+"' WHERE card_no = '"+ cardNO +"';");
                con.close();
            }
            catch(Exception exe) {
                mgmtFrame.setTitle(exe.getMessage());
            }
            mgmtFrame.remove(scroll);
            mgmtFrame.remove(lastDate);
            mgmtFrame.remove(dateLabel);
            mgmtFrame.remove(verifyButton);
            mgmtFrame.remove(verificationField);
            mgmtFrame.remove(verificationLabel);
            mgmtFrame.repaint();
        });

    }

    public void actionPerformedFind(ActionEvent e) {

        model.setRowCount(0);

        String rationCardNo = rationIDField.getText();
        String holderName = cardholderNameField.getText();

        if(rationCardNo.contains("'") || rationCardNo.contains(";") || holderName.contains("'") || holderName.contains(";")) {
            errorLabel.setText("Don't Inject SQL!!");
            mgmtFrame.add(errorLabel);
            mgmtFrame.repaint();
        }

        String query = "select card_no,last_received from card_table where card_no='" + rationCardNo + "' and holder_name='" + holderName + "' ;";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/ration_supply","root","1230");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(query);

            if(!rs.next()) {
                mgmtFrame.remove(scroll);
                mgmtFrame.remove(lastDate);
                mgmtFrame.remove(dateLabel);
                mgmtFrame.add(errorLabel);
                mgmtFrame.repaint();
            }else {
                lastDate.setText(rs.getString(2));
                cardNO = rs.getString(1);
                String query2 = "select card_no,name,aadhar_no,age,verification_id from person_table where card_no='" + rationCardNo + "';";
                rs = stmt.executeQuery(query2);
                int i = 0;
                while (rs.next()) {
                    model.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)});
                    data.add(rs.getString(5));
                    mgmtFrame.repaint();
                    i++;
                }
                if (i < 1) {
                    JOptionPane.showMessageDialog(null, "No Record Found", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    mgmtFrame.add(dateLabel);
                    mgmtFrame.add(scroll);
                    mgmtFrame.add(lastDate);
                }
                mgmtFrame.remove(errorLabel);
            }

            con.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(verifyButton.getText().equals("Retry")) {
            verificationLabel.setText("Verification ID:");
            mgmtFrame.add(verificationField);
            mgmtFrame.add(verificationLabel);
            verifyButton.setText("Verify");
            mgmtFrame.repaint();
        } else {

            String verificationID = verificationField.getText();

            if (data.elementAt(selectedRow).equals(verificationID)) {
                LocalDate date = LocalDate.parse(lastDate.getText());
                Month lastCheckedMonth = date.getMonth();
                now = LocalDate.now();
                Month currentMonth = now.getMonth();

                if (date.compareTo(now) < 0 && lastCheckedMonth != currentMonth) {
                    verificationLabel.setText("Verified!! Now you may receive ration.");
                    mgmtFrame.add(checkoutButton);

                } else {
                    verificationLabel.setText("Come again in next month!!");
                }

            } else {
                verificationLabel.setText("Invalid Verification ID !!");
                verifyButton.setText("Retry");
            }
        }
        if(!verifyButton.getText().equals("Retry")) {
            mgmtFrame.remove(verifyButton);
        }
        mgmtFrame.remove(verificationField);
        mgmtFrame.repaint();
    }
}
