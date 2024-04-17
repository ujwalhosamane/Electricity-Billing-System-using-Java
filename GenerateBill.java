package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class GenerateBill extends JFrame implements ActionListener{

    String meter;
    JButton bill;
    Choice cmonth;
    JTextArea area;
    GenerateBill(String meter) {
        this.meter = meter;
        
        setSize(500, 800);
        setLocation(550, 30);
        
        setLayout(new BorderLayout());
        
        JPanel panel = new JPanel();
        
        JLabel heading = new JLabel("Generate Bill");
        JLabel meternumber = new JLabel(meter);
        
        cmonth = new Choice();
        cmonth.setBounds(300, 200, 200, 20);
        try{
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT YEAR(date) AS year, MONTHNAME(date) AS month FROM bill WHERE meter_no = '"+meter+"' and status = 'Paid'");
            while(rs.next()) {
                cmonth.add(rs.getString(2) + " " + rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        area = new JTextArea(50, 15);
        area.setText("\n\n\t--------Click on the---------\n\t Generate Bill Button to get\n\tthe bill of the Selected Month");
        area.setFont(new Font("Senserif", Font.ITALIC, 18));
        
        JScrollPane pane = new JScrollPane(area);
        
        bill = new JButton("Generate Bill");
        bill.addActionListener(this);
        
        panel.add(heading);
        panel.add(meternumber);
        panel.add(cmonth);
        add(panel, "North");
        
        add(pane, "Center");
        add(bill, "South");
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        try {
            Conn c = new Conn();
          
            String[] words =cmonth.getSelectedItem().split("\\s");
            String month = words[0];
            String year = words[1];
            
            area.setText("\tReliance Power Limited\nELECTRICITY BILL GENERATED FOR THE MONTH\n\tOF "+month+"\n\n\n");
            
            ResultSet rs = c.s.executeQuery("select * from customer where meter_no = '"+meter+"'");
        
            if(rs.next()) {
                area.append("\n    Customer Name: " + rs.getString("name").toUpperCase());
                area.append("\n    Meter Number:    " + rs.getString("meter_no"));
                area.append("\n    Address:               " + rs.getString("address").toUpperCase());
                area.append("\n    City:                       " + rs.getString("city").toUpperCase());
                area.append("\n    State:                     " + rs.getString("state").toUpperCase());
                area.append("\n    Email:                   " + rs.getString("email"));
                area.append("\n    Phone:                  " + rs.getString("phone"));
                area.append("\n---------------------------------------------------");
                area.append("\n");
            }
            
            rs = c.s.executeQuery("select * from meter_info where meter_no = '"+meter+"'");
        
            if(rs.next()) {
                area.append("\n    Meter Location: " + rs.getString("meter_location"));
                area.append("\n    Meter Type:       " + rs.getString("meter_type"));
                area.append("\n    Phase Code:      " + rs.getString("phase_code"));
                area.append("\n    Bill Type:           " + rs.getString("bill_type"));
                area.append("\n    Days:                " + rs.getString("days"));
                area.append("\n---------------------------------------------------");
                area.append("\n");
            }
            
            rs = c.s.executeQuery("select * from tax");
        
            if(rs.next()) {
                area.append("\n");
                area.append("\n    Cost Per Unit:                " + rs.getString("cost_per_unit"));
                area.append("\n    Meter Rent:                   " + rs.getString("cost_per_unit"));
                area.append("\n    Service Charge:           " + rs.getString("service_charge"));
                area.append("\n    Service Tax:                  " + rs.getString("service_charge"));
                area.append("\n    Swacch Bharat Cess:  " + rs.getString("swacch_bharat_cess"));
                area.append("\n    Fixed Tax:                     " + rs.getString("fixed_tax"));
                area.append("\n");
            }
            
            rs = c.s.executeQuery("select * from bill where meter_no = '"+meter+"' and MONTHNAME(date) IN ('"+month+"') and YEAR(date) IN ('"+year+"')");
        
            if(rs.next()) {
                area.append("\n");
                area.append("\n    Current Month:             " + month);
                area.append("\n    Current Year:               " + year);
                area.append("\n    Units Consumed:        " + rs.getString("units"));
                area.append("\n    Total Charges:            " + rs.getString("totalbill"));
                area.append("\n-------------------------------------------------------");
                area.append("\n    Total Payable:            " + rs.getString("totalbill"));
                area.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new GenerateBill("");
    }
}