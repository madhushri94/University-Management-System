package university.management.system1;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class ExaminationDetails extends JFrame implements ActionListener {

    JTextField search;
    JButton submit, cancel;
    JTable table;
    JComboBox<String> cbSem;

    ExaminationDetails() {
        setSize(1000, 475);
        setLocation(300, 100);
        setLayout(null);

        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("Check Result");
        heading.setBounds(80, 15, 400, 50);
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        add(heading);

        JLabel lblroll = new JLabel("Enter Roll No");
        lblroll.setBounds(80, 60, 200, 25);
        lblroll.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblroll);

        search = new JTextField();
        search.setBounds(80, 90, 200, 30);
        search.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(search);

        JLabel lblSem = new JLabel("Select Semester");
        lblSem.setBounds(300, 60, 200, 25);
        lblSem.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblSem);

        String sem[] = {"1st", "2nd", "3rd", "4th", "5th", "6th"};
        cbSem = new JComboBox<>(sem);
        cbSem.setBounds(300, 90, 150, 30);
        cbSem.setFont(new Font("Tahoma", Font.PLAIN, 15));
        add(cbSem);

        submit = new JButton("Result");
        submit.setBounds(480, 90, 120, 30);
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        submit.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(submit);

        cancel = new JButton("Back");
        cancel.setBounds(620, 90, 120, 30);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(cancel);

        table = new JTable();
        table.setFont(new Font("Tahoma", Font.PLAIN, 16));

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 130, 1000, 310);
        add(jsp);

        loadStudentData();

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                int row = table.getSelectedRow();
                search.setText(table.getModel().getValueAt(row, 0).toString());
            }
        });

        setVisible(true);
    }

    private void loadStudentData() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select rollno, name, course, branch from student");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String roll = search.getText();
            String sem = cbSem.getSelectedItem().toString();

            if (roll.equals("")) {
                JOptionPane.showMessageDialog(null, "Please Enter Roll Number");
                return;
            }

            setVisible(false);
            new Marks(roll, sem);
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new ExaminationDetails();
    }
}