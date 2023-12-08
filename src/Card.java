import java.awt.*;
import java.awt.image.ImageObserver;

public class Card {
    private String name;
    public Card(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public void showImage(String x, Graphics g) {
        Toolkit t=Toolkit.getDefaultToolkit();
        if(x.equals("Diffuse")) {
            ImageIcon
            Image i = t.getImage("Diffdsads.png");
            g.drawImage(i, 120,100,null);
        }
    }
}
