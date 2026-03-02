package university.management.system1;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class TeacherLeaveDetails extends JFrame implements ActionListener {

    Choice cEmpId;
    JTable table, summaryTable;
    JButton search, print, cancel;

    TeacherLeaveDetails() {

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("Teacher Leave Details");
        heading.setBounds(20, 10, 300, 30);
        heading.setFont(new Font("Tahoma", Font.BOLD, 22));
        add(heading);

        JLabel lbl = new JLabel("Search by Employee Id");
        lbl.setBounds(20, 50, 200, 20);
        add(lbl);

        cEmpId = new Choice();
        cEmpId.setBounds(220, 50, 150, 20);
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

        table = new JTable();

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select empId,date,duration,leave_type from teacherleave");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(20, 90, 850, 350);
        add(jsp);

        // -------- SUMMARY TABLE --------

        summaryTable = new JTable();
        JScrollPane sp2 = new JScrollPane(summaryTable);
        sp2.setBounds(20, 460, 850, 100);
        add(sp2);

        loadSummaryTable(cEmpId.getSelectedItem());

        search = new JButton("Search");
        search.setBounds(400, 50, 80, 25);
        search.addActionListener(this);
        add(search);

        print = new JButton("Print");
        print.setBounds(500, 50, 80, 25);
        print.addActionListener(this);
        add(print);

        cancel = new JButton("Cancel");
        cancel.setBounds(600, 50, 80, 25);
        cancel.addActionListener(this);
        add(cancel);

        setSize(900, 620);
        setLocation(250, 80);
        setVisible(true);
    }

    void loadSummaryTable(String empId) {

        try {
            Conn c = new Conn();

            ResultSet rs = c.s.executeQuery(
                    "select total_leave as 'Total Leave', used_leave as 'Used Leave', " +
                            "remaining_leave as 'Remaining Leave', " +
                            "case when used_leave <= 8 then 'Casual Leave' else 'Paid Leave' end as 'Leave Type' " +
                            "from teacher_leave_summary where empId='" + empId + "'"
            );

            summaryTable.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == search) {

            String emp = cEmpId.getSelectedItem();

            try {
                Conn c = new Conn();

                ResultSet rs = c.s.executeQuery(
                        "select empId,date,duration,leave_type from teacherleave where empId='" + emp + "'"
                );
                table.setModel(DbUtils.resultSetToTableModel(rs));

                loadSummaryTable(emp);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (ae.getSource() == print) {

            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new TeacherLeaveDetails();
    }
}
