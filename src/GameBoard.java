import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameBoard implements ActionListener {
    JFrame frame;
    JPanel panel;
    JButton button;
    JLabel label;
    JTextField textfield;

    public void actionPerformed(ActionEvent e) {

    }

    public GameBoard() throws IOException {
        frame = new JFrame("Exploding Kitten!");
        frame.setSize(1400, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        frame.add(panel);
        initComponents(frame);
        frame.pack();
        frame.setVisible(true);
    }
    private void initComponents(JFrame frame) {
        frame.add(new ImagePanel());
    }



    class ImagePanel extends JPanel {
        private Card diffuse = new Card("Diffuse");
        private Dimension dim = new Dimension(450, 300);
        private final ArrayList<Card> Cards;

        public ImagePanel() {
            Cards = new ArrayList<Card>();
            Cards.add(diffuse);
            //Cards.get(0).showImage(Cards.get(0).getName(),g);
        }
        @Override
        public Dimension getPreferredSize() {
            return dim;
        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            diffuse.showImage("Diffuse", g);
        }
    }
}