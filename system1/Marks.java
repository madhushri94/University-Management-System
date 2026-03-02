package university.management.system1;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class Marks extends JFrame implements ActionListener {

    JTable table;
    JButton back;

    JLabel lblTotal, lblPercent, lblResult, lblGrade;

    Marks(String rollno, String semester) {

        setSize(900, 650);
        setLocation(300, 80);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("Semester Result");
        heading.setBounds(300, 10, 400, 40);
        heading.setFont(new Font("Tahoma", Font.BOLD, 28));
        add(heading);

        JLabel lblRoll = new JLabel("Roll No : " + rollno);
        lblRoll.setBounds(50, 60, 300, 25);
        lblRoll.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblRoll);

        JLabel lblSem = new JLabel("Semester : " + semester);
        lblSem.setBounds(350, 60, 300, 25);
        lblSem.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblSem);

        table = new JTable();
        table.setFont(new Font("Tahoma", Font.PLAIN, 16));

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(50, 100, 800, 300);
        add(jsp);

        lblTotal = new JLabel();
        lblTotal.setBounds(50, 420, 300, 30);
        lblTotal.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblTotal);

        lblPercent = new JLabel();
        lblPercent.setBounds(350, 420, 300, 30);
        lblPercent.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblPercent);

        lblResult = new JLabel();
        lblResult.setBounds(50, 460, 300, 30);
        lblResult.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblResult);

        lblGrade = new JLabel();
        lblGrade.setBounds(350, 460, 300, 30);
        lblGrade.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblGrade);

        back = new JButton("Back");
        back.setBounds(380, 520, 120, 35);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Tahoma", Font.BOLD, 15));
        back.addActionListener(this);
        add(back);

        loadMarks(rollno, semester);

        setVisible(true);
    }

    private void loadMarks(String roll, String sem) {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery(
                "select subject, marks from marks where rollno='" + roll + "' and semester='" + sem + "'"
            );

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Subject");
            model.addColumn("Marks");

            int total = 0, count = 0;

            while (rs.next()) {
                String sub = rs.getString("subject");
                int m = rs.getInt("marks");

                model.addRow(new Object[]{sub, m});

                total += m;
                count++;
            }

            table.setModel(model);

            if (count > 0) {
                double percent = total / (count * 100.0) * 100;

                lblTotal.setText("Total : " + total);
                lblPercent.setText("Percentage : " + String.format("%.2f", percent) + "%");

                if (percent >= 70) lblGrade.setText("Grade : A");
                else if (percent >= 60) lblGrade.setText("Grade : B");
                else if (percent >= 50) lblGrade.setText("Grade : C");
                else lblGrade.setText("Grade : D");

                lblResult.setText(percent >= 40 ? "Result : PASS" : "Result : FAIL");
            } else {
                JOptionPane.showMessageDialog(null, "No Result Found For This Semester");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new ExaminationDetails();
    }
}