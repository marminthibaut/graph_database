package gd.app.gui.graph;

import java.awt.BorderLayout;
import javax.swing.JFrame;

class GlobalConsoleFrame extends JFrame {
    /**
	 * 
	 */
    private static final long serialVersionUID = 6422810142668346018L;

    public GlobalConsoleFrame() {
        setSize(600, 480);
        setTitle("Test d'affichage d'une image");
        String url = System.getProperty("user.dir")
                + "/bin/gd/app/gui/graph/test.png";
        System.out.println(url);
        setContentPane(new AfficheImage(url));
        getContentPane().setLayout(new BorderLayout());
        this.setVisible(true);
    }
}