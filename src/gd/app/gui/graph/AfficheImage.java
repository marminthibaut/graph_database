package gd.app.gui.graph;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

class AfficheImage extends JPanel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -7819105802237756438L;
    Image img;

    AfficheImage(String s) {
        img = getToolkit().getImage(s);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }
}