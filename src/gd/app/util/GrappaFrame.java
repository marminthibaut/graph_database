package gd.app.util;

import java.awt.Dimension;
import java.io.File;
import java.io.FileReader;

import javax.swing.JFrame;

import att.grappa.Graph;
import att.grappa.GrappaPanel;
import att.grappa.Parser;

/**
 * Classe d'affichage d'une jFrame Grappa
 * 
 * @author Clément Sipieter <csipieter@gmail.com>
 * @version 0.1
 * 
 */
public class GrappaFrame extends JFrame {

    private static final long serialVersionUID = -2686665605701969705L;

    /**
     * Create a new GrappaFrame
     * 
     * @param dot_file
     * @param title
     * @throws Exception
     */
    public GrappaFrame(File dot_file, String title) throws Exception {
        super(title);
        this.init(dot_file, title);

    }

    private void init(File dot_file, String title) throws Exception {

        Parser parser;
        parser = new Parser(new FileReader(dot_file));
        parser.parse();
        Graph graph = parser.getGraph();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(800, 600));
        GrappaPanel panel = new GrappaPanel(graph);
        panel.setPreferredSize(new Dimension(800, 600));
        this.setContentPane(panel);
        this.pack();
    }

}
