package electricity.billing.system;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DepositDetails extends JFrame implements ActionListener{
    
    JTextField timestamp;
    Choice meternumber, cmonth;
    JTable table;
    JButton search, print;
    
    DepositDetails(){
        super("Deposit Details");
        
        Date date = Calendar.getInstance().getTime(); 
        DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");
        
        setSize(700, 500);
        setLocation(400, 150);
        
        setSize(700, 700);
        setLocation(400, 100);
        
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        
        JLabel lblmeternumber = new JLabel("Search By Meter Number");
        lblmeternumber.setBounds(20, 20, 150, 20);
        add(lblmeternumber);
        
        meternumber = new Choice();
        meternumber.setBounds(180, 20, 150, 20);
        add(meternumber);
        
        try {
            Conn c  = new Conn();
            ResultSet rs = c.s.executeQuery("select * from customer");
            while(rs.next()) {
                meternumber.add(rs.getString("meter_no"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JLabel lblmonth = new JLabel("");
        lblmonth.setBounds(400, 20, 100, 20);
        add(lblmonth);
        /*
        cmonth = new Choice();
        cmonth.setBounds(520, 20, 150, 20);
        cmonth.add("January");
        cmonth.add("February");
        cmonth.add("March");
        cmonth.add("April");
        cmonth.add("May");
        cmonth.add("June");
        cmonth.add("July");
        cmonth.add("August");
        cmonth.add("September");
        cmonth.add("October");
        cmonth.add("November");
        cmonth.add("December");
        add(cmonth);
        */
         timestamp = new JTextField();
        timestamp.setBounds(500, 20, 60, 20);
        timestamp.setEditable(false);
        timestamp.setText(dateFormat.format(date));
        add(timestamp);
        
        
        table = new JTable();
        
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from bill");
            
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(0, 100, 700, 600);
        add(sp);
        
        search = new JButton("Search");
        search.setBounds(20, 70, 80, 20);
        search.addActionListener(this);
        add(search);
        
        print = new JButton("Print");
        print.setBounds(120, 70, 80, 20);
        print.addActionListener(this);
        add(print);
        
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        
        if (ae.getSource() == search) {
            String tstamp = timestamp.getText();
            String query = "select * from bill where meter_no = '"+meternumber.getSelectedItem()+"' ";
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e) {
                
            }
        } else  {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new DepositDetails();
    }
}