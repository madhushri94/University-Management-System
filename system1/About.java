package university.management.system1;

import javax.swing.*;
import java.awt.*;

public class About extends JFrame {

    About() {
        setSize(750, 550);
        setLocation(350, 120);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/about.jpg"));
        Image i2 = i1.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(400, 20, 300, 200);
        add(image);

        JLabel heading = new JLabel("<html>University<br/>Management System</html>");
        heading.setBounds(50, 20, 300, 110);
        heading.setFont(new Font("Tahoma", Font.BOLD, 32));
        add(heading);

        // Developer 1
        JLabel name1 = new JLabel("Developed By: Madhushri Patil");
        name1.setBounds(60, 220, 600, 35);
        name1.setFont(new Font("Tahoma", Font.BOLD, 26));
        add(name1);

        JLabel rollno1 = new JLabel("Roll Number: 113");
        rollno1.setBounds(80, 260, 300, 30);
        rollno1.setFont(new Font("Tahoma", Font.PLAIN, 22));
        add(rollno1);

        JLabel contact1 = new JLabel("Email: madhushripatil55@gmail.com");
        contact1.setBounds(80, 295, 500, 30);
        contact1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(contact1);

        // Developer 2
        JLabel name2 = new JLabel("Developed By: Shruti Patil");
        name2.setBounds(60, 340, 600, 35);
        name2.setFont(new Font("Tahoma", Font.BOLD, 26));
        add(name2);

        JLabel rollno2 = new JLabel("Roll Number: 129");
        rollno2.setBounds(80, 380, 300, 30);
        rollno2.setFont(new Font("Tahoma", Font.PLAIN, 22));
        add(rollno2);

        JLabel contact2 = new JLabel("Email: shrutipatil01@gmail.com");
        contact2.setBounds(80, 415, 500, 30);
        contact2.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(contact2);

        setVisible(true);
    }

    public static void main(String[] args) {
        new About();
    }
}
