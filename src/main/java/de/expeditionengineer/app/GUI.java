import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class GUI {

    public GUI() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JTextField textField = new JTextField(10); // 10 columns
        JButton button = new JButton("Click me");
        String[] dropdownOptions = { "Option 1", "Option 2", "Option 3" };
        JComboBox<String> dropdown = new JComboBox<>(dropdownOptions);

        panel.add(textField);
        panel.add(dropdown);
        panel.add(button);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new GUI();
    }
}