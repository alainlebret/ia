package multiagents;

import javax.swing.JFrame;

public class Application {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        OceanPanel panel = new OceanPanel();

        frame.setTitle("Poissons agents");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setContentPane(panel);
        frame.setVisible(true);
        panel.run();
    }
}
