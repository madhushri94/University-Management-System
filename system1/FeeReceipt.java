package university.management.system1;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.*;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class FeeReceipt extends JFrame {

    String studentName, rollNumber, feeStatus;
    int totalFee, paidFee, remainingFee;

    public FeeReceipt(String name, String rollno, int total, int paid) {

        this.studentName = name;
        this.rollNumber = rollno;
        this.totalFee = total;
        this.paidFee = paid;
        this.remainingFee = total - paid;

        // ===== FEE STATUS LOGIC =====
        this.feeStatus = (remainingFee == 0) ? "PAID" : "PENDING";
        // ============================

        setTitle("Fee Receipt");
        setSize(500, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("STUDENT FEE RECEIPT", JLabel.CENTER);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(heading, BorderLayout.NORTH);

        String column[] = {"Field", "Details"};
        String data[][] = {
                {"Student Name", studentName},
                {"Roll No", rollNumber},
                {"Total Fee", String.valueOf(totalFee)},
                {"Paid Fee", String.valueOf(paidFee)},
                {"Remaining Fee", String.valueOf(remainingFee)},
                {"Fee Status", feeStatus}   // ✅ ADDED
        };

        JTable table = new JTable(data, column);
        table.setRowHeight(30);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton exportBtn = new JButton("Export PDF");
        exportBtn.addActionListener(e -> generatePDF());

        JPanel p = new JPanel();
        p.add(exportBtn);
        add(p, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void generatePDF() {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String date = sdf.format(new Date());

            String fileName = rollNumber + "_FeeReceipt_" + date + ".pdf";
            String path = System.getProperty("user.home") + "\\Desktop\\" + fileName;

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            Paragraph title = new Paragraph("Kolhapur Technical University\n\n");
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("Date : " + date + "\n\n"));
            document.add(new Paragraph("----------------------------------------------------\n"));

            PdfPTable pdfTable = new PdfPTable(2);
            pdfTable.setWidthPercentage(80);

            addRow(pdfTable, "Student Name", studentName);
            addRow(pdfTable, "Roll No", rollNumber);
            addRow(pdfTable, "Total Fee", String.valueOf(totalFee));
            addRow(pdfTable, "Paid Fee", String.valueOf(paidFee));
            addRow(pdfTable, "Remaining Fee", String.valueOf(remainingFee));
            addRow(pdfTable, "Fee Status", feeStatus);   // ✅ ADDED

            document.add(pdfTable);
            document.close();

            JOptionPane.showMessageDialog(this,
                    "Receipt PDF Generated Successfully\nSaved On Desktop");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "PDF Generation Error");
        }
    }

    private void addRow(PdfPTable table, String key, String value) {

        PdfPCell c1 = new PdfPCell(new Phrase(key));
        PdfPCell c2 = new PdfPCell(new Phrase(value));

        c1.setPadding(8);
        c2.setPadding(8);

        table.addCell(c1);
        table.addCell(c2);
    }
}