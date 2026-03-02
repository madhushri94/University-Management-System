package university.management.system1;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class TeacherDetails extends JFrame implements ActionListener {
    
    Choice cEmpId;
    JTable table;
    JButton search, print, update, add, cancel;
    
    TeacherDetails() {
        
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel heading = new JLabel("Search by Employee Id");
        heading.setBounds(20, 20, 180, 25);
        heading.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(heading);
        
        cEmpId = new Choice();
        cEmpId.setBounds(210, 20, 180, 25);
        add(cEmpId);
        
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select empid from teacher");
            while (rs.next()) {
                cEmpId.add(rs.getString("empid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        table = new JTable();
        
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from teacher");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(10, 100, 880, 550);
        add(jsp);
        
        search = new JButton("Search");
        search.setBounds(20, 60, 90, 25);
        search.addActionListener(this);
        add(search);
        
        print = new JButton("Print");
        print.setBounds(130, 60, 90, 25);
        print.addActionListener(this);
        add(print);
        
        add = new JButton("Add");
        add.setBounds(240, 60, 90, 25);
        add.addActionListener(this);
        add(add);
        
        update = new JButton("Update");
        update.setBounds(350, 60, 90, 25);
        update.addActionListener(this);
        add(update);
        
        cancel = new JButton("Cancel");
        cancel.setBounds(460, 60, 90, 25);
        cancel.addActionListener(this);
        add(cancel);
        
        setSize(900, 700);
        setLocationRelativeTo(null);   // center screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        toFront();
        requestFocus();
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String query = "select * from teacher where empid = '"+cEmpId.getSelectedItem()+"'";
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } else if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } else if (ae.getSource() == add) {
            setVisible(false);
            new AddTeacher();
            
        } else if (ae.getSource() == update) {
            setVisible(false);
             new UpdateTeacher();   // uncomment when class is ready
            
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }
    
    public static void main(String[] args) {
        new TeacherDetails();
    }
}
