package university.management.system1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import com.toedter.calendar.JDateChooser;
import java.awt.event.*;

public class TeacherLeave extends JFrame implements ActionListener {

    Choice cEmpId, ctime;
    JDateChooser dcdate;
    JButton submit, cancel;
    JTable table;
    DefaultTableModel model;

    TeacherLeave() {

        setSize(750, 550);
        setLocation(350, 100);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("Apply Leave (Teacher)");
        heading.setBounds(30, 20, 300, 30);
        heading.setFont(new Font("Tahoma", Font.BOLD, 22));
        add(heading);

        JLabel lblrollno = new JLabel("Employee Id");
        lblrollno.setBounds(40, 80, 150, 25);
        lblrollno.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblrollno);

        cEmpId = new Choice();
        cEmpId.setBounds(40, 110, 200, 25);
        add(cEmpId);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select empId from teacher");
            while (rs.next()) {
                cEmpId.add(rs.getString("empId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        cEmpId.addItemListener(e -> loadLeaveTable());

        JLabel lbldate = new JLabel("Date");
        lbldate.setBounds(40, 160, 150, 25);
        lbldate.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lbldate);

        dcdate = new JDateChooser();
        dcdate.setBounds(40, 190, 200, 25);
        add(dcdate);

        JLabel lbltime = new JLabel("Time Duration");
        lbltime.setBounds(40, 240, 200, 25);
        lbltime.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lbltime);

        ctime = new Choice();
        ctime.setBounds(40, 270, 200, 25);
        ctime.add("Full Day");
        ctime.add("Half Day");
        add(ctime);

        submit = new JButton("Submit");
        submit.setBounds(40, 330, 100, 30);
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(160, 330, 100, 30);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        add(cancel);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Total Leave", "Used Leave", "Remaining Leave", "Leave Type"});

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(300, 80, 420, 300);
        add(sp);

        loadLeaveTable();

        setVisible(true);
    }

    void loadLeaveTable() {

        model.setRowCount(0);

        try {
            Conn c = new Conn();
            String emp = cEmpId.getSelectedItem();

            ResultSet rs = c.s.executeQuery("select * from teacher_leave_summary where empId='" + emp + "'");

            if (rs.next()) {
                model.addRow(new Object[]{
                        8,
                        rs.getInt("used_leave"),
                        rs.getInt("remaining_leave"),
                        rs.getInt("used_leave") < 8 ? "Casual Leave" : "Paid Leave"
                });
            } else {
                c.s.executeUpdate("insert into teacher_leave_summary(empId) values('" + emp + "')");
                model.addRow(new Object[]{8, 0, 8, "Casual Leave"});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == submit) {

            submit.setEnabled(false);

            String empId = cEmpId.getSelectedItem();
            String date = ((JTextField) dcdate.getDateEditor().getUiComponent()).getText();
            String duration = ctime.getSelectedItem();

            if (date.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please select date!");
                submit.setEnabled(true);
                return;
            }

            try {
                Conn c = new Conn();

                ResultSet check = c.s.executeQuery(
                        "select * from teacherleave where empId='" + empId + "' and date='" + date + "'");

                if (check.next()) {
                    JOptionPane.showMessageDialog(null, "This date leave already applied!");
                    submit.setEnabled(true);
                    return;
                }

                ResultSet rs = c.s.executeQuery(
                        "select used_leave from teacher_leave_summary where empId='" + empId + "'");

                int used = 0;

                if (rs.next()) used = rs.getInt(1);

                used++;

                String leaveType = used <= 8 ? "Casual Leave" : "Paid Leave";

                c.s.executeUpdate(
                        "insert into teacherleave values('" + empId + "','" + date + "','" + duration + "','" + leaveType + "')");

                int remaining = Math.max(0, 8 - used);

                c.s.executeUpdate(
                        "update teacher_leave_summary set used_leave=" + used + ", remaining_leave=" + remaining + " where empId='" + empId + "'");

                JOptionPane.showMessageDialog(null, "Leave Applied Successfully (" + leaveType + ")");

                loadLeaveTable();

            } catch (Exception e) {
                e.printStackTrace();
            }

            submit.setEnabled(true);

        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new TeacherLeave();
    }
}