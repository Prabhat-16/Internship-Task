import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WordCountSwing extends JFrame implements ActionListener {

    JTextArea textArea;
    JButton countButton;
    JLabel resultLabel;

    WordCountSwing() {

        textArea = new JTextArea(5, 30);
        countButton = new JButton("Count");
        resultLabel = new JLabel("Words: 0 | Characters: 0");

        setLayout(new FlowLayout());

        add(new JScrollPane(textArea));
        add(countButton);
        add(resultLabel);

        countButton.addActionListener(this);

        setTitle("Word & Character Counter");
        setSize(400, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {

        String text = textArea.getText();

        int charCount = text.length();

        String words[] = text.trim().split("\\s+");
        int wordCount = (text.trim().isEmpty()) ? 0 : words.length;

        resultLabel.setText("Words: " + wordCount + " | Characters: " + charCount);
    }

    public static void main(String[] args) {
        new WordCountSwing();
    }
}
