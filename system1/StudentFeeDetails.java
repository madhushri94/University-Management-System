package university.management.system1;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import net.proteanit.sql.DbUtils;

public class StudentFeeDetails extends JFrame implements ActionListener {

    JTable table;
    JButton back;

    StudentFeeDetails() {

        setSize(900, 400);
        setLocation(300, 150);
        setLayout(null);

        JLabel heading = new JLabel("Student Fee Details");
        heading.setBounds(300, 10, 300, 30);
        heading.setFont(new Font("Tahoma", Font.BOLD, 22));
        add(heading);

        table = new JTable();

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from studentfee");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 60, 850, 250);
        add(sp);

        back = new JButton("Back");
        back.setBounds(380, 320, 120, 30);
        back.addActionListener(this);
        add(back);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
    }

    public static void main(String[] args) {
        new StudentFeeDetails();
    }
}
