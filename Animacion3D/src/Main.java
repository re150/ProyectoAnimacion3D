import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Pixel g = new Pixel();
            new Thread(g).start();
        });
    }
}
