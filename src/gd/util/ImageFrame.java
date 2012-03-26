package gd.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author clement
 * 
 */
public class ImageFrame extends JFrame {

    private static final long serialVersionUID = 1636296900020069277L;

    /**
     * @param url
     */
    public ImageFrame(String url) {
        init(url, "Image Frame", 800, 600);
    }

    /**
     * @param url
     * @param title
     */
    public ImageFrame(String url, String title) {
        init(url, title, 800, 600);
    }

    /**
     * @param url
     * @param title
     * @param width
     * @param height
     */
    public ImageFrame(String url, String title, int width, int height) {
        init(url, title, width, height);
    }

    private void init(String url, String title, int width, int height) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setTitle(title);
        getContentPane().add(new ImagePanel(url));
        pack();
        setVisible(true);
    }

    private class ImagePanel extends JPanel {

        private static final long serialVersionUID = 1853796008433160465L;
        BufferedImage img;

        ImagePanel(String image_path) {
            System.out.println(image_path);
            try {
                img = ImageIO.read(new File(image_path));
                setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }

}
