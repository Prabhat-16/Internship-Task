import java.awt.*;
import java.awt.event.*;

public class InfoAWT extends Frame implements ActionListener {

    Button btnA, btnB;
    Label result;

    InfoAWT() {

        // Set layout
        setLayout(new FlowLayout());

        // Create buttons
        btnA = new Button("A");
        btnB = new Button("B");

        // Create label to show result
        result = new Label("Press a button...");

        // Add components
        add(btnA);
        add(btnB);
        add(result);

        // Add action listener
        btnA.addActionListener(this);
        btnB.addActionListener(this);

        // Frame settings
        setSize(400, 200);
        setTitle("AWT Button Example");
        setVisible(true);

        // Close window properly
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    // Event Handling
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
