package university.management.system1;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class StudentFeeForm extends JFrame implements ActionListener {

    Choice crollno;
    JComboBox cbcourse, cbbranch, cbsemester;
    JLabel labeltotal;
    JTextField tfpay;
    JButton update, pay, receipt, back;

    int totalFee = 0;
    int paidFee = 0;
    String studentName = "";

    StudentFeeForm() {

        setSize(900, 500);
        setLocation(300, 100);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/fee.jpg"));
        Image i2 = i1.getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(380, 50, 500, 300);
        add(image);
        

        JLabel lblrollnumber = new JLabel("Select Roll No");
        lblrollnumber.setBounds(40, 60, 150, 20);
        lblrollnumber.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblrollnumber);

        crollno = new Choice();
        crollno.setBounds(200, 60, 150, 20);
        add(crollno);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select rollno from student");
            while (rs.next()) {
                crollno.add(rs.getString("rollno"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lblcourse = new JLabel("Course");
        lblcourse.setBounds(40, 150, 150, 20);
        lblcourse.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblcourse);

        String course[] = {"BTech","BBA","BCA","Bsc","Msc","MBA","MCA","MCom","MA","BA"};
        cbcourse = new JComboBox(course);
        cbcourse.setBounds(200, 150, 150, 20);
        add(cbcourse);

        JLabel lblbranch = new JLabel("Branch");
        lblbranch.setBounds(40, 190, 150, 20);
        lblbranch.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblbranch);

        String branch[] = {"Computer Science","Electronics","Mechanical","Civil","IT"};
        cbbranch = new JComboBox(branch);
        cbbranch.setBounds(200, 190, 150, 20);
        add(cbbranch);

        JLabel lblsemester = new JLabel("Semester");
        lblsemester.setBounds(40, 230, 150, 20);
        lblsemester.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblsemester);

        String semester[] = {"semester1","semester2","semester3","semester4",
                "semester5","semester6","semester7","semester8"};
        cbsemester = new JComboBox(semester);
        cbsemester.setBounds(200, 230, 150, 20);
        add(cbsemester);

        JLabel lbltotal = new JLabel("Remaining Fee");
        lbltotal.setBounds(40, 270, 150, 20);
        lbltotal.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lbltotal);

        labeltotal = new JLabel("0");
        labeltotal.setBounds(200, 270, 150, 20);
        add(labeltotal);

        JLabel lblpay = new JLabel("Pay Now");
        lblpay.setBounds(40, 310, 150, 20);
        lblpay.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblpay);

        tfpay = new JTextField();
        tfpay.setBounds(200, 310, 150, 25);
        add(tfpay);

        update = new JButton("Update");
        update.setBounds(40, 360, 100, 30);
        update.addActionListener(this);
        add(update);

        pay = new JButton("Pay Fee");
        pay.setBounds(160, 360, 100, 30);
        pay.addActionListener(this);
        add(pay);

        receipt = new JButton("Fee Receipt");
        receipt.setBounds(280, 360, 130, 30);
        receipt.addActionListener(this);
        add(receipt);

        back = new JButton("Back");
        back.setBounds(430, 360, 100, 30);
        back.addActionListener(this);
        add(back);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == update) {
            loadFeeDetails();
        }

        else if (ae.getSource() == pay) {

            if (labeltotal.getText().equals("0")) {
                JOptionPane.showMessageDialog(null, "Click Update First");
                return;
            }

            paidFee = Integer.parseInt(tfpay.getText());

            if (paidFee > totalFee) {
                JOptionPane.showMessageDialog(null, "Amount exceeds remaining fee");
                return;
            }

            showPaymentOptions();
        }

        else if (ae.getSource() == receipt) {

            if (paidFee == 0) {
                JOptionPane.showMessageDialog(null, "Pay Fee First");
                return;
            }

            new FeeReceipt(studentName, crollno.getSelectedItem(),
                    totalFee + paidFee, paidFee);
        }

        else {
            setVisible(false);
        }
    }

    // ===== LOAD REMAINING FEE =====
    private void loadFeeDetails() {

        String rollno = crollno.getSelectedItem();
        String course = (String) cbcourse.getSelectedItem();
        String semester = (String) cbsemester.getSelectedItem();

        try {
            Conn c = new Conn();

            ResultSet rs1 = c.s.executeQuery(
                    "select name from student where rollno='" + rollno + "'");
            if (rs1.next()) {
                studentName = rs1.getString("name");
            }

            ResultSet rs = c.s.executeQuery(
                    "select remaining_fee from studentfee where rollno='" + rollno +
                            "' and course='" + course + "' and semester='" + semester + "'");

            if (rs.next()) {
                totalFee = rs.getInt("remaining_fee");
            } else {
                ResultSet rs2 = c.s.executeQuery(
                        "select " + semester + " from fee where course='" + course + "'");
                if (rs2.next()) {
                    totalFee = rs2.getInt(1);
                }
            }

            labeltotal.setText(String.valueOf(totalFee));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== PAYMENT WINDOW =====
    private void showPaymentOptions() {

        JFrame frame = new JFrame("Online Payment");
        frame.setSize(300, 250);
        frame.setLocation(500, 300);
        frame.setLayout(null);

        JLabel lbl = new JLabel("Select Payment Method");
        lbl.setBounds(50, 20, 200, 25);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 14));
        frame.add(lbl);

        JButton phonepe = new JButton("PhonePe");
        phonepe.setBounds(80, 60, 120, 30);
        frame.add(phonepe);

        JButton gpay = new JButton("Google Pay");
        gpay.setBounds(80, 100, 120, 30);
        frame.add(gpay);

        JButton paytm = new JButton("Paytm");
        paytm.setBounds(80, 140, 120, 30);
        frame.add(paytm);

        ActionListener payment = e -> {
            JOptionPane.showMessageDialog(null, "Payment Successful ✔");
            saveFeeToDatabase();
            frame.setVisible(false);
        };

        phonepe.addActionListener(payment);
        gpay.addActionListener(payment);
        paytm.addActionListener(payment);

        frame.setVisible(true);
    }

    // ===== SAVE + UPDATE INSTALLMENT =====
    private void saveFeeToDatabase() {

        String rollno = crollno.getSelectedItem();
        String course = (String) cbcourse.getSelectedItem();
        String branch = (String) cbbranch.getSelectedItem();
        String semester = (String) cbsemester.getSelectedItem();

        try {
            Conn c = new Conn();

            ResultSet rs = c.s.executeQuery(
                    "select * from studentfee where rollno='" + rollno +
                            "' and course='" + course +
                            "' and semester='" + semester + "'");

            if (rs.next()) {

                int oldRemaining = rs.getInt("remaining_fee");
                int newRemaining = oldRemaining - paidFee;

                String status = (newRemaining == 0) ? "PAID" : "PENDING";

                String updateQuery = "update studentfee set " +
                        "paid_fee = paid_fee + " + paidFee +
                        ", remaining_fee = " + newRemaining +
                        ", fee_status='" + status +
                        "' where rollno='" + rollno +
                        "' and course='" + course +
                        "' and semester='" + semester + "'";

                c.s.executeUpdate(updateQuery);

            } else {

                int remaining = totalFee - paidFee;
                String status = (remaining == 0) ? "PAID" : "PENDING";

                String insertQuery = "insert into studentfee " +
                        "(rollno, course, branch, semester, total_fee, paid_fee, remaining_fee, fee_status) " +
                        "values('" + rollno + "','" + course + "','" + branch + "','" +
                        semester + "'," + totalFee + "," + paidFee + "," + remaining + ",'" + status + "')";

                c.s.executeUpdate(insertQuery);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new StudentFeeForm();
    }
}