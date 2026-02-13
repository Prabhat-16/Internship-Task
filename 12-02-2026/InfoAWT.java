import java.awt.*;
import java.awt.event.*;

public class InfoAWT extends Frame implements ActionListener {

    Button btnA, btnB;
    Label result;

    InfoAWT() {

        setLayout(new FlowLayout());

        btnA = new Button("A");
        btnB = new Button("B");

        result = new Label("Press a button...");

        add(btnA);
        add(btnB);
        add(result);

        btnA.addActionListener(this);
        btnB.addActionListener(this);

        setSize(400, 200);
        setTitle("AWT Button Example");
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnA) {
            result.setText("Name: Prabhat Mishra | Course: MCA | Roll No: 123 | College: MIT-WPU");
        }

        if (e.getSource() == btnB) {
            result.setText("Previous Semester CGPA: 8.7");
        }
    }

    public static void main(String[] args) {
        new InfoAWT();
    }
}
